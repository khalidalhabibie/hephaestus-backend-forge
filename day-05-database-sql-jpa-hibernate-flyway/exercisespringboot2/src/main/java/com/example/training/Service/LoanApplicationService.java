// Business logic loan: create (auto generate repayment schedule), get, list, pagination, filter status & tanggal, update status, summary report, outstanding report.

package com.example.training.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.training.DTO.CreateLoanApplicationRequest;
import com.example.training.DTO.CustomerSummaryResponse;
import com.example.training.DTO.LoanApplicationResponse;
import com.example.training.DTO.UpdateLoanStatusRequest;
import com.example.training.Entity.CustomerEntity;
import com.example.training.Entity.LoanApplicationEntity;
import com.example.training.Entity.RepaymentScheduleEntity;
import com.example.training.Exception.CustomerNotFoundException;
import com.example.training.Exception.LoanApplicationNotFoundException;
import com.example.training.Repository.CustomerRepository;
import com.example.training.Repository.LoanApplicationRepository;
import com.example.training.Repository.RepaymentScheduleRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional
    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        log.info("event=loan_submitted, customer_id={}, amount={}, tenor={}, purpose={}", 
                request.getCustomerId(), 
                request.getLoanAmount(), 
                request.getTenorMonth(),
                request.getPurpose());

        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> {
                    log.warn("event=loan_submission_failed, reason=customer_not_found, customer_id={}", 
                            request.getCustomerId());  
                    return new CustomerNotFoundException(request.getCustomerId());
                });

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(customer);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanApplicationEntity.LoanStatus.SUBMITTED);

        LoanApplicationEntity savedLoan = loanApplicationRepository.save(loan);
        
        log.info("event=loan_created, loan_id={}, customer_id={}, status=SUBMITTED", 
                savedLoan.getId(), customer.getId());

        return mapToResponse(savedLoan);
    }

    private void generateRepaymentSchedules(LoanApplicationEntity loan) {
        BigDecimal monthlyPrincipal = loan.getLoanAmount()
                .divide(BigDecimal.valueOf(loan.getTenorMonth()), 2, RoundingMode.HALF_UP);
        BigDecimal interestRate = new BigDecimal("0.01");
        BigDecimal monthlyInterest = loan.getLoanAmount().multiply(interestRate)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalMonthly = monthlyPrincipal.add(monthlyInterest);

        ZonedDateTime dueDate = ZonedDateTime.now().plusMonths(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        for (int i = 1; i <= loan.getTenorMonth(); i++) {
            RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
            schedule.setLoanApplication(loan);
            schedule.setInstallmentNumber(i);
            schedule.setDueDate(dueDate);
            schedule.setPrincipalAmount(monthlyPrincipal);
            schedule.setInterestAmount(monthlyInterest);
            schedule.setTotalAmount(totalMonthly);
            schedule.setStatus(RepaymentScheduleEntity.ScheduleStatus.UNPAID);
            repaymentScheduleRepository.save(schedule);
            dueDate = dueDate.plusMonths(1);
        }
        
        log.info("event=repayment_schedule_generated, loan_id={}, total_schedules={}", 
                loan.getId(), loan.getTenorMonth());
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getById(Long id) {
        log.info("event=loan_fetch_requested, loan_id={}", id);  
        
        LoanApplicationEntity entity = loanApplicationRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> {
                    log.warn("event=loan_fetch_failed, reason=not_found, loan_id={}", id);  
                    return new LoanApplicationNotFoundException(id);
                });
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAll() {
        log.info("event=loan_list_requested");  
        return loanApplicationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<LoanApplicationResponse> getAllPaginated(
            String status,
            ZonedDateTime startDate,
            ZonedDateTime endDate,
            Pageable pageable) {
        
        log.info("event=loan_list_paginated_requested, status_filter={}, page={}", status, pageable.getPageNumber());  
        
        LoanApplicationEntity.LoanStatus loanStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                loanStatus = LoanApplicationEntity.LoanStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("event=loan_list_paginated_failed, reason=invalid_status, status={}", status);  
                throw new IllegalArgumentException("Invalid loan status: " + status);
            }
        }
        if (startDate == null) {
            startDate = Instant.EPOCH.atZone(ZoneId.of("Asia/Jakarta"));
        }
        if (endDate == null) {
            endDate = ZonedDateTime.now();
        }
        Page<LoanApplicationEntity> result = loanApplicationRepository.findAllWithFilters(
                loanStatus, startDate, endDate, pageable);
        return result.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByCustomerId(Long customerId) {
        log.info("event=loan_list_by_customer_requested, customer_id={}", customerId);  
        
        if (!customerRepository.existsById(customerId)) {
            log.warn("event=loan_list_by_customer_failed, reason=customer_not_found, customer_id={}", customerId); 
            throw new CustomerNotFoundException(customerId);
        }
        return loanApplicationRepository.findLoansByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByStatus(String status) {
        log.info("event=loan_list_by_status_requested, status={}", status); 
        
        LoanApplicationEntity.LoanStatus loanStatus;
        try {
            loanStatus = LoanApplicationEntity.LoanStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("event=loan_list_by_status_failed, reason=invalid_status, status={}", status);
            throw new IllegalArgumentException("Invalid loan status: " + status);
        }
        return loanApplicationRepository.findByStatus(loanStatus.name()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public LoanApplicationResponse updateStatus(Long id, UpdateLoanStatusRequest request) {
        log.info("event=loan_status_update_requested, loan_id={}, requested_status={}", 
                id, request.getStatus());

        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("event=loan_status_update_failed, reason=loan_not_found, loan_id={}", id); 
                    return new LoanApplicationNotFoundException(id);
                });

        LoanApplicationEntity.LoanStatus currentStatus = loan.getStatus();
        LoanApplicationEntity.LoanStatus newStatus;
        try {
            newStatus = LoanApplicationEntity.LoanStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("event=loan_status_update_failed, reason=invalid_status, loan_id={}, status={}", 
                    id, request.getStatus()); 
            throw new IllegalArgumentException("Invalid status: " + request.getStatus());
        }

        validateStatusTransition(currentStatus, newStatus, id);

        if (newStatus == LoanApplicationEntity.LoanStatus.DISBURSED) {
            if (loan.getRepaymentSchedules() == null || loan.getRepaymentSchedules().isEmpty()) {
                generateRepaymentSchedules(loan);
            }
        }

        loan.setStatus(newStatus);
        LoanApplicationEntity updated = loanApplicationRepository.save(loan);
        
        log.info("event=loan_status_updated, loan_id={}, old_status={}, new_status={}", 
                id, currentStatus, newStatus);

        return mapToResponse(updated);
    }

    private void validateStatusTransition(LoanApplicationEntity.LoanStatus current,
            LoanApplicationEntity.LoanStatus next, Long loanId) {
        switch (current) {
            case SUBMITTED:
                if (next != LoanApplicationEntity.LoanStatus.APPROVED
                        && next != LoanApplicationEntity.LoanStatus.REJECTED) {
                    log.warn("event=loan_status_transition_forbidden, loan_id={}, current={}, attempted={}, reason=SUBMITTED_only_to_APPROVED_or_REJECTED", 
                            loanId, current, next);  
                    throw new IllegalArgumentException(
                            "SUBMITTED can only be changed to APPROVED or REJECTED");
                }
                break;
            case APPROVED:
                if (next != LoanApplicationEntity.LoanStatus.DISBURSED) {
                    log.warn("event=loan_status_transition_forbidden, loan_id={}, current={}, attempted={}, reason=APPROVED_only_to_DISBURSED", 
                            loanId, current, next);  
                    throw new IllegalArgumentException(
                            "APPROVED can only be changed to DISBURSED");
                }
                break;
            case DISBURSED:
                if (next != LoanApplicationEntity.LoanStatus.CLOSED) {
                    log.warn("event=loan_status_transition_forbidden, loan_id={}, current={}, attempted={}, reason=DISBURSED_only_to_CLOSED", 
                            loanId, current, next);  
                    throw new IllegalArgumentException(
                            "DISBURSED can only be changed to CLOSED");
                }
                long unpaidCount = repaymentScheduleRepository.countByLoanIdAndStatusNotPaid(loanId);
                if (unpaidCount > 0) {
                    log.warn("event=loan_status_transition_forbidden, loan_id={}, current={}, attempted={}, reason=unpaid_schedules_remaining, unpaid_count={}", 
                            loanId, current, next, unpaidCount);  
                    throw new IllegalArgumentException(
                            "Cannot close loan. All repayment schedules must be PAID first.");
                }
                break;
            case REJECTED:
                log.warn("event=loan_status_transition_forbidden, loan_id={}, current={}, attempted={}, reason=REJECTED_is_final", 
                        loanId, current, next);  
                throw new IllegalArgumentException(
                        "REJECTED is a final status and cannot be changed");
            case CLOSED:
                log.warn("event=loan_status_transition_forbidden, loan_id={}, current={}, attempted={}, reason=CLOSED_is_final", 
                        loanId, current, next);  
                throw new IllegalArgumentException(
                        "CLOSED is a final status and cannot be changed");
            default:
                throw new IllegalArgumentException("Invalid status transition");
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getSummaryByStatus() {
        log.info("event=loan_summary_by_status_requested"); 
        List<Object[]> results = loanApplicationRepository.getSummaryByStatusRaw();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("status", row[0]);
            map.put("total_loan", row[1]);
            map.put("total_amount", row[2]);
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getOutstandingPerCustomer() {
        log.info("event=loan_outstanding_per_customer_requested");  
        List<Object[]> results = loanApplicationRepository.getOutstandingPerCustomerRaw();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("customer_id", row[0]);
            map.put("customer_name", row[1]);
            map.put("outstanding_amount", row[2]);
            return map;
        }).collect(Collectors.toList());
    }

    private LoanApplicationResponse mapToResponse(LoanApplicationEntity entity) {
        return LoanApplicationResponse.builder()
                .id(entity.getId())
                .loanAmount(entity.getLoanAmount())
                .tenorMonth(entity.getTenorMonth())
                .purpose(entity.getPurpose())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .customer(mapCustomerSummary(entity.getCustomer()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private CustomerSummaryResponse mapCustomerSummary(CustomerEntity entity) {
        if (entity == null) return null;
        return CustomerSummaryResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .nik(entity.getNik())
                .email(entity.getEmail())
                .build();
    }
}