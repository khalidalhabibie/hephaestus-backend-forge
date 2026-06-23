package com.example.training.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.CustomerSummaryResponse;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.dto.UpdateLoanStatusRequest;
import com.example.training.entity.CustomerEntity;
import com.example.training.entity.LoanApplicationEntity;
import com.example.training.enums.LoanStatus;
import com.example.training.enums.RepaymentStatus;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.CustomerRepository;
import com.example.training.repository.LoanApplicationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleService repaymentScheduleService;

    @Transactional
    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=loan_create_request customer_id={} loan_amount={} tenor={} correlation_id={}",
                request.getCustomerId(), request.getLoanAmount(), request.getTenorMonth(), correlationId);

        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> {
                    log.warn("event=loan_submit_failed reason=customer_not_found customer_id={} correlation_id={}",
                            request.getCustomerId(), correlationId);
                    return new NotFoundException("CUSTOMER_NOT_FOUND",
                            "Customer not found with id: " + request.getCustomerId());
                });

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(customer);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanStatus.SUBMITTED);

        log.debug("event=loan_save_request correlation_id={}", correlationId);
        LoanApplicationEntity savedLoan = loanApplicationRepository.save(loan);
        log.debug("event=loan_saved loan_id={} customer_id={} correlation_id={}",
                savedLoan.getId(), customer.getId(), correlationId);

        log.info("event=loan_application_submitted loan_id={} customer_id={} correlation_id={}",
                savedLoan.getId(), customer.getId(), correlationId);

        return toResponse(savedLoan);
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse findById(UUID id) {
        String correlationId = MDC.get("correlation_id");

        return loanApplicationRepository.findByIdWithCustomer(id)
                .map(this::toResponse)
                .orElseThrow(() -> {
                    log.warn("event=loan_not_found loan_id={} correlation_id={}", id, correlationId);
                    return new NotFoundException("LOAN_APPLICATION_NOT_FOUND",
                            "Loan application not found with id: " + id);
                });
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findAll() {
        log.debug("event=loan_find_all correlation_id={}", MDC.get("correlation_id"));
        List<LoanApplicationEntity> loans = loanApplicationRepository.findAll();
        log.debug("event=loan_find_all_result count={} correlation_id={}", loans.size(), MDC.get("correlation_id"));
        return loans.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByStatus(String status) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=loan_find_by_status status={} correlation_id={}", status, correlationId);
        LoanStatus loanStatus = LoanStatus.valueOf(status.toUpperCase());
        List<LoanApplicationEntity> loans = loanApplicationRepository.findByStatus(loanStatus);
        log.debug("event=loan_find_by_status_result status={} count={} correlation_id={}", status, loans.size(), correlationId);
        return loans.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByCustomerId(UUID customerId) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=loan_find_by_customer customer_id={} correlation_id={}", customerId, correlationId);

        if (!customerRepository.existsById(customerId)) {
            log.warn("event=customer_not_found customer_id={} correlation_id={}", customerId, correlationId);
            throw new NotFoundException("CUSTOMER_NOT_FOUND",
                    "Customer not found with id: " + customerId);
        }

        List<LoanApplicationEntity> loans = loanApplicationRepository.findLoansByCustomerId(customerId);
        log.debug("event=loan_find_by_customer_result customer_id={} count={} correlation_id={}", customerId, loans.size(), correlationId);
        return loans.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public LoanApplicationResponse updateStatus(UUID id, UpdateLoanStatusRequest request) {
        String correlationId = MDC.get("correlation_id");

        LoanApplicationEntity loan = loanApplicationRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> {
                    log.warn("event=loan_not_found loan_id={} correlation_id={}", id, correlationId);
                    return new NotFoundException("LOAN_APPLICATION_NOT_FOUND",
                            "Loan application not found with id: " + id);
                });

        LoanStatus current = loan.getStatus();
        LoanStatus next = LoanStatus.valueOf(request.getStatus().toUpperCase());
        log.debug("event=loan_update_status_request loan_id={} from={} to={} correlation_id={}",
                id, current, next, correlationId);

        validateTransition(current, next, loan);

        loan.setStatus(next);
        log.debug("event=loan_save_after_status_change loan_id={} correlation_id={}", id, correlationId);
        LoanApplicationEntity savedLoan = loanApplicationRepository.save(loan);

        String eventName;
        if (next == LoanStatus.APPROVED) {
            eventName = "loan_application_approved";
        } else if (next == LoanStatus.REJECTED) {
            eventName = "loan_application_rejected";
        } else {
            eventName = "loan_status_updated";
        }

        log.info("event={} loan_id={} from_status={} to_status={} correlation_id={}",
                eventName, savedLoan.getId(), current, next, correlationId);

        if (next == LoanStatus.DISBURSED) {
            log.debug("event=loan_generating_schedule loan_id={} correlation_id={}", savedLoan.getId(), correlationId);
            repaymentScheduleService.generateSchedule(savedLoan.getId());
        }

        log.debug("event=loan_update_status_complete loan_id={} final_status={} correlation_id={}",
                savedLoan.getId(), next, correlationId);
        return toResponse(savedLoan);
    }

    private void validateTransition(LoanStatus current, LoanStatus next, LoanApplicationEntity loan) {
        log.debug("event=loan_validate_transition from={} to={} correlation_id={}",
                current, next, MDC.get("correlation_id"));
        if (current.isFinal()) {
            throw new IllegalStateException("Loan is already in final status: " + current);
        }

        if (!current.canTransitionTo(next)) {
            throw new IllegalStateException(
                    current + " loan can only transition to its allowed next statuses");
        }

        if (current == LoanStatus.DISBURSED && next == LoanStatus.CLOSED) {
            boolean allPaid = loan.getRepaymentSchedules() != null
                    && !loan.getRepaymentSchedules().isEmpty()
                    && loan.getRepaymentSchedules().stream()
                    .allMatch(s -> s.getStatus() == RepaymentStatus.PAID);

            if (!allPaid) {
                throw new IllegalStateException(
                        "All repayment schedules must be PAID before closing the loan");
            }
        }
    }

    private LoanApplicationResponse toResponse(LoanApplicationEntity loan) {
        CustomerEntity c = loan.getCustomer();

        CustomerSummaryResponse customerSummary = CustomerSummaryResponse.builder()
                .id(c.getId())
                .fullName(c.getFullName())
                .nik(c.getNik())
                .email(c.getEmail())
                .phoneNumber(c.getPhoneNumber())
                .build();

        return LoanApplicationResponse.builder()
                .id(loan.getId())
                .customer(customerSummary)
                .loanAmount(loan.getLoanAmount())
                .tenorMonth(loan.getTenorMonth())
                .purpose(loan.getPurpose())
                .status(loan.getStatus().name())
                .createdAt(loan.getCreatedAt())
                .updatedAt(loan.getUpdatedAt())
                .build();
    }
}
