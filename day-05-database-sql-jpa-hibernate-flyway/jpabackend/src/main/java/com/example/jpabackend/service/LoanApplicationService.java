package com.example.jpabackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jpabackend.entity.*;
import com.example.jpabackend.repository.*;
import com.example.jpabackend.dto.*;
import com.example.jpabackend.exception.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class LoanApplicationService {

    private static final Logger log = LoggerFactory.getLogger(LoanApplicationService.class);

    private final LoanApplicationRepository loanRepo;
    private final CustomerRepository customerRepo;
    private final RepaymentScheduleService repaymentService;

    public LoanApplicationService(
            LoanApplicationRepository loanRepo,
            CustomerRepository customerRepo,
            RepaymentScheduleService repaymentService) {

        this.loanRepo = loanRepo;
        this.customerRepo = customerRepo;
        this.repaymentService = repaymentService;
    }

    // CREATE LOAN
    @Transactional
    public LoanApplicationResponse createLoan(CreateLoanApplicationRequest req) {

        CustomerEntity customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(req.getCustomerId()));

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(customer);
        loan.setLoanAmount(req.getLoanAmount());
        loan.setTenorMonth(req.getTenorMonth());
        loan.setPurpose(req.getPurpose());
        loan.setStatus("SUBMITTED");

        loan.setCreatedAt(ZonedDateTime.now());
        loan.setUpdatedAt(ZonedDateTime.now());

        loanRepo.save(loan);
        
        log.info(
                "event=loan_application_submitted application_id={} customer_id={}",
                loan.getId(),
                customer.getId());

        return toResponse(loan);
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public LoanApplicationResponse getById(Long id) {

        LoanApplicationEntity loan = loanRepo.findByIdWithCustomer(id)
        .orElseThrow(() -> {
                    log.warn(
                            "event=loan_application_not_found application_id={}",
                            id);
                    return new LoanApplicationNotFoundException(id);
                });

        return toResponse(loan);
    }

    // GET ALL
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAll() {

        return loanRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // GET BY CUSTOMER
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByCustomerId(Long customerId) {

        return loanRepo.findByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // FILTER STATUS
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByStatus(String status) {

        return loanRepo.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // UPDATE STATUS (VALIDATED)
    @Transactional
    public LoanApplicationResponse updateStatus(Long id, String status) {

        LoanApplicationEntity loan = loanRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn(
                            "event=loan_application_not_found application_id={}",
                            id);
                    return new LoanApplicationNotFoundException(id);
                });
                
        status = status.trim().toUpperCase();
        // VALID STATUS BARU
        if (!List.of("SUBMITTED", "APPROVED", "REJECTED", "DISBURSED").contains(status)) {
            log.warn(
                    "event=validation_error field=status error=invalid_status value={}",
                    status          );
            throw new RuntimeException("INVALID_STATUS");
        }

        // OPTIONAL: VALIDASI FLOW (biar tidak loncat status)
        String currentStatus = loan.getStatus().trim().toUpperCase();
        String oldStatus = currentStatus; // simpan sebelum update
        
        log.info(
                "event=status_check application_id={} old_status={} new_status={}",
                loan.getId(),
                currentStatus,
                status);

        switch (currentStatus) {

            case "SUBMITTED":
                if (!status.equals("APPROVED")) {
                    log.warn(
                            "event=validation_error error=invalid_status_transition old_status={} new_status={}",
                            currentStatus,
                            status                  );
                    throw new RuntimeException("INVALID_STATUS_TRANSITION");
                }
                break;

            case "APPROVED":
                if (!status.equals("DISBURSED")) {
                    log.warn(
                            "event=validation_error error=invalid_status_transition old_status={} new_status={}",
                            currentStatus,
                            status                  );
                    throw new RuntimeException("INVALID_STATUS_TRANSITION");
                }
                break;
        }

        // ONLY GENERATE ON FIRST DISBURSE
        
        loan.setStatus(status);
        loan.setUpdatedAt(ZonedDateTime.now());

        loanRepo.saveAndFlush(loan);


        if ("APPROVED".equals(status)) {
            log.info(
                    "event=loan_application_approved application_id={} customer_id={} old_status={} new_status={}",
                    loan.getId(),
                    loan.getCustomer().getId(),
                    oldStatus,
                    status);
        }

        if ("REJECTED".equals(status)) {
            log.info(
                    "event=loan_application_rejected application_id={} customer_id={} old_status={} new_status={}",
                    loan.getId(),
                    loan.getCustomer().getId(),
                    oldStatus,
                    status);
        }

        // BARU SETELAH STATUS UPDATE
        // log business event (selalu kalau DISBURSED)
        if ("DISBURSED".equals(status) && !"DISBURSED".equals(currentStatus)) {
            log.info(
                    "event=loan_application_disbursed application_id={} customer_id={} old_status={} new_status={}",
                    loan.getId(),
                    loan.getCustomer().getId(),
                    oldStatus,
                    status);
        }

        // generate logic schedule hanya kalau belum ada
        if ("DISBURSED".equals(status)
                && !"DISBURSED".equals(currentStatus)
                && !repaymentService.existsByLoanId(loan.getId())) {

            repaymentService.generateSchedule(loan.getId());
        }

        return toResponse(loan);
        
    }

    // PAGINATION
    public Page<LoanApplicationResponse> getAll(int page, int size) {
        return loanRepo.findAll(PageRequest.of(page, size))
                .map(this::toResponse);
    }

    // FILTER BY DATE
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> filterByDate(
            ZonedDateTime start,
            ZonedDateTime end) {

        return loanRepo.findByCreatedAtBetween(start, end)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // // SUMMARY BY STATUS
    // @Transactional(readOnly = true)
    // public List<Map<String, Object>> getLoanSummary() {

    //     return loanRepo.getTotalLoanByStatus()
    //             .stream()
    //             .map(r -> Map.of(
    //                     "status", r[0],
    //                     "total", r[1] != null ? r[1] : BigDecimal.ZERO))
    //             .toList();
    // }

    //LOAN SUMMARY DTO
    @Transactional(readOnly = true)
    public List<LoanSummaryDTO> getLoanSummaryDTO() {
        return loanRepo.getLoanSummaryDTO();
    }

    // MAPPING
    private LoanApplicationResponse toResponse(LoanApplicationEntity loan) {

        CustomerEntity c = loan.getCustomer();

        CustomerSummaryResponse customer = new CustomerSummaryResponse(
                c.getId(),
                c.getFullName(),
                c.getNik(),
                c.getEmail());

        return new LoanApplicationResponse(
                loan.getId(),
                loan.getLoanAmount(),
                loan.getTenorMonth(),
                loan.getPurpose(),
                loan.getStatus(),
                loan.getCreatedAt(),
                loan.getUpdatedAt(),
                customer);
    }
}