package com.example.training.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.dto.ApiResponse;
import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.dto.UpdateLoanStatusRequest;
import com.example.training.service.LoanApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    @PostMapping("/loan-applications")
    public ApiResponse<LoanApplicationResponse> create(@Valid @RequestBody CreateLoanApplicationRequest request) {
        return ApiResponse.success(loanApplicationService.create(request));
    }

    @GetMapping("/loan-applications/{id}")
    public ApiResponse<LoanApplicationResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(loanApplicationService.findById(id));
    }

    @GetMapping("/loan-applications")
    public ApiResponse<List<LoanApplicationResponse>> getAll(
            @RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return ApiResponse.success(loanApplicationService.findByStatus(status));
        }
        return ApiResponse.success(loanApplicationService.findAll());
    }

    @GetMapping("/customers/{customerId}/loan-applications")
    public ApiResponse<List<LoanApplicationResponse>> getByCustomerId(@PathVariable UUID customerId) {
        return ApiResponse.success(loanApplicationService.findByCustomerId(customerId));
    }

    @PatchMapping("/loan-applications/{id}/status")
    public ApiResponse<LoanApplicationResponse> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateLoanStatusRequest request) {
        return ApiResponse.success(loanApplicationService.updateStatus(id, request));
    }
}
