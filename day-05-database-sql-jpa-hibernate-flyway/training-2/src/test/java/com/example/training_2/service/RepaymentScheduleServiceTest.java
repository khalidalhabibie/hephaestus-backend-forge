package com.example.training_2.service;

import com.example.training_2.dto.RepaymentScheduleResponse;
import com.example.training_2.entity.LoanApplication;
import com.example.training_2.entity.RepaymentSchedule;
import com.example.training_2.entity.RepaymentScheduleStatus;
import com.example.training_2.repository.RepaymentScheduleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @InjectMocks
    private RepaymentScheduleService repaymentScheduleService;

    private LoanApplication loanApplication;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(
                repaymentScheduleService,
                "annualRate",
                new BigDecimal("0.12"));

        loanApplication = new LoanApplication();
        loanApplication.setId(1L);
        loanApplication.setLoanAmount(
                BigDecimal.valueOf(12000000));
        loanApplication.setTenorMonth(12);
    }

    @Test
    void generateSchedules_ShouldCreateSchedulesSuccessfully() {

        repaymentScheduleService.generateSchedules(
                loanApplication);

        ArgumentCaptor<List<RepaymentSchedule>> captor = ArgumentCaptor.forClass(List.class);

        verify(repaymentScheduleRepository)
                .saveAll(captor.capture());

        List<RepaymentSchedule> schedules = captor.getValue();

        assertEquals(12, schedules.size());

        RepaymentSchedule first = schedules.get(0);

        assertEquals(
                1,
                first.getInstallmentNumber());

        assertEquals(
                RepaymentScheduleStatus.UNPAID,
                first.getStatus());

        assertEquals(
                BigDecimal.valueOf(1000000.00)
                        .setScale(2),
                first.getPrincipalAmount());
    }

    @Test
    void generateSchedules_ShouldGenerateCorrectInstallmentNumbers() {

        repaymentScheduleService.generateSchedules(
                loanApplication);

        ArgumentCaptor<List<RepaymentSchedule>> captor = ArgumentCaptor.forClass(List.class);

        verify(repaymentScheduleRepository)
                .saveAll(captor.capture());

        List<RepaymentSchedule> schedules = captor.getValue();

        assertEquals(
                1,
                schedules.get(0)
                        .getInstallmentNumber());

        assertEquals(
                12,
                schedules.get(11)
                        .getInstallmentNumber());
    }

    @Test
    void getById_ShouldReturnRepaymentSchedule() {

        RepaymentSchedule schedule = new RepaymentSchedule();

        schedule.setId(1L);
        schedule.setLoanApplication(
                loanApplication);

        schedule.setInstallmentNumber(1);

        schedule.setPrincipalAmount(
                BigDecimal.valueOf(1000000));

        schedule.setInterestAmount(
                BigDecimal.valueOf(120000));

        schedule.setTotalAmount(
                BigDecimal.valueOf(1120000));

        schedule.setStatus(
                RepaymentScheduleStatus.UNPAID);

        when(repaymentScheduleRepository.findById(1L))
                .thenReturn(Optional.of(schedule));

        RepaymentScheduleResponse response = repaymentScheduleService.getById(1L);

        assertNotNull(response);

        assertEquals(
                1L,
                response.getId());

        assertEquals(
                1L,
                response.getLoanApplicationId());

        assertEquals(
                1,
                response.getInstallmentNumber());

        assertEquals(
                "UNPAID",
                response.getStatus());
    }

    @Test
    void getById_ShouldThrowException_WhenScheduleNotFound() {

        when(repaymentScheduleRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> repaymentScheduleService.getById(1L));

        assertEquals(
                "Repayment schedule not found",
                exception.getMessage());
    }
}