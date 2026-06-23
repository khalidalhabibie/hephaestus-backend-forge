package com.example.training_2.service;

import com.example.training_2.dto.CreatePaymentTransactionRequest;
import com.example.training_2.dto.PaymentTransactionResponse;
import com.example.training_2.dto.PaymentTransactionStatus;
import com.example.training_2.entity.PaymentTransaction;
import com.example.training_2.entity.RepaymentSchedule;
import com.example.training_2.entity.RepaymentScheduleStatus;
import com.example.training_2.repository.PaymentTransactionRepository;
import com.example.training_2.repository.RepaymentScheduleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @Mock
    private PaymentTransactionRepository paymentTransactionRepository;

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    private RepaymentSchedule schedule;

    @BeforeEach
    void setUp() {

        schedule = new RepaymentSchedule();
        schedule.setId(1L);
        schedule.setTotalAmount(BigDecimal.valueOf(1_000_000));
        schedule.setStatus(RepaymentScheduleStatus.UNPAID);
    }

    @Test
    void create_ShouldCreatePaymentSuccessfully() {

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();

        request.setRepaymentScheduleId(1L);
        request.setPaymentReference("PAY-001");
        request.setPaidAmount(BigDecimal.valueOf(1_000_000));

        PaymentTransaction transaction = new PaymentTransaction();

        transaction.setId(1L);
        transaction.setRepaymentSchedule(schedule);
        transaction.setPaymentReference("PAY-001");
        transaction.setPaidAmount(BigDecimal.valueOf(1_000_000));
        transaction.setPaidAt(LocalDateTime.now());
        transaction.setStatus(PaymentTransactionStatus.SUCCESS);

        when(repaymentScheduleRepository.findById(1L))
                .thenReturn(Optional.of(schedule));

        when(paymentTransactionRepository.save(any()))
                .thenReturn(transaction);

        when(paymentTransactionRepository
                .sumPaidAmountByScheduleId(1L))
                .thenReturn(BigDecimal.valueOf(1_000_000));

        PaymentTransactionResponse response = paymentTransactionService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("PAY-001",
                response.getPaymentReference());

        verify(paymentTransactionRepository)
                .save(any(PaymentTransaction.class));

        verify(repaymentScheduleRepository)
                .save(any(RepaymentSchedule.class));
    }

    @Test
    void create_ShouldThrowException_WhenScheduleNotFound() {

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();

        request.setRepaymentScheduleId(99L);

        when(repaymentScheduleRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> paymentTransactionService.create(request));

        assertEquals(
                "Repayment Schedule not found",
                exception.getMessage());

        verify(paymentTransactionRepository, never())
                .save(any());
    }

    @Test
    void create_ShouldUpdateScheduleStatusToPaid() {

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();

        request.setRepaymentScheduleId(1L);
        request.setPaidAmount(BigDecimal.valueOf(1_000_000));
        request.setPaymentReference("PAY-001");

        PaymentTransaction transaction = new PaymentTransaction();

        transaction.setId(1L);
        transaction.setRepaymentSchedule(schedule);

        when(repaymentScheduleRepository.findById(1L))
                .thenReturn(Optional.of(schedule));

        when(paymentTransactionRepository.save(any()))
                .thenReturn(transaction);

        when(paymentTransactionRepository
                .sumPaidAmountByScheduleId(1L))
                .thenReturn(BigDecimal.valueOf(1_000_000));

        paymentTransactionService.create(request);

        ArgumentCaptor<RepaymentSchedule> captor = ArgumentCaptor.forClass(
                RepaymentSchedule.class);

        verify(repaymentScheduleRepository)
                .save(captor.capture());

        assertEquals(
                RepaymentScheduleStatus.PAID,
                captor.getValue().getStatus());
    }

    @Test
    void create_ShouldUpdateScheduleStatusToPartial() {

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();

        request.setRepaymentScheduleId(1L);
        request.setPaidAmount(BigDecimal.valueOf(300_000));
        request.setPaymentReference("PAY-001");

        PaymentTransaction transaction = new PaymentTransaction();

        transaction.setId(1L);
        transaction.setRepaymentSchedule(schedule);

        when(repaymentScheduleRepository.findById(1L))
                .thenReturn(Optional.of(schedule));

        when(paymentTransactionRepository.save(any()))
                .thenReturn(transaction);

        when(paymentTransactionRepository
                .sumPaidAmountByScheduleId(1L))
                .thenReturn(BigDecimal.valueOf(300_000));

        paymentTransactionService.create(request);

        ArgumentCaptor<RepaymentSchedule> captor = ArgumentCaptor.forClass(
                RepaymentSchedule.class);

        verify(repaymentScheduleRepository)
                .save(captor.capture());

        assertEquals(
                RepaymentScheduleStatus.PARTIAL,
                captor.getValue().getStatus());
    }

    @Test
    void create_ShouldUpdateScheduleStatusToUnpaid() {

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();

        request.setRepaymentScheduleId(1L);
        request.setPaidAmount(BigDecimal.ZERO);
        request.setPaymentReference("PAY-001");

        PaymentTransaction transaction = new PaymentTransaction();

        transaction.setId(1L);
        transaction.setRepaymentSchedule(schedule);

        when(repaymentScheduleRepository.findById(1L))
                .thenReturn(Optional.of(schedule));

        when(paymentTransactionRepository.save(any()))
                .thenReturn(transaction);

        when(paymentTransactionRepository
                .sumPaidAmountByScheduleId(1L))
                .thenReturn(BigDecimal.ZERO);

        paymentTransactionService.create(request);

        ArgumentCaptor<RepaymentSchedule> captor = ArgumentCaptor.forClass(
                RepaymentSchedule.class);

        verify(repaymentScheduleRepository)
                .save(captor.capture());

        assertEquals(
                RepaymentScheduleStatus.UNPAID,
                captor.getValue().getStatus());
    }
}