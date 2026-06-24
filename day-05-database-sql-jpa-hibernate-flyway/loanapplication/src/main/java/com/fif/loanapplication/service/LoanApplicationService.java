package com.fif.loanapplication.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fif.loanapplication.dto.customer.CustomerSummaryResponse;
import com.fif.loanapplication.dto.loanapplication.CreateLoanApplicationRequest;
import com.fif.loanapplication.dto.loanapplication.LoanApplicationResponse;
import com.fif.loanapplication.dto.loanapplication.UpdateLoanStatusRequest;
import com.fif.loanapplication.entity.CustomerEntity;
import com.fif.loanapplication.entity.LoanApplicationEntity;
import com.fif.loanapplication.entity.RepaymentScheduleEntity;
import com.fif.loanapplication.entity.enums.LoanStatus;
import com.fif.loanapplication.exception.CustomerNotFoundException;
import com.fif.loanapplication.exception.LoanApplicationNotFoundException;
import com.fif.loanapplication.repository.CustomerRepository;
import com.fif.loanapplication.repository.LoanApplicationRepository;
import com.fif.loanapplication.repository.RepaymentScheduleRepository;
import com.fif.loanapplication.utils.LoanApplicationUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final LoanApplicationUtils loanApplicationUtils;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final RepaymentScheduleService repaymentScheduleService;

    // Helper toLoanAppResponse
    private LoanApplicationResponse toLoanApplicationResponse(LoanApplicationEntity loanApplication) {
        CustomerEntity customer = loanApplication.getCustomer();
        CustomerSummaryResponse customerResponse = CustomerSummaryResponse.builder()
                .uid(customer.getUid())
                .nik(customer.getNik())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .build();

        return LoanApplicationResponse.builder()
                .uid(loanApplication.getUid())
                .loanAmount(loanApplication.getLoanAmount())
                .tenorMonth(loanApplication.getTenorMonth())
                .purpose(loanApplication.getPurpose())
                .status(loanApplication.getStatus())
                .createdAt(loanApplication.getCreatedAt())
                .updatedAt(loanApplication.getUpdatedAt())
                .customer(customerResponse)
                .build();
    }

    // Service Create Loan
    @Transactional
    public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerUid())
                .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerUid()));

        LoanApplicationEntity loanApplication = LoanApplicationEntity.builder()
                .customer(customer)
                .loanAmount(request.getLoanAmount())
                .tenorMonth(request.getTenorMonth())
                .purpose(request.getPurpose())
                .build();

        LoanApplicationEntity savedLoanApplication = loanApplicationRepository.save(loanApplication);

        List<RepaymentScheduleEntity> repaymentSchedules = repaymentScheduleService
                .generateRepaymentSchedules(savedLoanApplication);

        repaymentScheduleRepository.saveAll(repaymentSchedules);

        return toLoanApplicationResponse(savedLoanApplication);

    }

    // Service Get Loan By ID
    @Transactional
    public LoanApplicationResponse getLoanByUid(UUID uid) {
        LoanApplicationEntity loanApplication = loanApplicationRepository.findByUidWithCustomer(uid)
                .orElseThrow(() -> new LoanApplicationNotFoundException(uid));
        return toLoanApplicationResponse(loanApplication);
    }

    // Service Get Loans
    @Transactional
    public List<LoanApplicationResponse> getLoans(LoanStatus status) {
        List<LoanApplicationEntity> loans;

        if (status == null) {
            loans = loanApplicationRepository.findAllWithCustomer();
        } else {
            loans = loanApplicationRepository.findByStatus(status);
        }
        return loans.stream()
                .map(this::toLoanApplicationResponse)
                .toList();
    }

    // Service Patch Loan Status
    @Transactional
    public LoanApplicationResponse approveLoanApplicationByUid(UUID uid, UpdateLoanStatusRequest request) {
        LoanApplicationEntity loanApplication = loanApplicationRepository.findById(uid)
                .orElseThrow(() -> new LoanApplicationNotFoundException(uid));

        LoanStatus currentStatus = loanApplication.getStatus();
        LoanStatus newStatus = request.getStatus();

        loanApplicationUtils.validateStatusTransition(currentStatus, newStatus);
        loanApplication.setStatus(newStatus);

        LoanApplicationEntity updatedLoanApplication = loanApplicationRepository.save(loanApplication);
        return toLoanApplicationResponse(updatedLoanApplication);

    }

    // Service Get Loan Application By Customer Uid
    @Transactional
    public List<LoanApplicationResponse> getLoanApplicationByCustomerUid(UUID customerUid) {
        List<LoanApplicationEntity> loanApplication = loanApplicationRepository.findByCustomerUid(customerUid);
        return loanApplication.stream().map(this::toLoanApplicationResponse).toList();
    }

}
