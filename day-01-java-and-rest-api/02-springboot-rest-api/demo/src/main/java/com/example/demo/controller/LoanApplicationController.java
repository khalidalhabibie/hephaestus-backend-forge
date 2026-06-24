package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoanApplicationRequest;
import com.example.demo.dto.LoanApplicationResponse;
import com.example.demo.service.LoanApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @PostMapping()
    public ResponseEntity<LoanApplicationResponse> create(
            @Valid @RequestBody LoanApplicationRequest request) {

        return ResponseEntity.ok(loanApplicationService.createLoanApplication(request));
    }

    @GetMapping
    public ResponseEntity<List<LoanApplicationResponse>> getLoanApplications(@RequestParam(required = false) Long customerId, @RequestParam(required = false) String status) {
        return ResponseEntity.ok(loanApplicationService.getLoanApplications(customerId, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(loanApplicationService.getLoanApplicationById(id));
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<LoanApplicationResponse> setLoanApplicationStatus(@PathVariable Long id, @PathVariable String status) {
        return ResponseEntity.ok(loanApplicationService.patchStatusLoanApplication(id, status));
    }
    
}
