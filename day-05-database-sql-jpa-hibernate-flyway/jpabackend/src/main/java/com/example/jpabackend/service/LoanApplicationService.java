package com.example.jpabackend.service;

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

        return toResponse(loan);
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public LoanApplicationResponse getById(Long id) {

        LoanApplicationEntity loan = loanRepo.findByIdWithCustomer(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));

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
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));

        // VALIDASI STATUS
        if (!List.of("SUBMITTED", "APPROVED", "REJECTED").contains(status)) {
            throw new RuntimeException("INVALID_STATUS");
        }

        if (status.equals("APPROVED")) {
            repaymentService.generateSchedule(loan);
        }

        loan.setStatus(status);
        loan.setUpdatedAt(ZonedDateTime.now());

        loanRepo.save(loan);

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