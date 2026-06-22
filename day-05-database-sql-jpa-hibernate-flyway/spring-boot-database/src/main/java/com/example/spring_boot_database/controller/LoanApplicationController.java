package com.example.spring_boot_database.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.CreateLoanApplicationRequest;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.dto.UpdateLoanStatusRequest;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.service.LoanApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @PostMapping
    public ApiResponse<LoanApplicationResponse> create(@Valid @RequestBody CreateLoanApplicationRequest request) {
        return ApiResponse.success(loanApplicationService.createLoanApplication(request), "Loan application retrieved successfully ");
    }

    @GetMapping("/{id}")
    public ApiResponse<LoanApplicationResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(loanApplicationService.findById(id), "Loan application Found");
    }

    @GetMapping
    public ApiResponse<List<LoanApplicationResponse>> getByName(@RequestParam(required = false) Status status) {
        return ApiResponse.success(loanApplicationService.findLoanApplication(status), "Customer Found");
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<LoanApplicationResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateLoanStatusRequest request) {
        return ApiResponse.success(loanApplicationService.updateStatusLoanApplication(id, request), "Loan application Found");
    }

    @GetMapping("/{loan_application_id}/repayment-schedules")
    public ApiResponse<List<RepaymentScheduleResponse>> getLoanApplicationById(@PathVariable Long loan_application_id) {
        return ApiResponse.success(loanApplicationService.findRepaymentScheduleByLoanId(loan_application_id), "Customer Found");
    }

}
