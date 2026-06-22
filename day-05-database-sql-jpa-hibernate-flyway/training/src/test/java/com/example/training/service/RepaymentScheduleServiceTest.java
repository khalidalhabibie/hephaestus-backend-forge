package com.example.training.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.training.dto.RepaymentScheduleResponse;
import com.example.training.entity.LoanApplicationEntity;
import com.example.training.enums.LoanStatus;
import com.example.training.entity.RepaymentScheduleEntity;
import com.example.training.enums.RepaymentStatus;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.LoanApplicationRepository;
import com.example.training.repository.RepaymentScheduleRepository;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private RepaymentScheduleService repaymentScheduleService;

    private RepaymentScheduleEntity schedule;
    private LoanApplicationEntity loan;
    private UUID scheduleId;
    private UUID loanId;

    @BeforeEach
    void setUp() {
        // Inject @Value field since Mockito doesn't process Spring annotations
        ReflectionTestUtils.setField(repaymentScheduleService, "annualInterestRate", new BigDecimal("0.12"));

        scheduleId = UUID.randomUUID();
        loanId = UUID.randomUUID();

        loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setLoanAmount(new BigDecimal("12000000"));
        loan.setTenorMonth(12);
        loan.setStatus(LoanStatus.DISBURSED);

        schedule = new RepaymentScheduleEntity();
        schedule.setId(scheduleId);
        schedule.setLoanApplication(loan);
        schedule.setInstallmentNumber(1);
        schedule.setDueDate(LocalDate.of(2026, 7, 19));
        schedule.setPrincipalAmount(new BigDecimal("1000000"));
        schedule.setInterestAmount(new BigDecimal("100000"));
        schedule.setTotalAmount(new BigDecimal("1100000"));
        schedule.setStatus(RepaymentStatus.UNPAID);
    }

    // === read tests ===

    @Test
    void findByLoanApplicationId_shouldReturnList() {
        given(repaymentScheduleRepository.findByLoanApplication_Id(loanId))
                .willReturn(List.of(schedule));

        List<RepaymentScheduleResponse> result = repaymentScheduleService.findByLoanApplicationId(loanId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(scheduleId);
        assertThat(result.get(0).getLoanApplicationId()).isEqualTo(loanId);
        assertThat(result.get(0).getInstallmentNumber()).isEqualTo(1);
        assertThat(result.get(0).getStatus()).isEqualTo("UNPAID");
    }

    @Test
    void findByLoanApplicationId_shouldReturnEmptyList_whenNoSchedules() {
        given(repaymentScheduleRepository.findByLoanApplication_Id(loanId))
                .willReturn(List.of());

        List<RepaymentScheduleResponse> result = repaymentScheduleService.findByLoanApplicationId(loanId);

        assertThat(result).isEmpty();
    }

    @Test
    void findById_shouldReturnResponse() {
        given(repaymentScheduleRepository.findByIdWithLoanApplication(scheduleId))
                .willReturn(Optional.of(schedule));

        RepaymentScheduleResponse result = repaymentScheduleService.findById(scheduleId);

        assertThat(result.getId()).isEqualTo(scheduleId);
        assertThat(result.getInstallmentNumber()).isEqualTo(1);
        assertThat(result.getTotalAmount()).isEqualByComparingTo("1100000");
    }

    @Test
    void findById_shouldThrowNotFound_whenScheduleDoesNotExist() {
        given(repaymentScheduleRepository.findByIdWithLoanApplication(scheduleId))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> repaymentScheduleService.findById(scheduleId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Repayment schedule not found");
    }

    // === generate schedule tests ===

    @Test
    void generateSchedule_shouldCreateExactlyTenorInstallments() {
        given(loanApplicationRepository.findById(loanId)).willReturn(Optional.of(loan));
        given(repaymentScheduleRepository.existsByLoanApplication_Id(loanId)).willReturn(false);
        given(repaymentScheduleRepository.saveAll(anyList()))
                .willAnswer(inv -> inv.getArgument(0));

        List<RepaymentScheduleResponse> result = repaymentScheduleService.generateSchedule(loanId);

        assertThat(result).hasSize(12);
        assertThat(result.get(0).getInstallmentNumber()).isEqualTo(1);
        assertThat(result.get(11).getInstallmentNumber()).isEqualTo(12);
        assertThat(result.get(0).getStatus()).isEqualTo("UNPAID");
    }

    @Test
    void generateSchedule_shouldCalculateCorrectAmounts() {
        given(loanApplicationRepository.findById(loanId)).willReturn(Optional.of(loan));
        given(repaymentScheduleRepository.existsByLoanApplication_Id(loanId)).willReturn(false);
        given(repaymentScheduleRepository.saveAll(anyList()))
                .willAnswer(inv -> inv.getArgument(0));

        List<RepaymentScheduleResponse> result = repaymentScheduleService.generateSchedule(loanId);

        RepaymentScheduleResponse first = result.get(0);
        assertThat(first.getInterestAmount()).isEqualByComparingTo("120000");
        assertThat(first.getPrincipalAmount()).isEqualByComparingTo("1000000");
        assertThat(first.getTotalAmount()).isEqualByComparingTo("1120000");
    }

    @Test
    void generateSchedule_shouldSetCorrectDueDates() {
        given(loanApplicationRepository.findById(loanId)).willReturn(Optional.of(loan));
        given(repaymentScheduleRepository.existsByLoanApplication_Id(loanId)).willReturn(false);
        given(repaymentScheduleRepository.saveAll(anyList()))
                .willAnswer(inv -> inv.getArgument(0));

        List<RepaymentScheduleResponse> result = repaymentScheduleService.generateSchedule(loanId);

        LocalDate expectedMonth1 = LocalDate.now().plusMonths(1);
        LocalDate expectedMonth12 = LocalDate.now().plusMonths(12);
        assertThat(result.get(0).getDueDate()).isEqualTo(expectedMonth1);
        assertThat(result.get(11).getDueDate()).isEqualTo(expectedMonth12);
    }

    @Test
    void generateSchedule_shouldThrowNotFound_whenLoanDoesNotExist() {
        given(loanApplicationRepository.findById(loanId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> repaymentScheduleService.generateSchedule(loanId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Loan application not found");

        verify(repaymentScheduleRepository, never()).saveAll(anyList());
    }

    @Test
    void generateSchedule_shouldThrow_whenLoanNotDisbursed() {
        loan.setStatus(LoanStatus.SUBMITTED);
        given(loanApplicationRepository.findById(loanId)).willReturn(Optional.of(loan));

        assertThatThrownBy(() -> repaymentScheduleService.generateSchedule(loanId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("must be DISBURSED");

        verify(repaymentScheduleRepository, never()).saveAll(anyList());
    }

    @Test
    void generateSchedule_shouldThrow_whenScheduleAlreadyExists() {
        given(loanApplicationRepository.findById(loanId)).willReturn(Optional.of(loan));
        given(repaymentScheduleRepository.existsByLoanApplication_Id(loanId)).willReturn(true);

        assertThatThrownBy(() -> repaymentScheduleService.generateSchedule(loanId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already exists");

        verify(repaymentScheduleRepository, never()).saveAll(anyList());
    }
}
