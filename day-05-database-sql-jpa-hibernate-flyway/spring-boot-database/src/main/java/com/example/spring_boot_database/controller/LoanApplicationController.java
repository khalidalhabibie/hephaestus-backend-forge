package com.example.spring_boot_database.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.spring_boot_database.dto.*;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.service.LoanApplicationService;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanService;

    @PostMapping
    public ApiResponse<LoanApplicationResponse> create(
            @Valid @RequestBody CreateLoanApplicationRequest request) {

        return ApiResponse.success(
                loanService.createLoanApplication(request),
                "Loan application created successfully"
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<LoanApplicationResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(
                loanService.findById(id),
                "Loan application retrieved successfully"
        );
    }

    /**
     * Get all loan applications with optional filters:
     * - status
     * - startDate
     * - endDate
     *
     * Example:
     * GET /api/v1/loan-applications?status=SUBMITTED&startDate=2026-01-01&endDate=2026-01-31
     */
    @GetMapping
    public ApiResponse<List<LoanApplicationResponse>> getAll(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ApiResponse.success(
                loanService.findLoan(status, startDate, endDate),
                "Loan applications retrieved successfully"
        );
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<LoanApplicationResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLoanStatusRequest request) {

        return ApiResponse.success(
                loanService.updateStatus(id, request),
                "Loan status updated successfully"
        );
    }

    @GetMapping("/{loanId}/repayment-schedules")
    public ApiResponse<List<RepaymentScheduleResponse>> getSchedules(
            @PathVariable Long loanId) {

        return ApiResponse.success(
                loanService.getSchedules(loanId),
                "Repayment schedules retrieved successfully"
        );
    }

    /**
     * Endpoint summary total loan by status.
     *
     * Example:
     * GET /api/v1/loan-applications/summary/by-status
     */
    @GetMapping("/summary/by-status")
    public ApiResponse<List<LoanSummaryByStatusResponse>> getSummaryByStatus() {
        return ApiResponse.success(
                loanService.getSummaryByStatus(),
                "Loan summary by status retrieved successfully"
        );
    }

    /**
     * Get paginated loan applications with optional filters:
     * - status
     * - startDate
     * - endDate
     *
     * Example:
     * GET /api/v1/loan-applications/paged?status=APPROVED&startDate=2026-01-01&endDate=2026-01-31&page=0&size=10
     */
    @GetMapping("/paged")
    public ApiResponse<org.springframework.data.domain.Page<LoanApplicationResponse>> getPaged(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.success(
                loanService.findLoanPaged(status, startDate, endDate, pageable),
                "Loan applications paginated"
        );
    }
}