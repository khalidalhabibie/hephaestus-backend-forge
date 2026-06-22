package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.CreateLoanApplicationRequest;
import com.example.demoSpringbootDatabase.dto.LoanApplicationResponse;
import com.example.demoSpringbootDatabase.dto.UpdateLoanStatusRequest;
import com.example.demoSpringbootDatabase.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LoanApplicationController {
    private final LoanApplicationService loanService;
    public LoanApplicationController(LoanApplicationService loanService) { this.loanService = loanService; }

    @PostMapping("/loan-applications")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> create(@Valid @RequestBody CreateLoanApplicationRequest request) {
        LoanApplicationResponse res = loanService.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Loan application submitted successfully", res));
    }

    @GetMapping("/loan-applications/{id}")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Loan application retrieved successfully", loanService.getById(id)));
    }

    @GetMapping("/loan-applications")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getAll(@RequestParam(value = "status", required = false) String status) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Loan applications retrieved successfully", loanService.getAll(status)));
    }

    @GetMapping("/customers/{customerId}/loan-applications")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer loan applications retrieved successfully", loanService.getByCustomerId(customerId)));
    }

    @PatchMapping("/loan-applications/{id}/status")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateLoanStatusRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Loan application status updated successfully", loanService.updateStatus(id, request.getStatus())));
    }
}
