package com.fif.loanapplication.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fif.loanapplication.common.log.LogContext;
import com.fif.loanapplication.common.log.LogMaskingUtil;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info(
                "event=loan_submit_requested status=started correlation_id={} customer_uid={}",
                LogContext.getCorrelationId(),
                request.getCustomerUid());

        CustomerEntity customer = customerRepository.findById(request.getCustomerUid())
                .orElseThrow(() -> {
                    log.warn(
                            "event=loan_submit_failed status=failed correlation_id={} reason=customer_not_found customer_uid={}",
                            LogContext.getCorrelationId(),
                            request.getCustomerUid());
                    return new CustomerNotFoundException(request.getCustomerUid());
                });

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

        log.info(
                "event=loan_submitted status=success correlation_id={} loan_uid={} customer_uid={} Email={} NIK={} repayment_schedule_count={}",
                LogContext.getCorrelationId(),
                savedLoanApplication.getUid(),
                customer.getUid(),
                LogMaskingUtil.maskEmail(customer.getEmail()),
                LogMaskingUtil.maskNik(customer.getNik()),
                repaymentSchedules.size());

        return toLoanApplicationResponse(savedLoanApplication);
    }

    // Service Get Loan By ID
    @Transactional
    public LoanApplicationResponse getLoanByUid(UUID uid) {
        log.info(
                "event=loan_get_by_uid_requested status=started correlation_id={} loan_uid={}",
                LogContext.getCorrelationId(),
                uid);

        LoanApplicationEntity loanApplication = loanApplicationRepository.findByUidWithCustomer(uid)
                .orElseThrow(() -> {
                    log.warn(
                            "event=loan_get_by_uid_failed status=failed correlation_id={} reason=loan_not_found loan_uid={}",
                            LogContext.getCorrelationId(),
                            uid);
                    return new LoanApplicationNotFoundException(uid);
                });

        log.info(
                "event=loan_get_by_uid_success status=success correlation_id={} loan_uid={}",
                LogContext.getCorrelationId(),
                loanApplication.getUid());

        return toLoanApplicationResponse(loanApplication);
    }

    // Service Get Loans
    @Transactional
    public List<LoanApplicationResponse> getLoans(LoanStatus status) {
        log.info(
                "event=loan_get_all_requested status=started correlation_id={} filter_status={}",
                LogContext.getCorrelationId(),
                status);

        List<LoanApplicationEntity> loans;

        if (status == null) {
            loans = loanApplicationRepository.findAllWithCustomer();
        } else {
            loans = loanApplicationRepository.findByStatus(status);
        }

        log.info(
                "event=loan_get_all_success status=success correlation_id={} total_data={}",
                LogContext.getCorrelationId(),
                loans.size());

        return loans.stream()
                .map(this::toLoanApplicationResponse)
                .toList();
    }

    // Service Patch Loan Status
    @Transactional
    public LoanApplicationResponse approveLoanApplicationByUid(UUID uid, UpdateLoanStatusRequest request) {
        log.info(
                "event=loan_review_requested status=started correlation_id={} loan_uid={} requested_status={}",
                LogContext.getCorrelationId(),
                uid,
                request.getStatus());

        LoanApplicationEntity loanApplication = loanApplicationRepository.findById(uid)
                .orElseThrow(() -> {
                    log.warn(
                            "event=loan_review_failed status=failed correlation_id={} reason=loan_not_found loan_uid={}",
                            LogContext.getCorrelationId(),
                            uid);
                    return new LoanApplicationNotFoundException(uid);
                });

        LoanStatus currentStatus = loanApplication.getStatus();
        LoanStatus newStatus = request.getStatus();

        loanApplicationUtils.validateStatusTransition(currentStatus, newStatus);

        loanApplication.setStatus(newStatus);

        LoanApplicationEntity updatedLoanApplication = loanApplicationRepository.save(loanApplication);

        if (newStatus == LoanStatus.APPROVED) {
            log.info(
                    "event=loan_reviewed action=approved status=success correlation_id={} loan_uid={} previous_status={} new_status={}",
                    LogContext.getCorrelationId(),
                    updatedLoanApplication.getUid(),
                    currentStatus,
                    newStatus);
        } else if (newStatus == LoanStatus.REJECTED) {
            log.info(
                    "event=loan_reviewed action=rejected status=success correlation_id={} loan_uid={} previous_status={} new_status={}",
                    LogContext.getCorrelationId(),
                    updatedLoanApplication.getUid(),
                    currentStatus,
                    newStatus);
        } else {
            log.info(
                    "event=loan_status_updated status=success correlation_id={} loan_uid={} previous_status={} new_status={}",
                    LogContext.getCorrelationId(),
                    updatedLoanApplication.getUid(),
                    currentStatus,
                    newStatus);
        }

        return toLoanApplicationResponse(updatedLoanApplication);
    }

    // Service Get Loan Application By Customer Uid
    @Transactional
    public List<LoanApplicationResponse> getLoanApplicationByCustomerUid(UUID customerUid) {
        log.info(
                "event=loan_get_by_customer_requested status=started correlation_id={} customer_uid={}",
                LogContext.getCorrelationId(),
                customerUid);

        List<LoanApplicationEntity> loanApplications = loanApplicationRepository.findByCustomerUid(customerUid);

        log.info(
                "event=loan_get_by_customer_success status=success correlation_id={} customer_uid={} total_data={}",
                LogContext.getCorrelationId(),
                customerUid,
                loanApplications.size());

        return loanApplications.stream()
                .map(this::toLoanApplicationResponse)
                .toList();
    }
}