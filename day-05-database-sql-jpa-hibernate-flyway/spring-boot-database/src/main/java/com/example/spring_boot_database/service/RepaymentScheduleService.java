package com.example.spring_boot_database.service;

import com.example.spring_boot_database.repository.PaymentTransactionRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.entity.LoanApplicationEntity;
import com.example.spring_boot_database.entity.PaymentTransactionEntity;
import com.example.spring_boot_database.entity.RepaymentScheduleEntity;
import com.example.spring_boot_database.entity.StatusRepayment;
import com.example.spring_boot_database.exception.RepaymentScheduleNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final PaymentTransactionRepository paymentRepo;
    private final RepaymentScheduleRepository scheduleRepo;

    @Value("${loan.interest.annual-rate}")
    private BigDecimal annualRate;


    public List<RepaymentScheduleEntity> generateRepaymentSchedule(LoanApplicationEntity loan) {

        int tenor = loan.getTenorMonth();
        BigDecimal loanAmount = loan.getLoanAmount();

        BigDecimal monthlyRate = annualRate
                .divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);

        BigDecimal principal = loanAmount
                .divide(BigDecimal.valueOf(tenor), 2, RoundingMode.HALF_UP);

        BigDecimal interest = loanAmount
                .multiply(monthlyRate)
                .setScale(2, RoundingMode.HALF_UP);

        List<RepaymentScheduleEntity> result = new ArrayList<>();

        LocalDate startDate = LocalDate.now();

        for (int i = 1; i <= tenor; i++) {

            RepaymentScheduleEntity r = new RepaymentScheduleEntity();

            r.setLoanApplication(loan);
            r.setInstallmentNumber(i);
            r.setDueDate(startDate.plusMonths(i));

            r.setPrincipalAmount(principal);
            r.setInterestAmount(interest);
            r.setTotalAmount(principal.add(interest));

            r.setStatus(StatusRepayment.UNPAID.name());

            result.add(r);
        }

        return result;
    }

  
    @Transactional(readOnly = true)
    public RepaymentScheduleResponse findById(Long id) {
        return scheduleRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(id));
    }


    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> findAll(String status) {

        List<RepaymentScheduleEntity> data;

        if (status != null) {
            data = scheduleRepo.findAll()
                    .stream()
                    .filter(s -> s.getStatus().equalsIgnoreCase(status))
                    .toList();
        } else {
            data = scheduleRepo.findAll();
        }

        return data.stream()
                .map(this::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> findPaymentTransactionByRepaymentId(Long repaymentId) {

        List<PaymentTransactionEntity> data =
                paymentRepo.findByRepaymentScheduleId(repaymentId);

        return data.stream()
                .map(this::toPaymentResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> findByLoanId(Long loanId) {

        return scheduleRepo.findByLoanApplicationId(loanId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ============================
    // MAPPING
    // ============================
    public RepaymentScheduleResponse toResponse(RepaymentScheduleEntity e) {
        return RepaymentScheduleResponse.builder()
                .id(e.getId())
                .installmentNumber(e.getInstallmentNumber())
                .dueDate(e.getDueDate())
                .principalAmount(e.getPrincipalAmount())
                .interestAmount(e.getInterestAmount())
                .totalAmount(e.getTotalAmount())
                .status(StatusRepayment.valueOf(e.getStatus()))
                .build();
    }

    public PaymentTransactionResponse toPaymentResponse(PaymentTransactionEntity e) {
        return PaymentTransactionResponse.builder()
                .repaymentScheduleId(e.getRepaymentSchedule().getId())
                .paidAmount(e.getPaidAmount())
                .paymentReference(e.getPaymentReference())
                .paidAt(e.getPaidAt())
                .build();
    }
}
