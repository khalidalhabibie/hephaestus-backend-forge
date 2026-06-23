package com.example.training.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.training.dto.RepaymentScheduleResponse;
import com.example.training.entity.LoanApplicationEntity;
import com.example.training.enums.LoanStatus;
import com.example.training.entity.RepaymentScheduleEntity;
import com.example.training.enums.RepaymentStatus;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.LoanApplicationRepository;
import com.example.training.repository.RepaymentScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    @Value("${loan.interest.annual-rate:0.12}")
    private BigDecimal annualInterestRate;

    @Transactional
    public List<RepaymentScheduleResponse> generateSchedule(UUID loanApplicationId) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=schedule_generate_request loan_id={} correlation_id={}", loanApplicationId, correlationId);

        LoanApplicationEntity loan = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new NotFoundException("LOAN_APPLICATION_NOT_FOUND",
                        "Loan application not found with id: " + loanApplicationId));

        if (loan.getStatus() != LoanStatus.DISBURSED) {
            log.debug("event=schedule_generate_invalid_status loan_id={} status={} correlation_id={}",
                    loanApplicationId, loan.getStatus(), correlationId);
            throw new IllegalStateException(
                    "Loan must be DISBURSED before generating schedule. Current status: " + loan.getStatus());
        }

        if (repaymentScheduleRepository.existsByLoanApplication_Id(loanApplicationId)) {
            log.debug("event=schedule_generate_already_exists loan_id={} correlation_id={}", loanApplicationId, correlationId);
            throw new IllegalStateException("Repayment schedule already exists for this loan");
        }

        int tenor = loan.getTenorMonth();
        BigDecimal loanAmount = loan.getLoanAmount();

        BigDecimal monthlyInterest = loanAmount.multiply(annualInterestRate)
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

        BigDecimal monthlyPrincipal = loanAmount.divide(BigDecimal.valueOf(tenor), 2, RoundingMode.FLOOR);
        BigDecimal totalDistributed = monthlyPrincipal.multiply(BigDecimal.valueOf(tenor - 1));
        BigDecimal lastPrincipal = loanAmount.subtract(totalDistributed);

        log.debug("event=schedule_calculation loan_amount={} tenor={} monthly_interest={} monthly_principal={} last_principal={} correlation_id={}",
                loanAmount, tenor, monthlyInterest, monthlyPrincipal, lastPrincipal, correlationId);

        List<RepaymentScheduleEntity> schedules = new ArrayList<>();
        for (int i = 0; i < tenor; i++) {
            RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
            schedule.setLoanApplication(loan);
            schedule.setInstallmentNumber(i + 1);
            schedule.setDueDate(LocalDate.now().plusMonths(i + 1));
            schedule.setInterestAmount(monthlyInterest);

            boolean isLast = (i == tenor - 1);
            BigDecimal principal = isLast ? lastPrincipal : monthlyPrincipal;
            schedule.setPrincipalAmount(principal);
            schedule.setTotalAmount(principal.add(monthlyInterest));
            schedule.setStatus(RepaymentStatus.UNPAID);

            schedules.add(schedule);

            log.debug("event=schedule_installment_calculated installment={} principal={} total={} due_date={} correlation_id={}",
                    i + 1, principal, schedule.getTotalAmount(), schedule.getDueDate(), correlationId);
        }

        log.debug("event=schedule_save_all installments={} correlation_id={}", schedules.size(), correlationId);
        List<RepaymentScheduleEntity> saved = repaymentScheduleRepository.saveAll(schedules);

        log.info("event=repayment_schedule_generated loan_id={} installments={} correlation_id={}",
                loanApplicationId, saved.size(), correlationId);

        return saved.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> findByLoanApplicationId(UUID loanApplicationId) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=schedule_find_by_loan loan_id={} correlation_id={}", loanApplicationId, correlationId);
        List<RepaymentScheduleEntity> schedules = repaymentScheduleRepository.findByLoanApplication_Id(loanApplicationId);
        log.debug("event=schedule_find_by_loan_result count={} correlation_id={}", schedules.size(), correlationId);
        return schedules.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse findById(UUID id) {
        String correlationId = MDC.get("correlation_id");

        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findByIdWithLoanApplication(id)
                .orElseThrow(() -> {
                    log.warn("event=repayment_schedule_not_found schedule_id={} correlation_id={}", id, correlationId);
                    return new NotFoundException("REPAYMENT_SCHEDULE_NOT_FOUND",
                            "Repayment schedule not found with id: " + id);
                });
        return toResponse(schedule);
    }

    private RepaymentScheduleResponse toResponse(RepaymentScheduleEntity schedule) {
        return RepaymentScheduleResponse.builder()
                .id(schedule.getId())
                .loanApplicationId(schedule.getLoanApplication().getId())
                .installmentNumber(schedule.getInstallmentNumber())
                .dueDate(schedule.getDueDate())
                .principalAmount(schedule.getPrincipalAmount())
                .interestAmount(schedule.getInterestAmount())
                .totalAmount(schedule.getTotalAmount())
                .status(schedule.getStatus().name())
                .build();
    }
}
