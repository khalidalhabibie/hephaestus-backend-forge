package com.fif.exercise2.service;

import com.fif.exercise2.dto.CreatePaymentTransactionRequest;
import com.fif.exercise2.dto.PaymentTransactionResponse;
import com.fif.exercise2.entity.PaymentTransactionEntity;
import com.fif.exercise2.entity.RepaymentScheduleEntity;
import com.fif.exercise2.exception.RepaymentScheduleNotFoundException;
import com.fif.exercise2.repository.PaymentTransactionRepository;
import com.fif.exercise2.repository.RepaymentScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

        @Mock
        private PaymentTransactionRepository paymentTransactionRepository;

        @Mock
        private RepaymentScheduleRepository repaymentScheduleRepository;

        @InjectMocks
        private PaymentTransactionService paymentTransactionService;

        // ==========================================================================
        // createPaymentTransaction — 3 branch: PAID, PARTIAL, UNPAID
        // ==========================================================================

        @Test
        void should_create_payment_and_mark_schedule_as_paid_when_fully_paid() {
                // given — totalPaid >= totalAmount → status jadi PAID
                RepaymentScheduleEntity schedule = buildSchedule(1L, new BigDecimal("1000000"), "UNPAID");
                when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

                PaymentTransactionEntity saved = buildTransaction(10L, schedule,
                        new BigDecimal("1000000"), "TRX-001");
                when(paymentTransactionRepository.save(any())).thenReturn(saved);

                // totalPaid = 1.000.000 >= totalAmount = 1.000.000 → PAID
                when(paymentTransactionRepository.sumPaidAmountByScheduleId(1L))
                        .thenReturn(new BigDecimal("1000000"));
                when(repaymentScheduleRepository.save(any())).thenReturn(schedule);

                CreatePaymentTransactionRequest request = buildRequest(1L,
                        new BigDecimal("1000000"), "TRX-001");

                // when
                PaymentTransactionResponse response = paymentTransactionService.createPaymentTransaction(request);

                // then
                assertNotNull(response);
                assertEquals("SUCCESS", response.getStatus());
                assertEquals("PAID", schedule.getStatus()); // schedule ter-update ke PAID
                verify(repaymentScheduleRepository, times(1)).save(schedule);
        }

        @Test
        void should_create_payment_and_mark_schedule_as_partial_when_partially_paid() {
                // given — totalPaid > 0 tapi < totalAmount → status jadi PARTIAL
                RepaymentScheduleEntity schedule = buildSchedule(1L, new BigDecimal("1000000"), "UNPAID");
                when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

                PaymentTransactionEntity saved = buildTransaction(10L, schedule,
                        new BigDecimal("500000"), "TRX-002");
                when(paymentTransactionRepository.save(any())).thenReturn(saved);

                // totalPaid = 500.000 < totalAmount = 1.000.000 → PARTIAL
                when(paymentTransactionRepository.sumPaidAmountByScheduleId(1L))
                        .thenReturn(new BigDecimal("500000"));
                when(repaymentScheduleRepository.save(any())).thenReturn(schedule);

                CreatePaymentTransactionRequest request = buildRequest(1L,
                        new BigDecimal("500000"), "TRX-002");

                // when
                PaymentTransactionResponse response = paymentTransactionService.createPaymentTransaction(request);

                // then
                assertEquals("SUCCESS", response.getStatus());
                assertEquals("PARTIAL", schedule.getStatus());
        }

        @Test
        void should_create_payment_and_keep_schedule_unpaid_when_total_paid_is_zero() {
                // given — totalPaid = 0 (edge case: payment gagal di-sum) → status tetap UNPAID
                RepaymentScheduleEntity schedule = buildSchedule(1L, new BigDecimal("1000000"), "UNPAID");
                when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

                PaymentTransactionEntity saved = buildTransaction(10L, schedule,
                        new BigDecimal("0"), "TRX-003");
                when(paymentTransactionRepository.save(any())).thenReturn(saved);

                // totalPaid = 0 → tidak masuk branch PAID maupun PARTIAL → tetap UNPAID
                when(paymentTransactionRepository.sumPaidAmountByScheduleId(1L))
                        .thenReturn(BigDecimal.ZERO);
                when(repaymentScheduleRepository.save(any())).thenReturn(schedule);

                CreatePaymentTransactionRequest request = buildRequest(1L, BigDecimal.ZERO, "TRX-003");

                // when
                paymentTransactionService.createPaymentTransaction(request);

                // then — status tidak berubah dari UNPAID
                assertEquals("UNPAID", schedule.getStatus());
        }

        @Test
        void should_throw_not_found_when_repayment_schedule_does_not_exist() {
                // given — schedule tidak ditemukan
                when(repaymentScheduleRepository.findById(99L)).thenReturn(Optional.empty());

                CreatePaymentTransactionRequest request = buildRequest(99L,
                        new BigDecimal("1000000"), "TRX-999");

                // when & then
                assertThrows(RepaymentScheduleNotFoundException.class,
                        () -> paymentTransactionService.createPaymentTransaction(request));

                // tidak boleh ada yang tersimpan
                verify(paymentTransactionRepository, never()).save(any());
        }

        // ==========================================================================
        // getByRepaymentScheduleId
        // ==========================================================================

        @Test
        void should_return_payment_transactions_by_schedule_id() {
                // given
                RepaymentScheduleEntity schedule = buildSchedule(1L, new BigDecimal("1000000"), "PAID");
                List<PaymentTransactionEntity> entities = List.of(
                        buildTransaction(1L, schedule, new BigDecimal("1000000"), "TRX-001")
                );
                when(paymentTransactionRepository.findByRepaymentScheduleId(1L)).thenReturn(entities);

                // when
                List<PaymentTransactionResponse> responses =
                        paymentTransactionService.getByRepaymentScheduleId(1L);

                // then
                assertEquals(1, responses.size());
                assertEquals("TRX-001", responses.get(0).getPaymentReference());
                assertEquals(1L, responses.get(0).getRepaymentScheduleId());
        }

        @Test
        void should_return_empty_list_when_no_transactions_exist_for_schedule() {
                // given
                when(paymentTransactionRepository.findByRepaymentScheduleId(1L)).thenReturn(List.of());

                // when
                List<PaymentTransactionResponse> responses =
                        paymentTransactionService.getByRepaymentScheduleId(1L);

                // then
                assertTrue(responses.isEmpty());
        }

        // ==========================================================================
        // Helpers
        // ==========================================================================

        private RepaymentScheduleEntity buildSchedule(Long id, BigDecimal totalAmount, String status) {
                RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
                schedule.setId(id);
                schedule.setTotalAmount(totalAmount);
                schedule.setStatus(status);
                schedule.setInstallmentNumber(1);
                schedule.setPrincipalAmount(new BigDecimal("900000"));
                schedule.setInterestAmount(new BigDecimal("100000"));
                schedule.setCreatedAt(ZonedDateTime.now());
                schedule.setUpdatedAt(ZonedDateTime.now());
                return schedule;
        }

        private PaymentTransactionEntity buildTransaction(Long id, RepaymentScheduleEntity schedule, BigDecimal paidAmount, String reference) {
                PaymentTransactionEntity entity = new PaymentTransactionEntity();
                entity.setId(id);
                entity.setRepaymentSchedule(schedule);
                entity.setPaidAmount(paidAmount);
                entity.setPaymentReference(reference);
                entity.setPaidAt(ZonedDateTime.now());
                entity.setStatus("SUCCESS");
                entity.setCreatedAt(ZonedDateTime.now());
                entity.setUpdatedAt(ZonedDateTime.now());
                return entity;
        }

        private CreatePaymentTransactionRequest buildRequest(Long scheduleId, BigDecimal paidAmount, String reference) {
                CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
                req.setRepaymentScheduleId(scheduleId);
                req.setPaidAmount(paidAmount);
                req.setPaymentReference(reference);
                req.setPaidAt(ZonedDateTime.now());
                return req;
        }
        }
