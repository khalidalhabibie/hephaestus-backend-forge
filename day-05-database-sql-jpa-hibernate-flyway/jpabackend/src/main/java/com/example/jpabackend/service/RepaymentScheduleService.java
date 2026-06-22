package com.example.jpabackend.service;

import com.example.jpabackend.entity.*;
import com.example.jpabackend.exception.LoanApplicationNotFoundException;
import com.example.jpabackend.exception.RepaymentScheduleNotFoundException;
import com.example.jpabackend.repository.*;
import com.example.jpabackend.dto.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentRepo;
    private final LoanApplicationRepository loanRepo;

    public RepaymentScheduleService(
            RepaymentScheduleRepository repaymentRepo,
            LoanApplicationRepository loanRepo) {
        this.repaymentRepo = repaymentRepo;
        this.loanRepo = loanRepo;
    }

    // GET BY LOAN ID

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByLoanId(Long loanId) {

        LoanApplicationEntity loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new LoanApplicationNotFoundException(loanId));

        return repaymentRepo.findByLoanApplicationId(loan.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // GET BY ID

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getById(Long id) {

        RepaymentScheduleEntity r = repaymentRepo.findByIdWithLoan(id)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(id));

        return toResponse(r);
    }

    @Value("${loan.interest.annual-rate}")
    private BigDecimal annualInterestRate;

    @Transactional(readOnly = true)
    public boolean existsByLoanId(Long loanId) {
        return repaymentRepo.existsByLoanApplication_Id(loanId);
    }

    //GENERATE SCHEDULE
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateSchedule(Long loanId) {

        LoanApplicationEntity loan = loanRepo.findById(loanId)
                .orElseThrow();

        if (repaymentRepo.existsByLoanApplication_Id(loanId)) {
            return;
        }

        int tenor = loan.getTenorMonth();
        BigDecimal loanAmount = loan.getLoanAmount();

        BigDecimal monthlyRate = annualInterestRate
                .divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);

        BigDecimal principal = loanAmount
                .divide(BigDecimal.valueOf(tenor), 0, RoundingMode.HALF_UP);

        BigDecimal interest = loanAmount.multiply(monthlyRate);
        BigDecimal total = principal.add(interest);

        for (int i = 1; i <= tenor; i++) {

            RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();

            schedule.setLoanApplication(loan);
            schedule.setInstallmentNumber(i);
            schedule.setDueDate(LocalDate.now().plusMonths(i));

            schedule.setPrincipalAmount(principal);
            schedule.setInterestAmount(interest);
            schedule.setTotalAmount(total);

            schedule.setStatus("UNPAID");

            schedule.setCreatedAt(ZonedDateTime.now());
            schedule.setUpdatedAt(ZonedDateTime.now());

            repaymentRepo.save(schedule);
        }
    }

    // FILTER BY STATUS
    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByStatus(String status) {

        return repaymentRepo.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // GET ALL
    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getAll() {

        return repaymentRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // SUMMARY
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getOutstandingPerCustomer() {

        return repaymentRepo.getOutstandingByCustomer()
                .stream()
                .map(r -> Map.of(
                        "customer_id", r[0],
                        "outstanding_amount", r[1] != null ? r[1] : BigDecimal.ZERO))
                .toList();
    }

    // MAPPING
    private RepaymentScheduleResponse toResponse(RepaymentScheduleEntity r) {

        return new RepaymentScheduleResponse(
                r.getId(),
                r.getLoanApplication().getId(),
                r.getInstallmentNumber(),
                r.getDueDate(),
                r.getPrincipalAmount(),
                r.getInterestAmount(),
                r.getTotalAmount(),
                r.getStatus(),
                r.getCreatedAt(),
                r.getUpdatedAt());
    }

}