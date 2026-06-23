package com.adnan.loanappspringsql.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adnan.loanappspringsql.dto.CreatePaymentTransactionRequest;
import com.adnan.loanappspringsql.dto.PaymentTransactionResponse;
import com.adnan.loanappspringsql.enums.PaymentStatusEnum;
import com.adnan.loanappspringsql.enums.RepaymentStatusEnum;
import com.adnan.loanappspringsql.exception.BadRequestException;
import com.adnan.loanappspringsql.exception.NotFoundException;
import com.adnan.loanappspringsql.model.PaymentTransaction;
import com.adnan.loanappspringsql.model.RepaymentSchedule;
import com.adnan.loanappspringsql.repository.PaymentTransactionRepository;
import com.adnan.loanappspringsql.repository.RepaymentScheduleRepository;
import com.adnan.loanappspringsql.service.impl.PaymentTransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {
  @Mock
  private PaymentTransactionRepository paymentTransactionRepository;
  @Mock
  private RepaymentScheduleRepository repaymentScheduleRepository;

  @InjectMocks
  private PaymentTransactionServiceImpl paymentTransactionService;

  @Test
  void create_shouldCreatePaymentSuccessfully() {
    // Given
    CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
        .repaymentScheduleId(1L)
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(1_000_000))
        .paidAt(ZonedDateTime.now())
        .build();
    RepaymentSchedule schedule = RepaymentSchedule.builder()
        .id(1L)
        .status(RepaymentStatusEnum.UNPAID)
        .totalAmount(BigDecimal.valueOf(5_000_000))
        .build();
    PaymentTransaction payment = PaymentTransaction.builder()
        .id(1L)
        .repaymentSchedule(schedule)
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(1_000_000))
        .status(PaymentStatusEnum.SUCCESS)
        .build();
    when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
    when(paymentTransactionRepository.sumPaidAmountByScheduleId(1L)).thenReturn(BigDecimal.ZERO)
        .thenReturn(BigDecimal.valueOf(1_000_000));
    when(paymentTransactionRepository.save(any())).thenReturn(payment);

    // When
    PaymentTransactionResponse response = paymentTransactionService.create(request);

    // Then
    assertNotNull(response);
    assertEquals(PaymentStatusEnum.SUCCESS, response.getStatus());

    verify(paymentTransactionRepository).save(any());
    verify(repaymentScheduleRepository, never()).save(any());
  }

  @Test
  void create_shouldMarkRepaymentAsPaid() {
    // Given
    CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
        .repaymentScheduleId(1L)
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(5_000_000))
        .paidAt(ZonedDateTime.now())
        .build();
    RepaymentSchedule schedule = RepaymentSchedule.builder()
        .id(1L)
        .status(RepaymentStatusEnum.UNPAID)
        .totalAmount(BigDecimal.valueOf(5_000_000))
        .build();
    PaymentTransaction payment = PaymentTransaction.builder()
        .id(1L)
        .repaymentSchedule(schedule)
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(5_000_000))
        .status(PaymentStatusEnum.SUCCESS)
        .build();
    when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
    when(paymentTransactionRepository.sumPaidAmountByScheduleId(1L)).thenReturn(BigDecimal.ZERO)
        .thenReturn(BigDecimal.valueOf(5_000_000));
    when(paymentTransactionRepository.save(any())).thenReturn(payment);

    // When
    PaymentTransactionResponse response = paymentTransactionService.create(request);

    // Then
    assertEquals(PaymentStatusEnum.SUCCESS, response.getStatus());

    verify(repaymentScheduleRepository).save(schedule);

    assertEquals(RepaymentStatusEnum.PAID, schedule.getStatus());
  }

  @Test
  void create_shouldThrowNotFoundException() {
    // Given
    CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
        .repaymentScheduleId(1L)
        .build();
    when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.empty());

    // When & Then
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> paymentTransactionService.create(request));

    assertEquals("Repayment schedule not found with id: 1", exception.getMessage());

    verifyNoInteractions(paymentTransactionRepository);
  }

  @Test
  void create_shouldThrowBadRequestWhenAlreadyPaid() {
    // Given
    CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
        .repaymentScheduleId(1L)
        .build();
    RepaymentSchedule schedule = RepaymentSchedule.builder()
        .id(1L)
        .status(RepaymentStatusEnum.PAID)
        .build();
    when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

    // When & Then
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> paymentTransactionService.create(request));

    assertEquals("Repayment schedule has already been paid", exception.getMessage());
  }

  @Test
  void create_shouldThrowBadRequestWhenPaidAmountExceedsOutstanding() {
    // Given
    CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
        .repaymentScheduleId(1L)
        .paidAmount(BigDecimal.valueOf(6_000_000))
        .build();
    RepaymentSchedule schedule = RepaymentSchedule.builder()
        .id(1L)
        .status(RepaymentStatusEnum.UNPAID)
        .totalAmount(BigDecimal.valueOf(5_000_000))
        .build();
    when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
    when(paymentTransactionRepository.sumPaidAmountByScheduleId(1L)).thenReturn(BigDecimal.ZERO);

    // When & Then
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> paymentTransactionService.create(request));

    assertEquals("Paid amount exceeds remaining outstanding amount", exception.getMessage());
  }

  @Test
  void findByRepaymentScheduleId_shouldReturnPaymentsSuccessfully() {
    // Given
    PaymentTransaction payment = PaymentTransaction.builder()
        .id(1L)
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(2_000_000))
        .status(PaymentStatusEnum.SUCCESS)
        .build();
    when(paymentTransactionRepository.findByRepaymentScheduleId(1L)).thenReturn(List.of(payment));

    // When
    List<PaymentTransactionResponse> responses = paymentTransactionService.findByRepaymentScheduleId(1L);

    // Then
    assertEquals(1, responses.size());

    verify(paymentTransactionRepository).findByRepaymentScheduleId(1L);
  }

  @Test
  void findByRepaymentScheduleId_shouldReturnEmptyList() {
    // Given
    when(paymentTransactionRepository.findByRepaymentScheduleId(1L)).thenReturn(Collections.emptyList());

    // When
    List<PaymentTransactionResponse> responses = paymentTransactionService.findByRepaymentScheduleId(1L);

    // Then
    assertTrue(responses.isEmpty());
  }
}
