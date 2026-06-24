package com.fif.loanapplication.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fif.loanapplication.dto.payment.RepaymentScheduleResponse;
import com.fif.loanapplication.entity.LoanApplicationEntity;
import com.fif.loanapplication.entity.RepaymentScheduleEntity;
import com.fif.loanapplication.entity.enums.RepaymentStatus;
import com.fif.loanapplication.exception.RepaymentScheduleNotFoundException;
import com.fif.loanapplication.repository.RepaymentScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Value("${loan.interest.annual-rate}")
    private BigDecimal annualInteresRate;

    private RepaymentScheduleResponse toRepaymentResponse(RepaymentScheduleEntity repayment) {
        return RepaymentScheduleResponse.builder()
                .uid(repayment.getUid())
                .installmentNumber(repayment.getInstallmentNumber())
                .dueDate(repayment.getDueDate())
                .principalAmount(repayment.getPrincipalAmount())
                .interestAmount(repayment.getInterestAmount())
                .totalAmount(repayment.getTotalAmount())
                .status(repayment.getStatus())
                .build();
    }

    public List<RepaymentScheduleEntity> generateRepaymentSchedules(LoanApplicationEntity loanApplication) {
        List<RepaymentScheduleEntity> schedules = new ArrayList<>();

        BigDecimal loanAmount = loanApplication.getLoanAmount();
        Integer tenorMonth = loanApplication.getTenorMonth();

        BigDecimal monthlyInterestRate = annualInteresRate.divide(
                BigDecimal.valueOf(12),
                10,
                RoundingMode.HALF_UP);

        BigDecimal principalAmount = loanAmount.divide(
                BigDecimal.valueOf(tenorMonth),
                2,
                RoundingMode.HALF_UP);

        BigDecimal interestAmount = loanAmount.multiply(monthlyInterestRate)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalAmount = principalAmount.add(interestAmount);

        for (int i = 1; i <= tenorMonth; i++) {
            RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
                    .loanApplication(loanApplication)
                    .installmentNumber(i)
                    .dueDate(LocalDate.now().plusMonths(i))
                    .principalAmount(principalAmount)
                    .interestAmount(interestAmount)
                    .totalAmount(totalAmount)
                    .status(RepaymentStatus.UNPAID)
                    .build();

            schedules.add(schedule);
        }

        return schedules;
    }

    @Transactional
    public RepaymentScheduleResponse getRepaymentScheduleByUid(UUID uid) {
        RepaymentScheduleEntity repaymentSchedule = repaymentScheduleRepository.findById(uid)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(uid));
        return toRepaymentResponse(repaymentSchedule);
    }

    // Service get List of Repayments by Loan Application ID
    @Transactional
    public List<RepaymentScheduleResponse> getRepaymentScheduleByLoanApplicationUid(UUID loanApplicationUid) {
        List<RepaymentScheduleEntity> repaymentSchedules = repaymentScheduleRepository
                .findByLoanApplicationUid(loanApplicationUid);
        return repaymentSchedules.stream().map(this::toRepaymentResponse).toList();
    }

}
