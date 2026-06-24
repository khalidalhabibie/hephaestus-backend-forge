package com.example.training.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    // ponytail: 12% flat per tahun dari config — ganti rate di application.properties jika perlu
    @Value("${loan.interest.annual-rate:0.12}")
    private BigDecimal annualInterestRate;

    @Transactional
    public List<RepaymentScheduleResponse> generateSchedule(UUID loanApplicationId) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new NotFoundException("LOAN_APPLICATION_NOT_FOUND", "Loan application not found with id: " + loanApplicationId));

        if (loan.getStatus() != LoanStatus.DISBURSED) {
            throw new IllegalStateException("Loan must be DISBURSED before generating schedule. Current status: " + loan.getStatus());
        }

        if (repaymentScheduleRepository.existsByLoanApplication_Id(loanApplicationId)) {
            throw new IllegalStateException("Repayment schedule already exists for this loan");
        }

        int tenor = loan.getTenorMonth();
        BigDecimal loanAmount = loan.getLoanAmount();

        BigDecimal monthlyInterest = loanAmount.multiply(annualInterestRate)
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

        // equal principal, last installment gets remainder
        // ponytail: BigDecimal remainder logic — extract ke helper jika dipakai di tempat lain
        BigDecimal monthlyPrincipal = loanAmount.divide(BigDecimal.valueOf(tenor), 2, RoundingMode.FLOOR);
        BigDecimal totalDistributed = monthlyPrincipal.multiply(BigDecimal.valueOf(tenor - 1));
        BigDecimal lastPrincipal = loanAmount.subtract(totalDistributed);

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
        }

        List<RepaymentScheduleEntity> saved = repaymentScheduleRepository.saveAll(schedules);
        return saved.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> findByLoanApplicationId(UUID loanApplicationId) {
        return repaymentScheduleRepository.findByLoanApplication_Id(loanApplicationId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse findById(UUID id) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findByIdWithLoanApplication(id)
                .orElseThrow(() -> new NotFoundException("REPAYMENT_SCHEDULE_NOT_FOUND", "Repayment schedule not found with id: " + id));
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
