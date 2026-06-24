package com.fif.loanapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fif.loanapplication.dto.payment.CreatePaymentTransactionRequest;
import com.fif.loanapplication.dto.payment.PaymentTransactionResponse;
import com.fif.loanapplication.entity.PaymentTransactionEntity;
import com.fif.loanapplication.entity.RepaymentScheduleEntity;
import com.fif.loanapplication.entity.enums.PaymentStatus;
import com.fif.loanapplication.entity.enums.RepaymentStatus;
import com.fif.loanapplication.exception.RepaymentScheduleNotFoundException;
import com.fif.loanapplication.repository.PaymentTransactionRepository;
import com.fif.loanapplication.repository.RepaymentScheduleRepository;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @Mock
    private PaymentTransactionRepository paymentTransactionRepository;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    @Test
    void createPayments_whenPaidAmountGreaterThanOrEqualTotalAmount_shouldSetRepaymentStatusPaid() {
        // Given
        UUID repaymentScheduleUid = UUID.randomUUID();
        UUID paymentUid = UUID.randomUUID();

        CreatePaymentTransactionRequest request = createPaymentRequest(
                repaymentScheduleUid,
                BigDecimal.valueOf(1_000_000));

        RepaymentScheduleEntity repaymentSchedule = createRepaymentSchedule(
                repaymentScheduleUid,
                BigDecimal.valueOf(1_000_000));

        PaymentTransactionEntity savedPayment = createPaymentTransaction(
                paymentUid,
                repaymentSchedule,
                request.getPaidAmount());

        when(repaymentScheduleRepository.findById(repaymentScheduleUid))
                .thenReturn(Optional.of(repaymentSchedule));

        when(paymentTransactionRepository.save(any(PaymentTransactionEntity.class)))
                .thenReturn(savedPayment);

        // When
        PaymentTransactionResponse response = paymentTransactionService.createPayments(request);

        // Then
        assertEquals(paymentUid, response.getUid());
        assertEquals(repaymentScheduleUid, response.getRepaymentScheduleUid());
        assertEquals("PAY-001", response.getPaymentReference());
        assertEquals(BigDecimal.valueOf(1_000_000), response.getPaidAmount());
        assertEquals(PaymentStatus.SUCCESS, response.getStatus());

        assertEquals(RepaymentStatus.PAID, repaymentSchedule.getStatus());

        ArgumentCaptor<PaymentTransactionEntity> captor = ArgumentCaptor.forClass(PaymentTransactionEntity.class);

        verify(paymentTransactionRepository).save(captor.capture());

        PaymentTransactionEntity capturedPayment = captor.getValue();

        assertEquals(repaymentSchedule, capturedPayment.getRepaymentSchedule());
        assertEquals("PAY-001", capturedPayment.getPaymentReference());
        assertEquals(BigDecimal.valueOf(1_000_000), capturedPayment.getPaidAmount());
        assertEquals(PaymentStatus.SUCCESS, capturedPayment.getStatus());
    }

    @Test
    void createPayments_whenPaidAmountLessThanTotalAmount_shouldSetRepaymentStatusPartiallyPaid() {
        // Given
        UUID repaymentScheduleUid = UUID.randomUUID();
        UUID paymentUid = UUID.randomUUID();

        CreatePaymentTransactionRequest request = createPaymentRequest(
                repaymentScheduleUid,
                BigDecimal.valueOf(500_000));

        RepaymentScheduleEntity repaymentSchedule = createRepaymentSchedule(
                repaymentScheduleUid,
                BigDecimal.valueOf(1_000_000));

        PaymentTransactionEntity savedPayment = createPaymentTransaction(
                paymentUid,
                repaymentSchedule,
                request.getPaidAmount());

        when(repaymentScheduleRepository.findById(repaymentScheduleUid))
                .thenReturn(Optional.of(repaymentSchedule));

        when(paymentTransactionRepository.save(any(PaymentTransactionEntity.class)))
                .thenReturn(savedPayment);

        // When
        PaymentTransactionResponse response = paymentTransactionService.createPayments(request);

        // Then
        assertEquals(paymentUid, response.getUid());
        assertEquals(repaymentScheduleUid, response.getRepaymentScheduleUid());
        assertEquals(BigDecimal.valueOf(500_000), response.getPaidAmount());
        assertEquals(PaymentStatus.SUCCESS, response.getStatus());

        assertEquals(RepaymentStatus.PARTIALLY_PAID, repaymentSchedule.getStatus());

        verify(paymentTransactionRepository).save(any(PaymentTransactionEntity.class));
    }

    @Test
    void createPayments_whenRepaymentScheduleNotFound_shouldThrowRepaymentScheduleNotFoundException() {
        // Given
        UUID repaymentScheduleUid = UUID.randomUUID();

        CreatePaymentTransactionRequest request = createPaymentRequest(
                repaymentScheduleUid,
                BigDecimal.valueOf(500_000));

        when(repaymentScheduleRepository.findById(repaymentScheduleUid))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                RepaymentScheduleNotFoundException.class,
                () -> paymentTransactionService.createPayments(request));

        verify(paymentTransactionRepository, never()).save(any(PaymentTransactionEntity.class));
    }

    @Test
    void getPaymentByRepaymentUid_shouldReturnPaymentResponses() {
        // Given
        UUID repaymentScheduleUid = UUID.randomUUID();

        RepaymentScheduleEntity repaymentSchedule = createRepaymentSchedule(
                repaymentScheduleUid,
                BigDecimal.valueOf(1_000_000));

        PaymentTransactionEntity payment1 = createPaymentTransaction(
                UUID.randomUUID(),
                repaymentSchedule,
                BigDecimal.valueOf(500_000));

        PaymentTransactionEntity payment2 = createPaymentTransaction(
                UUID.randomUUID(),
                repaymentSchedule,
                BigDecimal.valueOf(500_000));

        when(paymentTransactionRepository.findByRepaymentScheduleUid(repaymentScheduleUid))
                .thenReturn(List.of(payment1, payment2));

        // When
        List<PaymentTransactionResponse> responses = paymentTransactionService
                .getPaymentByRepaymentUid(repaymentScheduleUid);

        // Then
        assertEquals(2, responses.size());
        assertEquals(repaymentScheduleUid, responses.get(0).getRepaymentScheduleUid());
        assertEquals(repaymentScheduleUid, responses.get(1).getRepaymentScheduleUid());
    }

    private CreatePaymentTransactionRequest createPaymentRequest(
            UUID repaymentScheduleUid,
            BigDecimal paidAmount) {
        return CreatePaymentTransactionRequest.builder()
                .repaymentScheduleUid(repaymentScheduleUid)
                .paymentReference("PAY-001")
                .paidAmount(paidAmount)
                .paidAt(ZonedDateTime.of(2026, 6, 23, 10, 0, 0, 0, ZoneId.of("Asia/Jakarta"))).build();
    }

    private RepaymentScheduleEntity createRepaymentSchedule(
            UUID repaymentScheduleUid,
            BigDecimal totalAmount) {
        return RepaymentScheduleEntity.builder()
                .uid(repaymentScheduleUid)
                .totalAmount(totalAmount)
                .status(RepaymentStatus.UNPAID)
                .build();
    }

    private PaymentTransactionEntity createPaymentTransaction(
            UUID paymentUid,
            RepaymentScheduleEntity repaymentSchedule,
            BigDecimal paidAmount) {
        return PaymentTransactionEntity.builder()
                .uid(paymentUid)
                .repaymentSchedule(repaymentSchedule)
                .paymentReference("PAY-001")
                .paidAmount(paidAmount)
                .paidAt(ZonedDateTime.of(2026, 6, 23, 10, 0, 0, 0, ZoneId.of("Asia/Jakarta")))
                .status(PaymentStatus.SUCCESS)
                .build();
    }
}