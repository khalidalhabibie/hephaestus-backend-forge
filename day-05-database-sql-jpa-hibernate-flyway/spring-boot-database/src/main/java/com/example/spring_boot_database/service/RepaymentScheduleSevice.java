package com.example.spring_boot_database.service;

import com.example.spring_boot_database.repository.PaymentTransactionRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.entity.LoanApplicationEntity;
import com.example.spring_boot_database.entity.PaymentTransactionEntity;
import com.example.spring_boot_database.entity.RepaymentScheduleEntity;
import com.example.spring_boot_database.entity.StatusRepayment;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleSevice {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Value("${loan.interest.annual-rate}")
    private BigDecimal annualInterestRate;

    public List<RepaymentScheduleEntity> generateRepaymentSchedule(LoanApplicationEntity loan) {

        int tenor = loan.getTenorMonth();
        BigDecimal loanAmount = loan.getLoanAmount();

        BigDecimal monthlyRate = annualInterestRate
                .divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);

        BigDecimal principalAmount = loanAmount
                .divide(BigDecimal.valueOf(tenor), 2, RoundingMode.HALF_UP);

        BigDecimal interestAmount = loanAmount
                .multiply(monthlyRate)
                .setScale(2, RoundingMode.HALF_UP);

        List<RepaymentScheduleEntity> schedules = new ArrayList<>();

        LocalDate startDate = LocalDate.now();

        for (int i = 1; i <= tenor; i++) {

            RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();

            schedule.setLoanApplication(loan);
            schedule.setInstallmentNumber(i);
            schedule.setDueDate(startDate.plusMonths(i));

            schedule.setPrincipalAmount(principalAmount);
            schedule.setInterestAmount(interestAmount);
            schedule.setTotalAmount(principalAmount.add(interestAmount));

            schedule.setStatus(StatusRepayment.UNPAID.name());

            schedules.add(schedule);
        }

        return schedules;
    }

    public RepaymentScheduleEntity getById(Long id) {
        return repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repayment not found"));
    }

    public RepaymentScheduleResponse findById(Long id) {
        return toResponse(getById(id));
    }

    public List<PaymentTransactionResponse> findPaymentTransactionByRepaymentId(Long repaymentId) {

        List<PaymentTransactionEntity> data;

        if (repaymentId != null) {
            data = paymentTransactionRepository.findByRepaymentScheduleId(repaymentId);
        } else {
            data = paymentTransactionRepository.findAll();
        }

        return data.stream()
                .map(this::toResponsePaymentTransaction)
                .collect(Collectors.toList());
    }

    public PaymentTransactionResponse toResponsePaymentTransaction(PaymentTransactionEntity entity) {
        return PaymentTransactionResponse.builder()
                .repaymentScheduleId(entity.getRepaymentSchedule().getId())
                .paidAmount(entity.getPaidAmount())
                .paymentReference(entity.getPaymentReference())
                .paidAt(entity.getPaidAt()) 
                .build();
    }

    public RepaymentScheduleResponse toResponse(RepaymentScheduleEntity entity) {
        return RepaymentScheduleResponse.builder()
                .id(entity.getId())
                .installmentNumber(entity.getInstallmentNumber())
                .dueDate(entity.getDueDate())
                .principalAmount(entity.getPrincipalAmount())
                .interestAmount(entity.getInterestAmount())
                .totalAmount(entity.getTotalAmount())
                .build();
    }
}

