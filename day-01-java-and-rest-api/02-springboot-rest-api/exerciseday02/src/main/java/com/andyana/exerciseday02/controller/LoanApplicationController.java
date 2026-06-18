package com.andyana.exerciseday02.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andyana.exerciseday02.dto.CreateLoanApplicationRequest;
import com.andyana.exerciseday02.dto.LoanApplicationResponse;
import com.andyana.exerciseday02.service.LoanApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(@RequestBody @Valid CreateLoanApplicationRequest request) {
        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanApplicationResponse>> getAllLoanApplications() {
        List<LoanApplicationResponse> responses = loanApplicationService.getAllLoanApplications();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable Long id) {
        LoanApplicationResponse response = loanApplicationService.getLoanApplicationById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<LoanApplicationResponse> approveLoanApplication(@PathVariable Long id) {
        LoanApplicationResponse response = loanApplicationService.approveLoanApplication(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<LoanApplicationResponse> rejectLoanApplication(@PathVariable Long id) {
        LoanApplicationResponse response = loanApplicationService.rejectLoanApplication(id);
        return ResponseEntity.ok(response);
    }
}
