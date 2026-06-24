package com.example.jpabackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.repository.*;
import com.example.jpabackend.service.PaymentTransactionService;
import com.example.jpabackend.entity.*;
import com.example.jpabackend.exception.*;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @InjectMocks
    private PaymentTransactionService service;

    @Mock
    private PaymentTransactionRepository paymentRepo;

    @Mock
    private RepaymentScheduleRepository repaymentRepo;

    @Test
    void should_create_payment_successfully() {
        // given
        CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
        req.setRepaymentScheduleId(1L);
        req.setPaymentReference("REF123");
        req.setPaidAmount(BigDecimal.valueOf(100));
        req.setPaidAt(ZonedDateTime.now());

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(10L);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setId(1L);
        schedule.setStatus("UNPAID");
        schedule.setTotalAmount(BigDecimal.valueOf(100));
        schedule.setLoanApplication(loan);

        when(repaymentRepo.findById(1L)).thenReturn(Optional.of(schedule));
        when(paymentRepo.existsByRepaymentSchedule_LoanApplication_IdAndPaymentReference(10L, "REF123"))
                .thenReturn(false);
        when(paymentRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        // when
        PaymentTransactionResponse result = service.createPayment(req);

        // then
        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("PAID", schedule.getStatus()); // penting!
        verify(paymentRepo).save(any());
    }

    @Test
    void should_throw_not_found_when_schedule_not_exist() {
        // given
        CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
        req.setRepaymentScheduleId(1L);

        when(repaymentRepo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RepaymentScheduleNotFoundException.class,
                () -> service.createPayment(req));
    }

    @Test
    void should_throw_duplicate_reference() {
        // given
        CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
        req.setRepaymentScheduleId(1L);
        req.setPaymentReference("REF123");

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(10L);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setLoanApplication(loan);

        when(repaymentRepo.findById(1L)).thenReturn(Optional.of(schedule));
        when(paymentRepo.existsByRepaymentSchedule_LoanApplication_IdAndPaymentReference(10L, "REF123"))
                .thenReturn(true);

        // when & then
        assertThrows(RuntimeException.class,
                () -> service.createPayment(req));

        verify(paymentRepo, never()).save(any());
    }

    @Test
    void should_throw_when_schedule_already_paid() {
        // given
        CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
        req.setRepaymentScheduleId(1L);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(10L);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setStatus("PAID");
        schedule.setLoanApplication(loan);

        when(repaymentRepo.findById(1L)).thenReturn(Optional.of(schedule));
        when(paymentRepo.existsByRepaymentSchedule_LoanApplication_IdAndPaymentReference(any(), any()))
                .thenReturn(false);

        // when & then
        assertThrows(RuntimeException.class,
                () -> service.createPayment(req));
    }

    @Test
    void should_throw_invalid_payment_amount() {
        // given
        CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
        req.setRepaymentScheduleId(1L);
        req.setPaidAmount(BigDecimal.valueOf(50));

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(10L);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setStatus("UNPAID");
        schedule.setTotalAmount(BigDecimal.valueOf(100));
        schedule.setLoanApplication(loan);

        when(repaymentRepo.findById(1L)).thenReturn(Optional.of(schedule));
        when(paymentRepo.existsByRepaymentSchedule_LoanApplication_IdAndPaymentReference(any(), any()))
                .thenReturn(false);

        // when & then
        assertThrows(RuntimeException.class,
                () -> service.createPayment(req));
    }

    @Test
    void should_get_payments_by_schedule_id() {
        // given
        when(repaymentRepo.findById(1L))
                .thenReturn(Optional.of(new RepaymentScheduleEntity()));

        when(paymentRepo.findByRepaymentScheduleId(1L))
                .thenReturn(List.of(new PaymentTransactionEntity()));

        // when
        List<PaymentTransactionResponse> result = service.getByScheduleId(1L);

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_throw_not_found_when_get_schedule_payments() {
        // given
        when(repaymentRepo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RepaymentScheduleNotFoundException.class,
                () -> service.getByScheduleId(1L));
    }

    @Test
    void should_get_total_paid() {
        // given
        when(repaymentRepo.findById(1L))
                .thenReturn(Optional.of(new RepaymentScheduleEntity()));

        when(paymentRepo.sumPaidAmount(1L))
                .thenReturn(BigDecimal.valueOf(200));

        // when
        BigDecimal result = service.getTotalPaid(1L);

        // then
        assertEquals(BigDecimal.valueOf(200), result);
    }
}
