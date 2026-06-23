package com.example.training.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.training.dto.CreatePaymentTransactionRequest;
import com.example.training.dto.PaymentTransactionResponse;
import com.example.training.enums.PaymentStatus;
import com.example.training.entity.PaymentTransactionEntity;
import com.example.training.entity.RepaymentScheduleEntity;
import com.example.training.enums.RepaymentStatus;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.PaymentTransactionRepository;
import com.example.training.repository.RepaymentScheduleRepository;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @Mock
    private PaymentTransactionRepository paymentTransactionRepository;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    private RepaymentScheduleEntity schedule;
    private PaymentTransactionEntity payment;
    private UUID scheduleId;
    private UUID paymentId;

    @BeforeEach
    void setUp() {
        scheduleId = UUID.randomUUID();
        paymentId = UUID.randomUUID();

        schedule = new RepaymentScheduleEntity();
        schedule.setId(scheduleId);
        schedule.setTotalAmount(new BigDecimal("1100000"));
        schedule.setStatus(RepaymentStatus.UNPAID);

        payment = new PaymentTransactionEntity();
        payment.setId(paymentId);
        payment.setRepaymentSchedule(schedule);
        payment.setPaymentReference("PAY-20260619-001");
        payment.setPaidAmount(new BigDecimal("950000"));
        payment.setPaidAt(OffsetDateTime.parse("2026-06-19T10:00:00+07:00"));
        payment.setStatus(PaymentStatus.SUCCESS);
    }

    @Test
    void create_shouldReturnResponse_whenScheduleExists() {
        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
        request.setRepaymentScheduleId(scheduleId);
        request.setPaymentReference("PAY-20260619-001");
        request.setPaidAmount(new BigDecimal("950000"));
        request.setPaidAt(OffsetDateTime.parse("2026-06-19T10:00:00+07:00"));

        when(repaymentScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentTransactionRepository.save(any(PaymentTransactionEntity.class))).thenReturn(payment);
        when(paymentTransactionRepository.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(new BigDecimal("950000"));

        PaymentTransactionResponse response = paymentTransactionService.create(request);

        assertThat(response.getId()).isEqualTo(paymentId);
        assertThat(response.getRepaymentScheduleId()).isEqualTo(scheduleId);
        assertThat(response.getPaymentReference()).isEqualTo("PAY-20260619-001");
        assertThat(response.getPaidAmount()).isEqualByComparingTo("950000");
        assertThat(response.getStatus()).isEqualTo("SUCCESS");

        verify(paymentTransactionRepository).save(any(PaymentTransactionEntity.class));
    }

    @Test
    void create_shouldThrowNotFound_whenScheduleDoesNotExist() {
        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
        request.setRepaymentScheduleId(scheduleId);
        request.setPaymentReference("PAY-20260619-001");
        request.setPaidAmount(new BigDecimal("950000"));
        request.setPaidAt(OffsetDateTime.parse("2026-06-19T10:00:00+07:00"));

        when(repaymentScheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentTransactionService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Repayment schedule not found");
    }

    @Test
    void findByRepaymentScheduleId_shouldReturnList() {
        when(paymentTransactionRepository.findByRepaymentSchedule_Id(scheduleId))
                .thenReturn(List.of(payment));

        List<PaymentTransactionResponse> result = paymentTransactionService.findByRepaymentScheduleId(scheduleId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(paymentId);
        assertThat(result.get(0).getRepaymentScheduleId()).isEqualTo(scheduleId);
    }

    @Test
    void findByRepaymentScheduleId_shouldReturnEmptyList_whenNoPayments() {
        when(paymentTransactionRepository.findByRepaymentSchedule_Id(scheduleId))
                .thenReturn(List.of());

        List<PaymentTransactionResponse> result = paymentTransactionService.findByRepaymentScheduleId(scheduleId);

        assertThat(result).isEmpty();
    }

    @Test
    void create_shouldThrow_whenScheduleIsAlreadyPaid() {
        schedule.setStatus(RepaymentStatus.PAID);

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
        request.setRepaymentScheduleId(scheduleId);
        request.setPaymentReference("PAY-001");
        request.setPaidAmount(new BigDecimal("950000"));
        request.setPaidAt(OffsetDateTime.parse("2026-06-19T10:00:00+07:00"));

        when(repaymentScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        assertThatThrownBy(() -> paymentTransactionService.create(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already PAID");
    }

    @Test
    void create_shouldMarkScheduleAsPaid_whenTotalPaidMeetsAmount() {
        schedule.setTotalAmount(new BigDecimal("950000"));

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
        request.setRepaymentScheduleId(scheduleId);
        request.setPaymentReference("PAY-FULL");
        request.setPaidAmount(new BigDecimal("950000"));
        request.setPaidAt(OffsetDateTime.parse("2026-06-19T10:00:00+07:00"));

        when(repaymentScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentTransactionRepository.save(any(PaymentTransactionEntity.class))).thenReturn(payment);
        when(paymentTransactionRepository.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(new BigDecimal("950000"));

        PaymentTransactionResponse response = paymentTransactionService.create(request);

        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        verify(repaymentScheduleRepository).save(schedule);
        assertThat(schedule.getStatus()).isEqualTo(RepaymentStatus.PAID);
    }
}
