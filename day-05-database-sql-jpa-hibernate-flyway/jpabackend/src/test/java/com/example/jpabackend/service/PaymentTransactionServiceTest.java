package com.example.jpabackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.example.jpabackend.dto.request.CreatePaymentTransactionRequest;
import com.example.jpabackend.dto.response.PaymentTransactionResponse;
import com.example.jpabackend.entity.PaymentTransactionEntity;
import com.example.jpabackend.entity.RepaymentScheduleEntity;
import com.example.jpabackend.enums.PaymentStatus;
import com.example.jpabackend.enums.RepaymentStatus;
import com.example.jpabackend.exception.PaymentTransactionNotFoundException;
import com.example.jpabackend.exception.RepaymentScheduleNotFoundException;
import com.example.jpabackend.repository.PaymentTransactionRepository;
import com.example.jpabackend.repository.RepaymentScheduleRepository;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class PaymentTransactionServiceTest {
    // @Test
    // void should_create_loan_application_successfully() {
    // Long customerId = 1L;
    // CreateLoanApplicationRequest request = CreateLoanApplicationRequest.builder()
    //     .customerId(customerId)
    //     .loanAmount(java.math.BigDecimal.valueOf(1000000))
    //     .tenorMonth(12)
    //     .purpose("Business")
    //     .build();
    // CustomerEntity customer = CustomerEntity.builder()
    //     .fullName("Edith")
    //     .nik("123")
    //     .email("edith@mail.com")
    //     .phoneNumber("08123")
    //     .build();
    //     customer.setId(customerId);
    // LoanApplicationEntity savedLoan = LoanApplicationEntity.builder()
    //     .customer(customer)
    //     .loanAmount(request.getLoanAmount())
    //     .tenorMonth(request.getTenorMonth())
    //     .purpose(request.getPurpose())
    //     .status(com.example.jpabackend.enums.LoanStatus.SUBMITTED)
    //     .build();
    //     savedLoan.setId(10L);
    // when(customerRepository.findById(customerId))
    //     .thenReturn(java.util.Optional.of(customer));
    // when(loanApplicationRepository.save(any()))
    //     .thenReturn(savedLoan);
    // LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
    // assertNotNull(response);
    // assertEquals(10L, response.getId());
    // assertEquals("Business", response.getPurpose());
    // verify(loanApplicationRepository).save(any());
    // }

    @Mock
    private PaymentTransactionRepository paymentTransactionRepository;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    @Test
    void should_create_payment_transaction_successfully() {
    Long scheduleId = 1L;
    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(900))
        .interestAmount(BigDecimal.valueOf(100))
        .totalAmount(BigDecimal.valueOf(1000))
        .status(RepaymentStatus.UNPAID)
        .build();
        schedule.setId(scheduleId);
    CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
        .repaymentScheduleId(scheduleId)
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(500))
        .paidAt(LocalDateTime.now())
        .build();
    PaymentTransactionEntity saved = PaymentTransactionEntity.builder()
        .repaymentSchedule(schedule)
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(500))
        .paidAt(request.getPaidAt())
        .status(PaymentStatus.SUCCESS)
        .build();
        saved.setId(1L);
    when(repaymentScheduleRepository.findById(scheduleId))
        .thenReturn(Optional.of(schedule));
    when(paymentTransactionRepository.save(any(PaymentTransactionEntity.class)))
        .thenReturn(saved);
    when(paymentTransactionRepository.sumPaidAmountByScheduleId(scheduleId))
        .thenReturn(BigDecimal.valueOf(500));
    PaymentTransactionResponse response = paymentTransactionService.createPaymentTransaction(request);
    assertEquals(1L, response.getId());
    assertEquals("PAY-001", response.getPaymentReference());

    verify(paymentTransactionRepository).save(any(PaymentTransactionEntity.class));
    verify(repaymentScheduleRepository, never()).save(any());
    }

    @Test
    void should_mark_schedule_paid_when_total_paid_reaches_total_amount() {
    Long scheduleId = 1L;
    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(900))
        .interestAmount(BigDecimal.valueOf(100))
        .totalAmount(BigDecimal.valueOf(1000))
        .status(RepaymentStatus.UNPAID)
        .build();
        schedule.setId(scheduleId);
    CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
        .repaymentScheduleId(scheduleId)
        .paymentReference("PAY-002")
        .paidAmount(BigDecimal.valueOf(1000))
        .paidAt(LocalDateTime.now())
        .build();
    PaymentTransactionEntity saved = PaymentTransactionEntity.builder()
        .repaymentSchedule(schedule)
        .paymentReference("PAY-002")
        .paidAmount(BigDecimal.valueOf(1000))
        .paidAt(request.getPaidAt())
        .status(PaymentStatus.SUCCESS)
        .build();
        saved.setId(2L);
    when(repaymentScheduleRepository.findById(scheduleId))
        .thenReturn(Optional.of(schedule));
    when(paymentTransactionRepository.save(any(PaymentTransactionEntity.class)))
        .thenReturn(saved);
    when(paymentTransactionRepository.sumPaidAmountByScheduleId(scheduleId))
        .thenReturn(BigDecimal.valueOf(1000));
        paymentTransactionService.createPaymentTransaction(request);
    assertEquals(RepaymentStatus.PAID, schedule.getStatus());
    verify(repaymentScheduleRepository).save(schedule);
    }

    @Test
    void should_throw_not_found_when_repayment_schedule_does_not_exist() {
    Long scheduleId = 99L;
    CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
        .repaymentScheduleId(scheduleId)
        .paymentReference("PAY-003")
        .paidAmount(BigDecimal.valueOf(100))
        .paidAt(LocalDateTime.now())
        .build();
    when(repaymentScheduleRepository.findById(scheduleId))
        .thenReturn(Optional.empty());
    assertThrows(RepaymentScheduleNotFoundException.class, () -> paymentTransactionService.createPaymentTransaction(request));
    }

    @Test
    void should_return_payments_by_schedule_successfully() {
    Long scheduleId = 1L;
    PaymentTransactionEntity payment = PaymentTransactionEntity.builder()
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(500))
        .paidAt(LocalDateTime.now())
        .status(PaymentStatus.SUCCESS)
        .build();
        payment.setId(1L);
    when(repaymentScheduleRepository.existsById(scheduleId))
        .thenReturn(true);

    when(paymentTransactionRepository.findByRepaymentScheduleId(scheduleId))
        .thenReturn(List.of(payment));
    List<PaymentTransactionResponse> result = paymentTransactionService.getPaymentsBySchedule(scheduleId);
    assertEquals(1, result.size());
    assertEquals("PAY-001", result.get(0).getPaymentReference());
    }

    @Test
    void should_return_empty_list_when_no_payments_found() {
    Long scheduleId = 1L;
    when(repaymentScheduleRepository.existsById(scheduleId))
            .thenReturn(true);
    when(paymentTransactionRepository.findByRepaymentScheduleId(scheduleId))
            .thenReturn(List.of());
    List<PaymentTransactionResponse> result = paymentTransactionService.getPaymentsBySchedule(scheduleId);
    assertEquals(0, result.size());
    }

    @Test
    void should_throw_not_found_when_schedule_for_get_payment_not_found() {
    Long scheduleId = 99L;
    when(repaymentScheduleRepository.existsById(scheduleId))
        .thenReturn(false);

    assertThrows(RepaymentScheduleNotFoundException.class, () -> paymentTransactionService.getPaymentsBySchedule(scheduleId));
    }

    @Test
    void should_delete_payment_successfully() {
    Long paymentId = 1L;
    PaymentTransactionEntity payment = PaymentTransactionEntity.builder()
        .paymentReference("PAY-001")
        .paidAmount(BigDecimal.valueOf(500))
        .status(PaymentStatus.SUCCESS)
        .build();
        payment.setId(paymentId);
    when(paymentTransactionRepository.findById(paymentId))
        .thenReturn(Optional.of(payment));
        paymentTransactionService.deletePayment(paymentId);
    assertEquals(true, payment.isDeleted());
    verify(paymentTransactionRepository).save(payment);
    }

    @Test
    void should_throw_not_found_when_payment_not_found() {
    Long paymentId = 99L;
    when(paymentTransactionRepository.findById(paymentId))
        .thenReturn(Optional.empty());
    assertThrows(PaymentTransactionNotFoundException.class, () -> paymentTransactionService.deletePayment(paymentId));
    }
}