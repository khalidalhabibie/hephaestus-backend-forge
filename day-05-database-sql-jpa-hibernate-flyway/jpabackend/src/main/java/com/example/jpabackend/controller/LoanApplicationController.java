package com.example.jpabackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpabackend.dto.common.ApiResponse;
import com.example.jpabackend.dto.request.CreateLoanApplicationRequest;
import com.example.jpabackend.dto.request.UpdateLoanStatusRequest;
import com.example.jpabackend.dto.response.LoanApplicationResponse;
import com.example.jpabackend.enums.LoanStatus;
import com.example.jpabackend.service.LoanApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService service;

    @PostMapping
    public ApiResponse<LoanApplicationResponse> create(@RequestBody @Valid CreateLoanApplicationRequest request) {
        return ApiResponse.success("Created", service.createLoanApplication(request));
    }
    
    @GetMapping("/{id}")
    public ApiResponse<LoanApplicationResponse> get(@PathVariable Long id) {
        return ApiResponse.success("OK", service.getLoanById(id));
    }

    @GetMapping
    public ApiResponse<List<LoanApplicationResponse>> getAll(
        @RequestParam(required = false) LoanStatus status) {
        if (status != null) {
            return ApiResponse.success("Filtered", service.getLoansByStatus(status));
        }
        return ApiResponse.success("OK", service.getAllLoans());
    }

    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<LoanApplicationResponse>> byCustomer(@PathVariable Long customerId) {
        return ApiResponse.success("OK", service.getLoansByCustomer(customerId));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<LoanApplicationResponse> updateStatus(
        @PathVariable Long id,
        @RequestBody @Valid UpdateLoanStatusRequest request) {
        return ApiResponse.success("Updated", service.updateLoanStatus(id, request));
    }
}