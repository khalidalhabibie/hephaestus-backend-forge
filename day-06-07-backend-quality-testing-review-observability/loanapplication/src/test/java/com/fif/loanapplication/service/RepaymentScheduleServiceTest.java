package com.fif.loanapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fif.loanapplication.dto.payment.RepaymentScheduleResponse;
import com.fif.loanapplication.entity.LoanApplicationEntity;
import com.fif.loanapplication.entity.RepaymentScheduleEntity;
import com.fif.loanapplication.entity.enums.RepaymentStatus;
import com.fif.loanapplication.exception.RepaymentScheduleNotFoundException;
import com.fif.loanapplication.repository.RepaymentScheduleRepository;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @InjectMocks
    private RepaymentScheduleService repaymentScheduleService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(
                repaymentScheduleService,
                "annualInteresRate",
                BigDecimal.valueOf(0.12));
    }

    @Test
    void generateRepaymentSchedules_shouldGenerateSchedulesCorrectly() {

        // Given
        LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
                .uid(UUID.randomUUID())
                .loanAmount(BigDecimal.valueOf(12_000_000))
                .tenorMonth(12)
                .build();

        // When
        List<RepaymentScheduleEntity> schedules = repaymentScheduleService.generateRepaymentSchedules(loanApplication);

        // Then
        assertEquals(12, schedules.size());

        RepaymentScheduleEntity firstSchedule = schedules.get(0);

        assertEquals(1, firstSchedule.getInstallmentNumber());
        assertEquals(
                BigDecimal.valueOf(1_000_000).setScale(2),
                firstSchedule.getPrincipalAmount());

        assertEquals(
                BigDecimal.valueOf(120_000).setScale(2),
                firstSchedule.getInterestAmount());

        assertEquals(
                BigDecimal.valueOf(1_120_000).setScale(2),
                firstSchedule.getTotalAmount());

        assertEquals(
                RepaymentStatus.UNPAID,
                firstSchedule.getStatus());

        assertEquals(
                LocalDate.now().plusMonths(1),
                firstSchedule.getDueDate());

        assertEquals(
                LocalDate.now().plusMonths(12),
                schedules.get(11).getDueDate());
    }

    @Test
    void getRepaymentScheduleByUid_shouldReturnResponse() {

        // Given
        UUID uid = UUID.randomUUID();

        RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
                .uid(uid)
                .installmentNumber(1)
                .principalAmount(BigDecimal.valueOf(1_000_000))
                .interestAmount(BigDecimal.valueOf(120_000))
                .totalAmount(BigDecimal.valueOf(1_120_000))
                .status(RepaymentStatus.UNPAID)
                .dueDate(LocalDate.now())
                .build();

        when(repaymentScheduleRepository.findById(uid))
                .thenReturn(Optional.of(schedule));

        // When
        RepaymentScheduleResponse response = repaymentScheduleService.getRepaymentScheduleByUid(uid);

        // Then
        assertEquals(uid, response.getUid());
        assertEquals(1, response.getInstallmentNumber());
        assertEquals(RepaymentStatus.UNPAID, response.getStatus());
        assertEquals(
                BigDecimal.valueOf(1_120_000),
                response.getTotalAmount());
    }

    @Test
    void getRepaymentScheduleByUid_shouldThrowExceptionWhenNotFound() {

        // Given
        UUID uid = UUID.randomUUID();

        when(repaymentScheduleRepository.findById(uid))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                RepaymentScheduleNotFoundException.class,
                () -> repaymentScheduleService.getRepaymentScheduleByUid(uid));
    }

    @Test
    void getRepaymentScheduleByLoanApplicationUid_shouldReturnResponses() {

        // Given
        UUID loanApplicationUid = UUID.randomUUID();

        RepaymentScheduleEntity schedule1 = RepaymentScheduleEntity.builder()
                .uid(UUID.randomUUID())
                .installmentNumber(1)
                .principalAmount(BigDecimal.valueOf(1_000_000))
                .interestAmount(BigDecimal.valueOf(120_000))
                .totalAmount(BigDecimal.valueOf(1_120_000))
                .status(RepaymentStatus.UNPAID)
                .dueDate(LocalDate.now())
                .build();

        RepaymentScheduleEntity schedule2 = RepaymentScheduleEntity.builder()
                .uid(UUID.randomUUID())
                .installmentNumber(2)
                .principalAmount(BigDecimal.valueOf(1_000_000))
                .interestAmount(BigDecimal.valueOf(120_000))
                .totalAmount(BigDecimal.valueOf(1_120_000))
                .status(RepaymentStatus.UNPAID)
                .dueDate(LocalDate.now().plusMonths(1))
                .build();

        when(repaymentScheduleRepository.findByLoanApplicationUid(loanApplicationUid))
                .thenReturn(List.of(schedule1, schedule2));

        // When
        List<RepaymentScheduleResponse> responses = repaymentScheduleService.getRepaymentScheduleByLoanApplicationUid(
                loanApplicationUid);

        // Then
        assertEquals(2, responses.size());

        assertEquals(1, responses.get(0).getInstallmentNumber());
        assertEquals(2, responses.get(1).getInstallmentNumber());

        assertEquals(
                BigDecimal.valueOf(1_120_000),
                responses.get(0).getTotalAmount());

        assertEquals(
                BigDecimal.valueOf(1_120_000),
                responses.get(1).getTotalAmount());
    }
}