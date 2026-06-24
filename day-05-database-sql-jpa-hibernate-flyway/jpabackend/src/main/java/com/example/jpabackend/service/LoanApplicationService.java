package com.example.jpabackend.service;

import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.jpabackend.config.LoanInterestConfig;
import com.example.jpabackend.dto.request.CreateLoanApplicationRequest;
import com.example.jpabackend.dto.request.UpdateLoanStatusRequest;
import com.example.jpabackend.dto.response.CustomerSummaryResponse;
import com.example.jpabackend.dto.response.LoanApplicationResponse;
import com.example.jpabackend.dto.response.LoanSummaryResponse;
import com.example.jpabackend.dto.response.OutstandingCustomerResponse;
import com.example.jpabackend.entity.CustomerEntity;
import com.example.jpabackend.entity.LoanApplicationEntity;
import com.example.jpabackend.enums.LoanStatus;
import com.example.jpabackend.enums.RepaymentStatus;
import com.example.jpabackend.exception.CustomerNotFoundException;
import com.example.jpabackend.exception.LoanApplicationNotFoundException;
import com.example.jpabackend.repository.CustomerRepository;
import com.example.jpabackend.repository.LoanApplicationRepository;
import com.example.jpabackend.repository.RepaymentScheduleRepository;
import lombok.*;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanInterestConfig loanInterestConfig;
    private static final Logger log = LoggerFactory.getLogger(LoanApplicationService.class);
    private LoanApplicationResponse mapToResponse(LoanApplicationEntity loan) {
        return LoanApplicationResponse.builder()
            .id(loan.getId())
            .loanAmount(loan.getLoanAmount())
            .tenorMonth(loan.getTenorMonth())
            .purpose(loan.getPurpose())
            .status(loan.getStatus())
            .customer(
                CustomerSummaryResponse.builder()
                    .id(loan.getCustomer().getId())
                    .fullName(loan.getCustomer().getFullName())
                    .nik(loan.getCustomer().getNik())
                    .email(loan.getCustomer().getEmail())
                    .build())
                .build();
    }
    
    private void generateRepaymentSchedule(LoanApplicationEntity loan) {
        double annualRate = loanInterestConfig.getAnnualRate();
        double monthlyRate = annualRate / 12;
        int tenor = loan.getTenorMonth();
        var loanAmount = loan.getLoanAmount();
        var principalPerMonth = loanAmount.divide(
            java.math.BigDecimal.valueOf(tenor),
            2,
            java.math.RoundingMode.HALF_UP
        );
        var interestPerMonth = loanAmount.multiply(
            java.math.BigDecimal.valueOf(monthlyRate))
                .setScale(2, java.math.RoundingMode.HALF_UP);
        var totalPerMonth = principalPerMonth.add(interestPerMonth);
            java.time.LocalDate startDate = java.time.LocalDate.now();
            if (!repaymentScheduleRepository
                .findByLoanApplicationId(loan.getId())
                .isEmpty()) {
        return;
        }
        for (int i = 1; i <= tenor; i++) {
        var dueDate = startDate.plusMonths(i);
        var schedule = com.example.jpabackend.entity.RepaymentScheduleEntity.builder()
            .loanApplication(loan)
            .installmentNumber(i)
            .dueDate(dueDate)
            .principalAmount(principalPerMonth)
            .interestAmount(interestPerMonth)
            .totalAmount(totalPerMonth)
            .status(com.example.jpabackend.enums.RepaymentStatus.UNPAID)
            .build();
        repaymentScheduleRepository.save(schedule);
        }
        System.out.println("Generated " + tenor + " repayment schedules");
    }

    public List<OutstandingCustomerResponse> getOutstandingPerCustomer() {
        return loanApplicationRepository.getOutstandingPerCustomer()
            .stream()
            .map(obj -> OutstandingCustomerResponse.builder()
            .customerId((Long) obj[0])
            .fullName((String) obj[1])
            .outstandingAmount((BigDecimal) obj[2])
            .build())
            .toList();
    }

    @Transactional
    public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new CustomerNotFoundException(
                "Customer " + request.getCustomerId() + " not found"));
        LoanApplicationEntity loan = LoanApplicationEntity.builder()
            .customer(customer)
            .loanAmount(request.getLoanAmount())
            .tenorMonth(request.getTenorMonth())
            .purpose(request.getPurpose())
            .status(LoanStatus.SUBMITTED)
            .build();
        LoanApplicationEntity savedLoan = loanApplicationRepository.save(loan);
        log.info("event=loan_application_submitted application_id={} customer_id={}", savedLoan.getId(), customer.getId());
        return mapToResponse(savedLoan);
    }

    @Transactional(readOnly = true)
    public Page<LoanApplicationResponse> getLoans(Pageable pageable) {
        return loanApplicationRepository
            .findAll(pageable)
            .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAllLoans() {
        return loanApplicationRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getLoanById(Long id) {
        LoanApplicationEntity loan = loanApplicationRepository
            .findByIdWithCustomer(id)
            .orElseThrow(() -> new LoanApplicationNotFoundException(
                "Loan application " + id + " not found"));
        return mapToResponse(loan);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getLoansByCustomer(Long customerId) {
        return loanApplicationRepository
            .findLoansByCustomerId(customerId)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getLoansByStatus(LoanStatus status) {
        return loanApplicationRepository
            .findByStatus(status)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanSummaryResponse> getLoanSummary() {
        return loanApplicationRepository
            .countLoanByStatus()
            .stream()
            .map(row -> LoanSummaryResponse.builder()
            .status((LoanStatus) row[0])
            .totalLoan((Long) row[1])
            .totalAmount((BigDecimal) row[2])
            .build())
            .toList();
    }

    // @Transactional
    // public LoanApplicationResponse updateLoanStatus(Long id, UpdateLoanStatusRequest request) {
    //     LoanApplicationEntity loan = loanApplicationRepository.findById(id)
    //         .orElseThrow(() -> new LoanApplicationNotFoundException(
    //             "Loan application " + id + " not found"));
    //     LoanStatus current = loan.getStatus();
    //     LoanStatus next = request.getStatus();
    //         if (!isValidTransition(current, next)) {
    //             throw new IllegalStateException("Invalid status transition");
    //         }
    //     loan.setStatus(next);
    //     LoanApplicationEntity updated = loanApplicationRepository.save(loan);
    //         if (next == LoanStatus.DISBURSED) {
    //             generateRepaymentSchedule(updated);
    //         }
    //     return mapToResponse(updatedLoan);
    // }
    
    @Transactional
    public LoanApplicationResponse updateLoanStatus(Long id, UpdateLoanStatusRequest request) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
            .orElseThrow(() -> new LoanApplicationNotFoundException(
            "Loan application " + id + " not found"));
        LoanStatus current = loan.getStatus();
        LoanStatus next = request.getStatus();
        switch (current) {
            case SUBMITTED -> {
                if (next != LoanStatus.APPROVED && next != LoanStatus.REJECTED) {
                    throw new IllegalStateException("SUBMITTED can only go to APPROVED or REJECTED");
                }
            }
            case APPROVED -> {
                if (next != LoanStatus.DISBURSED) {
                    throw new IllegalStateException("APPROVED can only go to DISBURSED");
                }
            }
            case REJECTED -> {
                throw new IllegalStateException("REJECTED is a final state");
            }
            case DISBURSED -> {
                if (next != LoanStatus.CLOSED) {
                    throw new IllegalStateException("DISBURSED can only go to CLOSED");
                }
                boolean allPaid = repaymentScheduleRepository
                .findByLoanApplicationId(loan.getId())
                .stream().allMatch(schedule -> schedule.getStatus() == RepaymentStatus.PAID);
                if (!allPaid) {
                    throw new IllegalStateException(
                        "Loan cannot be CLOSED because there are unpaid schedules");
                }
            }
            case CLOSED -> {
                throw new IllegalStateException("CLOSED is a final state");
            }
        }
        loan.setStatus(next);
    LoanApplicationEntity updated = loanApplicationRepository.save(loan);
        if (next == LoanStatus.APPROVED) {
            log.info("event=loan_application_approved application_id={}", updated.getId());
        }
        if (next == LoanStatus.REJECTED) {
            log.info("event=loan_application_rejected application_id={}", updated.getId());
        }
        if (next == LoanStatus.DISBURSED) {
            generateRepaymentSchedule(updated);
        }
    return mapToResponse(updated);
    }

    @Transactional
    public LoanApplicationResponse updateLoan(Long id, CreateLoanApplicationRequest request) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
            .orElseThrow(() -> new LoanApplicationNotFoundException(
                "Loan application " + id + " not found"));
            loan.setLoanAmount(request.getLoanAmount());
            loan.setTenorMonth(request.getTenorMonth());
            loan.setPurpose(request.getPurpose());
        return mapToResponse(loanApplicationRepository.save(loan));
    }

    @Transactional
    public void deleteLoan(Long id) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
            .orElseThrow(() -> new LoanApplicationNotFoundException(
                "Loan application " + id + " not found"));
            loan.setDeleted(true);
        loanApplicationRepository.save(loan);
    }
    
}