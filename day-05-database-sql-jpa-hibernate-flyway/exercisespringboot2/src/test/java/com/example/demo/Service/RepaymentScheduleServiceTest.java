package com.example.demo.Service;

import com.example.training.DTO.RepaymentScheduleResponse;
import com.example.training.Entity.LoanApplicationEntity;
import com.example.training.Entity.RepaymentScheduleEntity;
import com.example.training.Exception.LoanApplicationNotFoundException;
import com.example.training.Exception.RepaymentScheduleNotFoundException;
import com.example.training.Repository.LoanApplicationRepository;
import com.example.training.Repository.RepaymentScheduleRepository;
import com.example.training.Service.RepaymentScheduleService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
@DisplayName("Repayment Schedule Service Tests")
class RepaymentScheduleServiceTest {

    @InjectMocks
    private RepaymentScheduleService repaymentScheduleService;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    // ========== Constants ==========
    private static final Long LOAN_ID = 1L;
    private static final Long SCHEDULE_ID = 1L;
    private static final Long NON_EXISTING_ID = 999L;
    private static final BigDecimal TOTAL_AMOUNT = new BigDecimal("1000000");

    // ========== Test Data Factory ==========
    private LoanApplicationEntity buildLoan() {
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(LOAN_ID);
        loan.setLoanAmount(new BigDecimal("10000000"));
        loan.setTenorMonth(12);
        return loan;
    }

    private RepaymentScheduleEntity buildSchedule(int installmentNumber, RepaymentScheduleEntity.ScheduleStatus status) {
        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setId(SCHEDULE_ID + installmentNumber - 1);
        schedule.setLoanApplication(buildLoan());
        schedule.setInstallmentNumber(installmentNumber);
        schedule.setPrincipalAmount(new BigDecimal("800000"));
        schedule.setInterestAmount(new BigDecimal("200000"));
        schedule.setTotalAmount(TOTAL_AMOUNT);
        schedule.setStatus(status);
        schedule.setDueDate(ZonedDateTime.now().plusMonths(installmentNumber));
        schedule.setCreatedAt(ZonedDateTime.now());
        schedule.setUpdatedAt(ZonedDateTime.now());
        return schedule;
    }

    // ========== Nested: Get By Loan ==========
    @Nested
    @DisplayName("Get Repayment Schedules By Loan")
    class GetByLoanTests {

        @Test
        @DisplayName("Positive: Existing loan with schedules → should return all schedules")
        void existingLoanWithSchedules_shouldReturnAllSchedules() {
            // Given
            RepaymentScheduleEntity schedule1 = buildSchedule(1, RepaymentScheduleEntity.ScheduleStatus.UNPAID);
            RepaymentScheduleEntity schedule2 = buildSchedule(2, RepaymentScheduleEntity.ScheduleStatus.UNPAID);

            given(loanApplicationRepository.existsById(LOAN_ID)).willReturn(true);
            given(repaymentScheduleRepository.findByLoanApplicationId(LOAN_ID))
                    .willReturn(List.of(schedule1, schedule2));

            // When
            List<RepaymentScheduleResponse> result = repaymentScheduleService.getByLoanApplicationId(LOAN_ID);

            // Then
            assertThat(result)
                    .hasSize(2)
                    .extracting(RepaymentScheduleResponse::getInstallmentNumber)
                    .containsExactly(1, 2);
        }

        @Test
        @DisplayName("Positive: Existing loan without schedules → should return empty list")
        void existingLoanWithoutSchedules_shouldReturnEmptyList() {
            // Given
            given(loanApplicationRepository.existsById(LOAN_ID)).willReturn(true);
            given(repaymentScheduleRepository.findByLoanApplicationId(LOAN_ID)).willReturn(List.of());

            // When
            List<RepaymentScheduleResponse> result = repaymentScheduleService.getByLoanApplicationId(LOAN_ID);

            // Then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Negative: Non-existing loan ID → should throw LoanApplicationNotFoundException")
        void nonExistingLoan_shouldThrowLoanApplicationNotFoundException() {
            // Given
            given(loanApplicationRepository.existsById(NON_EXISTING_ID)).willReturn(false);

            // When & Then
            assertThatThrownBy(() -> repaymentScheduleService.getByLoanApplicationId(NON_EXISTING_ID))
                    .isInstanceOf(LoanApplicationNotFoundException.class);
            then(repaymentScheduleRepository).should(never()).findByLoanApplicationId(any());
        }
    }

    // ========== Nested: Get By Loan & Status ==========
    @Nested
    @DisplayName("Get Repayment Schedules By Loan And Status")
    class GetByLoanAndStatusTests {

