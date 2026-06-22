package com.fif.finance_training.controller;

import com.fif.finance_training.dto.ApiResponse;
import com.fif.finance_training.dto.CreateLoanApplicationRequest;
import com.fif.finance_training.dto.LoanApplicationResponse;
import com.fif.finance_training.dto.UpdateLoanStatusRequest;
import com.fif.finance_training.service.LoanApplicationService;
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
    private final LoanApplicationService loanApplicationService;
    // 1. Create Loan Application (POST /api/v1/loan-applications)
    @PostMapping("/loan-applications")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> createLoan(
            @Valid @RequestBody CreateLoanApplicationRequest request) {
        
        LoanApplicationResponse response = loanApplicationService.createLoan(request);
        
        ApiResponse<LoanApplicationResponse> apiResponse = ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Loan application submitted successfully")
                .data(response)
                .build();
                
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    // 2. Get Loan Application by ID (GET /api/v1/loan-applications/{id})
    @GetMapping("/loan-applications/{id}")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> getLoanById(
            @PathVariable("id") Long id) {
        
        LoanApplicationResponse response = loanApplicationService.getLoanById(id);
        
        ApiResponse<LoanApplicationResponse> apiResponse = ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Loan application retrieved successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }
    // 3. Get All Loans OR Filter by Status (GET /api/v1/loan-applications?status=SUBMITTED)
    @GetMapping("/loan-applications")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getAllLoans(
            @RequestParam(value = "status", required = false) String status) {
        
        List<LoanApplicationResponse> response;
        
        if (status != null && !status.trim().isEmpty()) {
            response = loanApplicationService.getLoansByStatus(status.toUpperCase());
        } else {
            response = loanApplicationService.getAllLoans();
        }
        
        ApiResponse<List<LoanApplicationResponse>> apiResponse = ApiResponse.<List<LoanApplicationResponse>>builder()
                .success(true)
                .message("Loan applications retrieved successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }

    // 4. Get Loans by Customer ID (GET /api/v1/customers/{customer_id}/loan-applications)
    @GetMapping("/customers/{customer_id}/loan-applications")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getLoansByCustomerId(
            @PathVariable("customer_id") Long customerId) {
        
        List<LoanApplicationResponse> response = loanApplicationService.getLoansByCustomerId(customerId);
        
        ApiResponse<List<LoanApplicationResponse>> apiResponse = ApiResponse.<List<LoanApplicationResponse>>builder()
                .success(true)
                .message("Customer's loan applications retrieved successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }

    // 5. Update Loan Status (PATCH /api/v1/loan-applications/{id}/status)
    @PatchMapping("/loan-applications/{id}/status")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> updateLoanStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateLoanStatusRequest request) {
        
        LoanApplicationResponse response = loanApplicationService.updateLoanStatus(id, request);
        
        ApiResponse<LoanApplicationResponse> apiResponse = ApiResponse.<LoanApplicationResponse>builder()
                .success(true)
                .message("Loan application status updated successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }
}





