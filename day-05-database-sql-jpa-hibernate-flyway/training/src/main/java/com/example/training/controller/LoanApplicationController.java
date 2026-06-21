package com.example.training.controller;

import com.example.training.dto.*;
import com.example.training.enums.LoanStatus;
import com.example.training.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanService;

    @PostMapping("/loan-applications")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> create(@Valid @RequestBody CreateLoanApplicationRequest request) {
        LoanApplicationResponse data = loanService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Loan application created successfully", data));
    }

    @GetMapping("/loan-applications/{id}")
    public ResponseEntity<ApiResponse<LoanApplicationDetailResponse>> getById(@PathVariable Long id) {
        LoanApplicationDetailResponse data = loanService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("Loan application retrieved successfully", data));
    }

    @GetMapping("/loan-applications")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getAll(
            @RequestParam(value = "status", required = false) LoanStatus status) {
        List<LoanApplicationResponse> data = status != null ? loanService.findByStatus(status) : loanService.findAll();
        return ResponseEntity.ok(ApiResponse.success("Loan applications retrieved successfully", data));
    }

    @GetMapping("/customers/{customerId}/loan-applications")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getByCustomerId(@PathVariable Long customerId) {
        List<LoanApplicationResponse> data = loanService.findByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success("Customer loan applications retrieved successfully", data));
    }

    @PatchMapping("/loan-applications/{id}/status")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLoanStatusRequest request) {
        LoanApplicationResponse data = loanService.updateStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Loan status updated successfully", data));
    }
}
