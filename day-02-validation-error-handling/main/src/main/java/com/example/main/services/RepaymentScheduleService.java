package com.example.main.services;

import com.example.main.dto.response.PaymentTransactionResponse;
import com.example.main.dto.response.RepaymentScheduleResponse;
import com.example.main.entity.LoanApplicationEntity;
import com.example.main.entity.PaymentTransactionEntity;
import com.example.main.entity.RepaymentScheduleEntity;
import com.example.main.enums.ScheduleStatus;
import com.example.main.exceptions.NotFoundException;
import com.example.main.repositories.PaymentTransactionRepository;
import com.example.main.repositories.RepaymentScheduleRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Value("${loan.interest.annual-rate:0.12}")
    private double annualInterestRate;

    public RepaymentScheduleService(RepaymentScheduleRepository repaymentScheduleRepository, PaymentTransactionRepository paymentTransactionRepository) {
        this.repaymentScheduleRepository = repaymentScheduleRepository;
        this.paymentTransactionRepository = paymentTransactionRepository;
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getRepaymentScheduleById(Long id) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findByIdWithLoanApplication(id)
                .orElseThrow(() -> new NotFoundException("Repayment schedule not found with ID: " + id));

        return mapToScheduleResponse(schedule);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getPaymentTransactionsByScheduleId(Long scheduleId) {
        PaymentTransactionService paymentTransactionService = new PaymentTransactionService(paymentTransactionRepository, repaymentScheduleRepository);

        return paymentTransactionService.getTransactionsByScheduleId(scheduleId);
    }

    @Transactional
    public void createRepaymentSchedules(LoanApplicationEntity loan) {
        int tenor = loan.getTenorMonth();
        BigDecimal loanAmount = loan.getLoanAmount();

        // principal_amount = loan_amount / tenor_month
        BigDecimal monthlyPrincipal = loanAmount.divide(BigDecimal.valueOf(tenor), 2, RoundingMode.HALF_UP);

        // monthly_interest_rate = annual_interest_rate / 12
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(annualInterestRate)
                .divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);

        // interest_amount = loan_amount x monthly_interest_rate
        BigDecimal monthlyInterest = loanAmount.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);

        // total_amount = principal_amount + interest_amount
        BigDecimal monthlyTotal = monthlyPrincipal.add(monthlyInterest);

        // Tanggal jatuh tempo pertama dihitung 1 bulan sejak trigger pencairan/approval
        LocalDate nextDueDate = LocalDate.now().plusMonths(1);

        // Loop pembuatan record sebanyak jumlah bulan tenor
        for (int i = 1; i <= tenor; i++) {
            RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
            schedule.setLoanApplication(loan);
            schedule.setInstallmentNumber(i);
            schedule.setDueDate(nextDueDate);
            schedule.setPrincipalAmount(monthlyPrincipal);
            schedule.setInterestAmount(monthlyInterest);
            schedule.setTotalAmount(monthlyTotal);
            
            // Set status awal menggunakan nama ENUM ("UNPAID") agar klop dengan tipe data String di DB
            schedule.setStatus(ScheduleStatus.UNPAID);

            repaymentScheduleRepository.save(schedule);

            // Majukan tanggal jatuh tempo 1 bulan ke depan untuk cicilan berikutnya
            nextDueDate = nextDueDate.plusMonths(1);
        }
    }

    private RepaymentScheduleResponse mapToScheduleResponse(RepaymentScheduleEntity schedule) {
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();
        
        response.setId(schedule.getId());
        response.setInstallmentNumber(schedule.getInstallmentNumber());
        response.setDueDate(schedule.getDueDate());
        response.setPrincipalAmount(schedule.getPrincipalAmount());
        response.setInterestAmount(schedule.getInterestAmount());
        response.setTotalAmount(schedule.getTotalAmount());
        response.setStatus(schedule.getStatus());
        
        return response;
    }
}