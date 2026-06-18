package com.example.main.controllers;

import com.example.main.dto.LoanApplicationRequest;
import com.example.main.dto.LoanApplicationResponse;
import com.example.main.security.RequiresRoles;
import com.example.main.security.UserRole;
import com.example.main.services.LoanApplicationService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF})
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(
            @Valid @RequestBody LoanApplicationRequest request) {
        
        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF, UserRole.APPROVER})
    public ResponseEntity<List<LoanApplicationResponse>> getAllLoanApplications() {
        List<LoanApplicationResponse> response = loanApplicationService.getAllLoanApplications();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF, UserRole.APPROVER})
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable Long id) {
        LoanApplicationResponse response = loanApplicationService.getLoanApplicationById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/approve")
    @RequiresRoles({UserRole.ADMIN, UserRole.APPROVER})
    public ResponseEntity<LoanApplicationResponse> approveLoanApplication(@PathVariable Long id) {
        
        LoanApplicationResponse response = loanApplicationService.approveLoanApplication(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reject")
    @RequiresRoles({UserRole.ADMIN, UserRole.APPROVER})
    public ResponseEntity<LoanApplicationResponse> rejectLoanApplication(@PathVariable Long id) {
        
        LoanApplicationResponse response = loanApplicationService.rejectLoanApplication(id);
        return ResponseEntity.ok(response);
    }
}