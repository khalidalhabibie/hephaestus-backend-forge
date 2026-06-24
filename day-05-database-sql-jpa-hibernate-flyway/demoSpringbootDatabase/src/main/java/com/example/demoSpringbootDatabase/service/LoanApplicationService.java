package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.*;
import com.example.demoSpringbootDatabase.entity.CustomerEntity;
import com.example.demoSpringbootDatabase.entity.LoanApplicationEntity;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.CustomerNotFoundException;
import com.example.demoSpringbootDatabase.exception.LoanApplicationNotFoundException;
import com.example.demoSpringbootDatabase.repository.CustomerRepository;
import com.example.demoSpringbootDatabase.repository.LoanApplicationRepository;
import com.example.demoSpringbootDatabase.repository.RepaymentScheduleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class LoanApplicationService {
    private final LoanApplicationRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository scheduleRepository;

    @Value("${loan.interest.annual-rate:0.12}")
    private double annualInterestRate;

    public LoanApplicationService(LoanApplicationRepository loanRepository, 
                                CustomerRepository customerRepository, 
                                RepaymentScheduleRepository scheduleRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public LoanApplicationResponse createLoan(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> {
                    log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"Customer with ID {} not found during loan creation\"}", request.getCustomerId());
                    return new CustomerNotFoundException(request.getCustomerId());
                });

        LoanApplicationEntity loan = LoanApplicationEntity.builder()
                .customer(customer)
                .loanAmount(request.getLoanAmount())
                .tenorMonth(request.getTenorMonth())
                .purpose(request.getPurpose())
                .status("SUBMITTED")
                .build();
        
        loanRepository.save(loan);

        // ✅ STRUCTURED LOG: LOAN_SUBMITTED (Aman tanpa membawa data mentah PII)
        log.info("{\"event\":\"LOAN_SUBMITTED\", \"loan_id\":{}, \"customer_id\":{}, \"amount\":{}}", 
                loan.getId(), request.getCustomerId(), loan.getLoanAmount());

        return mapToResponse(loan);
    }

    @Transactional
    public LoanApplicationResponse updateStatus(Long id, String targetStatus) {
        LoanApplicationEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"Loan application with ID {} not found\"}", id);
                    return new LoanApplicationNotFoundException(id);
                });
        
        String currentStatus = loan.getStatus().toUpperCase();
        String nextStatus = targetStatus.toUpperCase();

        if (currentStatus.equals(nextStatus)) {
            return mapToResponse(loan);
        }

        // Validasi aturan transisi alur status akhir
        if ("REJECTED".equals(currentStatus) || "CLOSED".equals(currentStatus)) {
            log.error("{\"event\":\"VALIDATION_ERROR\", \"message\":\"Cannot change status from final state: {}\"}", currentStatus);
            throw new IllegalArgumentException("Cannot change status from final state: " + currentStatus);
        }

        switch (currentStatus) {
            case "SUBMITTED":
                if (!"APPROVED".equals(nextStatus) && !"REJECTED".equals(nextStatus)) {
                    log.error("{\"event\":\"VALIDATION_ERROR\", \"message\":\"SUBMITTED loan can only transition to APPROVED or REJECTED\"}");
                    throw new IllegalArgumentException("SUBMITTED loan can only transition to APPROVED or REJECTED");
                }
                break;
                
            case "APPROVED":
                if (!"DISBURSED".equals(nextStatus)) {
                    log.error("{\"event\":\"VALIDATION_ERROR\", \"message\":\"APPROVED loan can only transition to DISBURSED\"}");
                    throw new IllegalArgumentException("APPROVED loan can only transition to DISBURSED");
                }
                break;
                
            case "DISBURSED":
                if (!"CLOSED".equals(nextStatus)) {
                    log.error("{\"event\":\"VALIDATION_ERROR\", \"message\":\"DISBURSED loan can only transition to CLOSED\"}");
                    throw new IllegalArgumentException("DISBURSED loan can only transition to CLOSED");
                }
                
                List<RepaymentScheduleEntity> schedules = scheduleRepository.findByLoanApplicationId(id);
                boolean allPaid = !schedules.isEmpty() && schedules.stream()
                        .allMatch(schedule -> "PAID".equalsIgnoreCase(schedule.getStatus()));
                
                if (!allPaid) {
                    log.error("{\"event\":\"VALIDATION_ERROR\", \"message\":\"Cannot CLOSE loan. Repayment schedules not fully paid.\"}");
                    throw new IllegalArgumentException("Cannot CLOSE loan. Not all repayment schedules are PAID.");
                }
                break;
                
            default:
                log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"Unknown current loan status: {}\"}", currentStatus);
                throw new IllegalArgumentException("Unknown current loan status: " + currentStatus);
        }

        loan.setStatus(nextStatus);

        // TRIGGER UTAMA: Pembuatan jadwal cicilan
        if ("DISBURSED".equals(nextStatus)) {
            generateRepaymentSchedules(loan);
        }

        loanRepository.save(loan);

        // ✅ STRUCTURED LOG: LOAN_STATUS_CHANGED (Mencakup event approve/reject/disburse/closed)
        log.info("{\"event\":\"LOAN_STATUS_CHANGED\", \"loan_id\":{}, \"from\":\"{}\", \"to\":\"{}\"}", 
                id, currentStatus, nextStatus);

        return mapToResponse(loan);
    }

    private void generateRepaymentSchedules(LoanApplicationEntity loan) {
        BigDecimal loanAmount = BigDecimal.valueOf(loan.getLoanAmount());
        BigDecimal tenor = BigDecimal.valueOf(loan.getTenorMonth());
        
        BigDecimal principalPerMonth = loanAmount.divide(tenor, 0, RoundingMode.HALF_UP);
        
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(annualInterestRate)
                .divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);
        BigDecimal interestPerMonth = loanAmount.multiply(monthlyInterestRate).setScale(0, RoundingMode.HALF_UP);
        
        BigDecimal totalPerMonth = principalPerMonth.add(interestPerMonth);

        log.debug("Generating {} months of repayment schedules for loan ID: {}", loan.getTenorMonth(), loan.getId());

        for (int i = 1; i <= loan.getTenorMonth(); i++) {
            RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
                    .loanApplication(loan)
                    .installmentNumber(i)
                    .dueDate(LocalDate.now().plusMonths(i))
                    .principalAmount(principalPerMonth.longValue())
                    .interestAmount(interestPerMonth.longValue())
                    .totalAmount(totalPerMonth.longValue())
                    .status("UNPAID")
                    .build();
            
            scheduleRepository.save(schedule);
        }
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getById(Long id) {
        return loanRepository.findByIdWithCustomer(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> {
                    log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"Loan ID {} not found\"}", id);
                    return new LoanApplicationNotFoundException(id);
                });
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAll(String status) {
        log.debug("Fetching loan applications with status filter: {}", status);
        List<LoanApplicationEntity> loans = (status != null) ? 
                loanRepository.findByStatus(status.toUpperCase()) : loanRepository.findAll();
        return loans.stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByCustomerId(Long customerId) {
        log.debug("Fetching loans for customer ID: {}", customerId);
        return loanRepository.findLoansByCustomerId(customerId).stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public Page<LoanApplicationResponse> getLoansWithPagination(String status, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        log.debug("Executing paginated loan query between {} and {}", start, end);
        Page<LoanApplicationEntity> entities = (status != null && !status.isBlank()) ? 
                loanRepository.findByStatusAndCreatedAtBetween(status.toUpperCase(), start, end, pageable) :
                loanRepository.findByCreatedAtBetween(start, end, pageable);
        
        return entities.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public List<LoanStatusSummaryDto> getStatusSummary() {
        log.debug("Requesting loan status reporting summary matrix");
        return loanRepository.getSummaryByStatus();
    }

    private LoanApplicationResponse mapToResponse(LoanApplicationEntity loan) {
        CustomerSummaryResponse customerSummary = CustomerSummaryResponse.builder()
                .id(loan.getCustomer().getId())
                .fullName(loan.getCustomer().getFullName())
                .nik(loan.getCustomer().getNik())
                .email(loan.getCustomer().getEmail())
                .build();

        return LoanApplicationResponse.builder()
                .id(loan.getId())
                .loanAmount(loan.getLoanAmount())
                .tenorMonth(loan.getTenorMonth())
                .purpose(loan.getPurpose())
                .status(loan.getStatus())
                .customer(customerSummary)
                .build();
    }
}