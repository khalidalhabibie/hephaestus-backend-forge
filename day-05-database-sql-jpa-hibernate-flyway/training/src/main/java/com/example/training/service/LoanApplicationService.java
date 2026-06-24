package com.example.training.service;

import java.util.List;
import java.util.UUID;

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

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleService repaymentScheduleService;

    @Transactional
    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", "Customer not found with id: " + request.getCustomerId()));

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(customer);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanStatus.SUBMITTED);

        return toResponse(loanApplicationRepository.save(loan));
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse findById(UUID id) {
        LoanApplicationEntity loan = loanApplicationRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> new NotFoundException("LOAN_APPLICATION_NOT_FOUND", "Loan application not found with id: " + id));
        return toResponse(loan);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findAll() {
        return loanApplicationRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByStatus(String status) {
        LoanStatus loanStatus = LoanStatus.valueOf(status.toUpperCase());
        return loanApplicationRepository.findByStatus(loanStatus).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByCustomerId(UUID customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("CUSTOMER_NOT_FOUND", "Customer not found with id: " + customerId);
        }
        return loanApplicationRepository.findLoansByCustomerId(customerId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public LoanApplicationResponse updateStatus(UUID id, UpdateLoanStatusRequest request) {
        LoanApplicationEntity loan = loanApplicationRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> new NotFoundException("LOAN_APPLICATION_NOT_FOUND", "Loan application not found with id: " + id));

        LoanStatus current = loan.getStatus();
        LoanStatus next = LoanStatus.valueOf(request.getStatus().toUpperCase());

        validateTransition(current, next, loan);

        loan.setStatus(next);
        loan = loanApplicationRepository.save(loan);

        // Auto-generate repayment schedule when loan becomes DISBURSED
        if (next == LoanStatus.DISBURSED) {
            repaymentScheduleService.generateSchedule(loan.getId());
        }

        return toResponse(loan);
    }

    private void validateTransition(LoanStatus current, LoanStatus next, LoanApplicationEntity loan) {
        if (current.isFinal()) {
            throw new IllegalStateException("Loan is already in final status: " + current);
        }
        if (!current.canTransitionTo(next)) {
            throw new IllegalStateException(current + " loan can only transition to its allowed next statuses");
        }
        // DISBURSED → CLOSED: all repayment schedules must be PAID
        if (current == LoanStatus.DISBURSED && next == LoanStatus.CLOSED) {
            boolean allPaid = loan.getRepaymentSchedules() != null
                    && !loan.getRepaymentSchedules().isEmpty()
                    && loan.getRepaymentSchedules().stream().allMatch(s -> s.getStatus() == RepaymentStatus.PAID);
            if (!allPaid) {
                throw new IllegalStateException("All repayment schedules must be PAID before closing the loan");
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