        @Test
        @DisplayName("Positive: Filter by PAID status → should return only paid schedules")
        void filterByPaidStatus_shouldReturnPaidOnly() {
            // Given
            String status = "PAID";
            RepaymentScheduleEntity schedule = buildSchedule(1, RepaymentScheduleEntity.ScheduleStatus.PAID);

            given(loanApplicationRepository.existsById(LOAN_ID)).willReturn(true);
            given(repaymentScheduleRepository.findByLoanIdAndStatus(LOAN_ID, RepaymentScheduleEntity.ScheduleStatus.PAID))
                    .willReturn(List.of(schedule));

            // When
            List<RepaymentScheduleResponse> result = repaymentScheduleService.getByLoanIdAndStatus(LOAN_ID, status);

            // Then
            assertThat(result)
                    .hasSize(1)
                    .first()
                    .satisfies(r -> assertThat(r.getStatus()).isEqualTo("PAID"));
        }

        @Test
        @DisplayName("Positive: Filter by UNPAID status → should return only unpaid schedules")
        void filterByUnpaidStatus_shouldReturnUnpaidOnly() {
            // Given
            String status = "UNPAID";
            RepaymentScheduleEntity schedule1 = buildSchedule(1, RepaymentScheduleEntity.ScheduleStatus.UNPAID);
            RepaymentScheduleEntity schedule2 = buildSchedule(2, RepaymentScheduleEntity.ScheduleStatus.UNPAID);

            given(loanApplicationRepository.existsById(LOAN_ID)).willReturn(true);
            given(repaymentScheduleRepository.findByLoanIdAndStatus(LOAN_ID, RepaymentScheduleEntity.ScheduleStatus.UNPAID))
                    .willReturn(List.of(schedule1, schedule2));

            // When
            List<RepaymentScheduleResponse> result = repaymentScheduleService.getByLoanIdAndStatus(LOAN_ID, status);

            // Then
            assertThat(result)
                    .hasSize(2)
                    .extracting(RepaymentScheduleResponse::getStatus)
                    .containsOnly("UNPAID");
        }

        @Test
        @DisplayName("Positive: Null/blank status → should return all schedules")
        void nullStatus_shouldReturnAllSchedules() {
            // Given
            RepaymentScheduleEntity schedule = buildSchedule(1, RepaymentScheduleEntity.ScheduleStatus.UNPAID);

            given(loanApplicationRepository.existsById(LOAN_ID)).willReturn(true);
            given(repaymentScheduleRepository.findByLoanIdAndStatus(LOAN_ID, null))
                    .willReturn(List.of(schedule));

            // When
            List<RepaymentScheduleResponse> result = repaymentScheduleService.getByLoanIdAndStatus(LOAN_ID, null);

            // Then
            assertThat(result).hasSize(1);
        }

        @Test
        @DisplayName("Negative: Invalid status string → should throw IllegalArgumentException")
        void invalidStatus_shouldThrowIllegalArgumentException() {
            // Given
            String status = "INVALID_STATUS";
            given(loanApplicationRepository.existsById(LOAN_ID)).willReturn(true);

            // When & Then
            assertThatThrownBy(() -> repaymentScheduleService.getByLoanIdAndStatus(LOAN_ID, status))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Negative: Non-existing loan ID → should throw LoanApplicationNotFoundException")
        void nonExistingLoan_shouldThrowLoanApplicationNotFoundException() {
            // Given
            given(loanApplicationRepository.existsById(NON_EXISTING_ID)).willReturn(false);

            // When & Then
            assertThatThrownBy(() -> repaymentScheduleService.getByLoanIdAndStatus(NON_EXISTING_ID, "PAID"))
                    .isInstanceOf(LoanApplicationNotFoundException.class);
        }
    }

    // ========== Nested: Get By ID ==========
    @Nested
    @DisplayName("Get Repayment Schedule By ID")
    class GetByIdTests {

        @Test
        @DisplayName("Positive: Existing schedule ID → should return schedule details")
        void existingId_shouldReturnSchedule() {
            // Given
            RepaymentScheduleEntity schedule = buildSchedule(1, RepaymentScheduleEntity.ScheduleStatus.UNPAID);
            given(repaymentScheduleRepository.findByIdWithLoanApplication(SCHEDULE_ID)).willReturn(Optional.of(schedule));

            // When
            RepaymentScheduleResponse result = repaymentScheduleService.getById(SCHEDULE_ID);

            // Then
            assertThat(result)
                    .isNotNull()
                    .satisfies(r -> {
                        assertThat(r.getId()).isEqualTo(SCHEDULE_ID);
                        assertThat(r.getInstallmentNumber()).isEqualTo(1);
                        assertThat(r.getTotalAmount()).isEqualTo(TOTAL_AMOUNT);
                        assertThat(r.getStatus()).isEqualTo("UNPAID");
                    });
        }

        @Test
        @DisplayName("Negative: Non-existing schedule ID → should throw RepaymentScheduleNotFoundException")
        void nonExistingId_shouldThrowRepaymentScheduleNotFoundException() {
            // Given
            given(repaymentScheduleRepository.findByIdWithLoanApplication(NON_EXISTING_ID)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> repaymentScheduleService.getById(NON_EXISTING_ID))
                    .isInstanceOf(RepaymentScheduleNotFoundException.class)
                    .hasMessageContaining(String.valueOf(NON_EXISTING_ID));
        }
    }
}