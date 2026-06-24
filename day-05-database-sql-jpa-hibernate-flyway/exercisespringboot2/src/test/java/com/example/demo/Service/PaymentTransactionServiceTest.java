package com.example.demo.Service;

import com.example.training.DTO.CreatePaymentTransactionRequest;
import com.example.training.DTO.PaymentTransactionResponse;
import com.example.training.Entity.PaymentTransactionEntity;
import com.example.training.Entity.RepaymentScheduleEntity;
import com.example.training.Exception.RepaymentScheduleNotFoundException;
import com.example.training.Repository.PaymentTransactionRepository;
import com.example.training.Repository.RepaymentScheduleRepository;
import com.example.training.Service.PaymentTransactionService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
@DisplayName("Payment Transaction Service Tests")
class PaymentTransactionServiceTest {

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    @Mock
    private PaymentTransactionRepository paymentTransactionRepository;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    // ========== Constants ==========
    private static final Long SCHEDULE_ID = 1L;
    private static final Long PAYMENT_ID = 1L;
    private static final Long NON_EXISTING_SCHEDULE_ID = 999L;
    private static final BigDecimal TOTAL_AMOUNT = new BigDecimal("1000000");
    private static final String PAYMENT_REF = "REF123456789";

    // ========== Test Data Factory ==========
    private RepaymentScheduleEntity buildSchedule(RepaymentScheduleEntity.ScheduleStatus status) {
        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setId(SCHEDULE_ID);
        schedule.setTotalAmount(TOTAL_AMOUNT);
        schedule.setStatus(status);
        schedule.setDueDate(ZonedDateTime.now().plusMonths(1));
        return schedule;
    }

    private CreatePaymentTransactionRequest buildPaymentRequest(BigDecimal amount) {
        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
        request.setRepaymentScheduleId(SCHEDULE_ID);
        request.setPaidAmount(amount);
        request.setPaymentReference(PAYMENT_REF);
        request.setPaidAt(ZonedDateTime.now());
        return request;
    }

    private PaymentTransactionEntity buildSavedPayment(BigDecimal amount) {
        PaymentTransactionEntity payment = new PaymentTransactionEntity();
        payment.setId(PAYMENT_ID);
        payment.setRepaymentSchedule(buildSchedule(RepaymentScheduleEntity.ScheduleStatus.UNPAID));
        payment.setPaidAmount(amount);
        payment.setPaymentReference(PAYMENT_REF);
        payment.setStatus(PaymentTransactionEntity.PaymentStatus.SUCCESS);
        payment.setPaidAt(ZonedDateTime.now());
        payment.setCreatedAt(ZonedDateTime.now());
        payment.setUpdatedAt(ZonedDateTime.now());
        return payment;
    }

    // ========== Nested: Create Payment ==========
    @Nested
    @DisplayName("Create Payment Transaction")
    class CreatePaymentTests {

        @Test
        @DisplayName("Positive: Full payment (amount = total) → schedule should become PAID")
        void fullPayment_shouldSetScheduleToPaid() {
            // Given
            CreatePaymentTransactionRequest request = buildPaymentRequest(TOTAL_AMOUNT);
            RepaymentScheduleEntity schedule = buildSchedule(RepaymentScheduleEntity.ScheduleStatus.UNPAID);
            PaymentTransactionEntity savedPayment = buildSavedPayment(TOTAL_AMOUNT);

            given(repaymentScheduleRepository.findById(SCHEDULE_ID)).willReturn(Optional.of(schedule));
            given(paymentTransactionRepository.sumPaidAmountByScheduleId(SCHEDULE_ID)).willReturn(BigDecimal.ZERO);
            given(repaymentScheduleRepository.save(any(RepaymentScheduleEntity.class))).willReturn(schedule);
            given(paymentTransactionRepository.save(any(PaymentTransactionEntity.class))).willReturn(savedPayment);

            // When
            PaymentTransactionResponse result = paymentTransactionService.create(request);

            // Then
            assertThat(result)
                    .isNotNull()
                    .satisfies(r -> {
                        assertThat(r.getId()).isEqualTo(PAYMENT_ID);
                        assertThat(r.getPaidAmount()).isEqualTo(TOTAL_AMOUNT);
                        assertThat(r.getStatus()).isEqualTo("SUCCESS");
                    });
            assertThat(schedule.getStatus()).isEqualTo(RepaymentScheduleEntity.ScheduleStatus.PAID);
            then(repaymentScheduleRepository).should().save(schedule);
        }

