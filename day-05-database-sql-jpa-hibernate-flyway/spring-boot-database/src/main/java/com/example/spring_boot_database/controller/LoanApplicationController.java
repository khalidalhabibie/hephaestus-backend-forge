package com.example.spring_boot_database.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.spring_boot_database.dto.*;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.service.LoanApplicationService;

import jakarta.validation.Valid;

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

    @GetMapping
    public ApiResponse<List<LoanApplicationResponse>> getAll(
            @RequestParam(required = false) Status status) {

        return ApiResponse.success(
                loanService.findLoan(status),
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
}
