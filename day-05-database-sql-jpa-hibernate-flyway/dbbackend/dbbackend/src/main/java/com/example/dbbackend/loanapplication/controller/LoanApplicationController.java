package com.example.dbbackend.loanapplication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.dbbackend.common.dto.ApiResponse;
import com.example.dbbackend.loanapplication.dto.CreateLoanApplicationRequest;
import com.example.dbbackend.loanapplication.dto.LoanApplicationResponse;
import com.example.dbbackend.loanapplication.dto.UpdateLoanStatusRequest;
import com.example.dbbackend.loanapplication.service.LoanApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService service;

    public LoanApplicationController(LoanApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<LoanApplicationResponse> create(
            @Valid @RequestBody CreateLoanApplicationRequest request) {

        return ApiResponse.success("Loan created successfully",
                service.createLoan(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<LoanApplicationResponse> getById(@PathVariable Long id) {
        return ApiResponse.success("Loan retrieved successfully",
                service.getLoanById(id));
    }

    @GetMapping
    public ApiResponse<List<LoanApplicationResponse>> getAll(
            @RequestParam(required = false) String status) {
        if (status != null) {
            return ApiResponse.success(
                    "Loan filtered by status",
                    service.getByStatus(status));
        }
        return ApiResponse.success(
                "All loan retrieved",
                service.getAll());
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<LoanApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateLoanStatusRequest request) {
        return ApiResponse.success(
                "Status updated successfully",
                service.updateStatus(id, request.getStatus()));
    }
}
