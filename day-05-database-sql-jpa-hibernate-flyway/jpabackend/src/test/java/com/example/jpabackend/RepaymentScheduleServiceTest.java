package com.example.jpabackend;

import org.springframework.test.util.ReflectionTestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.repository.*;
import com.example.jpabackend.service.RepaymentScheduleService;
import com.example.jpabackend.entity.*;
import com.example.jpabackend.exception.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

    @InjectMocks
    private RepaymentScheduleService service;

    @Mock
    private RepaymentScheduleRepository repaymentRepo;

    @Mock
    private LoanApplicationRepository loanRepo;

    @Test
    void should_get_schedule_by_loan_id_successfully() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setLoanApplication(loan);

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));
        when(repaymentRepo.findByLoanApplicationId(1L))
                .thenReturn(List.of(schedule));

        // when
        List<RepaymentScheduleResponse> result = service.getByLoanId(1L);

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_throw_not_found_when_loan_not_exist() {
        // given
        when(loanRepo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(LoanApplicationNotFoundException.class,
                () -> service.getByLoanId(1L));
    }

    @Test
    void should_get_schedule_by_id_successfully() {
        // given
        RepaymentScheduleEntity r = new RepaymentScheduleEntity();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(10L);
        r.setLoanApplication(loan);

        when(repaymentRepo.findByIdWithLoan(1L))
                .thenReturn(Optional.of(r));

        // when
        RepaymentScheduleResponse result = service.getById(1L);

        // then
        assertNotNull(result);
    }

    @Test
    void should_throw_not_found_when_schedule_not_exist() {
        when(repaymentRepo.findByIdWithLoan(1L))
                .thenReturn(Optional.empty());

        assertThrows(RepaymentScheduleNotFoundException.class,
                () -> service.getById(1L));
    }

    @Test
    void should_return_exists_by_loan_id() {
        // given
        when(repaymentRepo.existsByLoanApplication_Id(1L)).thenReturn(true);

        // when
        boolean result = service.existsByLoanId(1L);

        // then
        assertTrue(result);
    }

    @Test
    void should_generate_schedule_successfully() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setTenorMonth(2);
        loan.setLoanAmount(BigDecimal.valueOf(1000));

        ReflectionTestUtils.setField(service, "annualInterestRate", BigDecimal.valueOf(0.12));

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));
        when(repaymentRepo.existsByLoanApplication_Id(1L)).thenReturn(false);

        // when
        service.generateSchedule(1L);

        // then
        verify(repaymentRepo, times(2)).save(any());
    }

    @Test
    void should_not_generate_when_schedule_already_exists() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));
        when(repaymentRepo.existsByLoanApplication_Id(1L)).thenReturn(true);

        // when
        service.generateSchedule(1L);

        // then
        verify(repaymentRepo, never()).save(any());
    }

    @Test
    void should_get_by_status() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setLoanApplication(loan);

        when(repaymentRepo.findByStatus("PAID"))
                .thenReturn(List.of(schedule));

        // when
        List<RepaymentScheduleResponse> result = service.getByStatus("PAID");

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_get_all_schedules() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setLoanApplication(loan);

        when(repaymentRepo.findAll())
                .thenReturn(List.of(schedule));

        // when
        List<RepaymentScheduleResponse> result = service.getAll();

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_throw_not_found_when_generate_schedule_loan_not_exist() {
        // given
        when(loanRepo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(LoanApplicationNotFoundException.class,
                () -> service.generateSchedule(1L));
    }

    @Test
    void should_return_false_when_loan_not_exist_in_schedule() {
        // given
        when(repaymentRepo.existsByLoanApplication_Id(1L)).thenReturn(false);

        // when
        boolean result = service.existsByLoanId(1L);

        // then
        assertFalse(result);
    }

    @Test
    void should_get_outstanding_per_customer() {

        // given
        List<Object[]> data = java.util.Arrays.<Object[]>asList(
                new Object[] { 1L, BigDecimal.valueOf(500) });    

        when(repaymentRepo.getOutstandingByCustomer())
                .thenReturn(data);

        // when
        List<Map<String, Object>> result = service.getOutstandingPerCustomer();

        // then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).get("customer_id"));
        assertEquals(BigDecimal.valueOf(500), result.get(0).get("outstanding_amount"));
    }

    @Test
    void should_handle_null_outstanding_amount() {
        // given
        List<Object[]> data = java.util.Arrays.<Object[]>asList(
                new Object[] { 1L, null });

        when(repaymentRepo.getOutstandingByCustomer())
                .thenReturn(data);

        // when
        List<Map<String, Object>> result = service.getOutstandingPerCustomer();

        // then
        assertEquals(BigDecimal.ZERO, result.get(0).get("outstanding_amount"));
    }
}