        @Test
        @DisplayName("Positive: Partial payment (amount < total) → schedule should become PARTIAL")
        void partialPayment_shouldSetScheduleToPartial() {
            // Given
            BigDecimal partialAmount = new BigDecimal("300000");
            CreatePaymentTransactionRequest request = buildPaymentRequest(partialAmount);
            RepaymentScheduleEntity schedule = buildSchedule(RepaymentScheduleEntity.ScheduleStatus.UNPAID);
            PaymentTransactionEntity savedPayment = buildSavedPayment(partialAmount);

            given(repaymentScheduleRepository.findById(SCHEDULE_ID)).willReturn(Optional.of(schedule));
            given(paymentTransactionRepository.sumPaidAmountByScheduleId(SCHEDULE_ID)).willReturn(BigDecimal.ZERO);
            given(repaymentScheduleRepository.save(any(RepaymentScheduleEntity.class))).willReturn(schedule);
            given(paymentTransactionRepository.save(any(PaymentTransactionEntity.class))).willReturn(savedPayment);

            // When
            PaymentTransactionResponse result = paymentTransactionService.create(request);

            // Then
            assertThat(result.getPaidAmount()).isEqualTo(partialAmount);
            assertThat(schedule.getStatus()).isEqualTo(RepaymentScheduleEntity.ScheduleStatus.PARTIAL);
        }

        @Test
        @DisplayName("Positive: Multiple partial payments → cumulative should reach PAID")
        void multiplePartialPayments_shouldEventuallyBecomePaid() {
            // Given - second payment of 700K when 300K already paid
            BigDecimal secondPayment = new BigDecimal("700000");
            CreatePaymentTransactionRequest request = buildPaymentRequest(secondPayment);
            RepaymentScheduleEntity schedule = buildSchedule(RepaymentScheduleEntity.ScheduleStatus.PARTIAL);
            PaymentTransactionEntity savedPayment = buildSavedPayment(secondPayment);

            given(repaymentScheduleRepository.findById(SCHEDULE_ID)).willReturn(Optional.of(schedule));
            given(paymentTransactionRepository.sumPaidAmountByScheduleId(SCHEDULE_ID)).willReturn(new BigDecimal("300000"));
            given(repaymentScheduleRepository.save(any(RepaymentScheduleEntity.class))).willReturn(schedule);
            given(paymentTransactionRepository.save(any(PaymentTransactionEntity.class))).willReturn(savedPayment);

            // When
            PaymentTransactionResponse result = paymentTransactionService.create(request);

            // Then
            assertThat(result.getPaidAmount()).isEqualTo(secondPayment);
            assertThat(schedule.getStatus()).isEqualTo(RepaymentScheduleEntity.ScheduleStatus.PAID);
        }

        @Test
        @DisplayName("Negative: Non-existing schedule ID → should throw RepaymentScheduleNotFoundException")
        void nonExistingSchedule_shouldThrowRepaymentScheduleNotFoundException() {
            // Given
            CreatePaymentTransactionRequest request = buildPaymentRequest(TOTAL_AMOUNT);
            request.setRepaymentScheduleId(NON_EXISTING_SCHEDULE_ID);
            given(repaymentScheduleRepository.findById(NON_EXISTING_SCHEDULE_ID)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> paymentTransactionService.create(request))
                    .isInstanceOf(RepaymentScheduleNotFoundException.class)
                    .hasMessageContaining(String.valueOf(NON_EXISTING_SCHEDULE_ID));
            then(paymentTransactionRepository).should(never()).save(any());
        }

