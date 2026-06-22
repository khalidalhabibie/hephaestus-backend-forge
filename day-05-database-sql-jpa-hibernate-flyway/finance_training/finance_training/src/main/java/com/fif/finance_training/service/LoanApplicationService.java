package com.fif.finance_training.service;

import com.fif.finance_training.dto.CreateLoanApplicationRequest;
import com.fif.finance_training.dto.LoanApplicationResponse;
import com.fif.finance_training.dto.UpdateLoanStatusRequest;
import com.fif.finance_training.entity.CustomerEntity;
import com.fif.finance_training.entity.LoanApplicationEntity;
import com.fif.finance_training.entity.RepaymentScheduleEntity;
import com.fif.finance_training.entity.enums.LoanStatus;
import com.fif.finance_training.entity.enums.RepaymentStatus;
import com.fif.finance_training.exception.CustomerNotFoundException;
import com.fif.finance_training.exception.LoanApplicationNotFoundException;
import com.fif.finance_training.repository.CustomerRepository;
import com.fif.finance_training.repository.LoanApplicationRepository;
import com.fif.finance_training.repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleService repaymentScheduleService;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional
    public LoanApplicationResponse createLoan(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + request.getCustomerId()));

        LoanApplicationEntity entity = LoanApplicationEntity.builder()
                .customer(customer)
                .loanAmount(request.getLoanAmount())
                .tenorMonth(request.getTenorMonth())
                .purpose(request.getPurpose())
                .status(LoanStatus.SUBMITTED)
                .build();

        LoanApplicationEntity saved = loanApplicationRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public LoanApplicationResponse updateLoanStatus(Long id, UpdateLoanStatusRequest request) {
        LoanApplicationEntity loan = loanApplicationRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan not found with id: " + id));

        LoanStatus currentStatus = loan.getStatus();
        
        LoanStatus newStatus;
        try {
            newStatus = LoanStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + request.getStatus());
        }

        validateStatusTransition(currentStatus, newStatus, loan);

        loan.setStatus(newStatus);
        
        if (currentStatus == LoanStatus.APPROVED && newStatus == LoanStatus.DISBURSED) {
            List<RepaymentScheduleEntity> existing = repaymentScheduleRepository.findByLoanApplicationId(loan.getId());
            if (existing.isEmpty()) {
                repaymentScheduleService.generateSchedulesForLoan(loan);
            }
        }

        LoanApplicationEntity updated = loanApplicationRepository.save(loan);
        return mapToResponse(updated);
    }

    private void validateStatusTransition(LoanStatus currentStatus, LoanStatus newStatus, LoanApplicationEntity loan) {
        boolean isValid = false;
        
        switch (currentStatus) {
            case SUBMITTED:
                isValid = newStatus == LoanStatus.APPROVED || newStatus == LoanStatus.REJECTED;
                break;
            case APPROVED:
                isValid = newStatus == LoanStatus.DISBURSED;
                break;
            case DISBURSED:
                if (newStatus == LoanStatus.CLOSED) {
                    List<RepaymentScheduleEntity> schedules = repaymentScheduleRepository.findByLoanApplicationId(loan.getId());
                    boolean allPaid = schedules.stream().allMatch(s -> s.getStatus() == RepaymentStatus.PAID);
                    if (!allPaid) {
                        throw new IllegalArgumentException("Cannot close loan. Not all repayment schedules are PAID.");
                    }
                    isValid = true;
                }
                break;
            case REJECTED:
            case CLOSED:
                throw new IllegalArgumentException("Status is final. Cannot be changed.");
        }

        if (!isValid) {
            throw new IllegalArgumentException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getLoanById(Long id) {
        LoanApplicationEntity entity = loanApplicationRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan not found with id: " + id));
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAllLoans() {
        return loanApplicationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getLoansByCustomerId(Long customerId) {
        return loanApplicationRepository.findLoansByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getLoansByStatus(String status) {
        LoanStatus loanStatus = LoanStatus.valueOf(status.toUpperCase());
        return loanApplicationRepository.findByStatus(loanStatus).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private LoanApplicationResponse mapToResponse(LoanApplicationEntity entity) {
        LoanApplicationResponse.CustomerDetail customerDetail = LoanApplicationResponse.CustomerDetail.builder()
                .id(entity.getCustomer().getId())
                .fullName(entity.getCustomer().getFullName())
                .nik(entity.getCustomer().getNik())
                .email(entity.getCustomer().getEmail())
                .build();

        return LoanApplicationResponse.builder()
                .id(entity.getId())
                .loanAmount(entity.getLoanAmount())
                .tenorMonth(entity.getTenorMonth())
                .purpose(entity.getPurpose())
                .status(entity.getStatus().name())
                .customer(customerDetail)
                .build();
    }
}