        @Test
        @DisplayName("Negative: Overpayment (amount > total) → should still mark as PAID")
        void overpayment_shouldStillMarkAsPaid() {
            // Given
            BigDecimal overPayment = new BigDecimal("1500000");
            CreatePaymentTransactionRequest request = buildPaymentRequest(overPayment);
            RepaymentScheduleEntity schedule = buildSchedule(RepaymentScheduleEntity.ScheduleStatus.UNPAID);
            PaymentTransactionEntity savedPayment = buildSavedPayment(overPayment);

            given(repaymentScheduleRepository.findById(SCHEDULE_ID)).willReturn(Optional.of(schedule));
            given(paymentTransactionRepository.sumPaidAmountByScheduleId(SCHEDULE_ID)).willReturn(BigDecimal.ZERO);
            given(repaymentScheduleRepository.save(any(RepaymentScheduleEntity.class))).willReturn(schedule);
            given(paymentTransactionRepository.save(any(PaymentTransactionEntity.class))).willReturn(savedPayment);

            // When
            PaymentTransactionResponse result = paymentTransactionService.create(request);

            // Then
            assertThat(schedule.getStatus()).isEqualTo(RepaymentScheduleEntity.ScheduleStatus.PAID);
            // Note: Logic allows overpayment - might need validation layer improvement
        }
    }

    // ========== Nested: Get Payments ==========
    @Nested
    @DisplayName("Get Payment Transactions")
    class GetPaymentTests {

        @Test
        @DisplayName("Positive: Existing schedule with payments → should return payment list")
        void existingScheduleWithPayments_shouldReturnList() {
            // Given
            PaymentTransactionEntity payment1 = buildSavedPayment(new BigDecimal("500000"));
            payment1.setId(1L);
            PaymentTransactionEntity payment2 = buildSavedPayment(new BigDecimal("500000"));
            payment2.setId(2L);

            given(repaymentScheduleRepository.existsById(SCHEDULE_ID)).willReturn(true);
            given(paymentTransactionRepository.findByRepaymentScheduleId(SCHEDULE_ID))
                    .willReturn(List.of(payment1, payment2));

            // When
            List<PaymentTransactionResponse> result = paymentTransactionService.getByRepaymentScheduleId(SCHEDULE_ID);

            // Then
            assertThat(result)
                    .hasSize(2)
                    .extracting(PaymentTransactionResponse::getPaidAmount)
                    .containsExactly(new BigDecimal("500000"), new BigDecimal("500000"));
        }

        @Test
        @DisplayName("Positive: Existing schedule without payments → should return empty list")
        void existingScheduleWithoutPayments_shouldReturnEmptyList() {
            // Given
            given(repaymentScheduleRepository.existsById(SCHEDULE_ID)).willReturn(true);
            given(paymentTransactionRepository.findByRepaymentScheduleId(SCHEDULE_ID)).willReturn(List.of());

            // When
            List<PaymentTransactionResponse> result = paymentTransactionService.getByRepaymentScheduleId(SCHEDULE_ID);

            // Then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Negative: Non-existing schedule ID → should throw RepaymentScheduleNotFoundException")
        void nonExistingSchedule_shouldThrowRepaymentScheduleNotFoundException() {
            // Given
            given(repaymentScheduleRepository.existsById(NON_EXISTING_SCHEDULE_ID)).willReturn(false);

            // When & Then
            assertThatThrownBy(() -> paymentTransactionService.getByRepaymentScheduleId(NON_EXISTING_SCHEDULE_ID))
                    .isInstanceOf(RepaymentScheduleNotFoundException.class);
            then(paymentTransactionRepository).should(never()).findByRepaymentScheduleId(any());
        }
    }
}