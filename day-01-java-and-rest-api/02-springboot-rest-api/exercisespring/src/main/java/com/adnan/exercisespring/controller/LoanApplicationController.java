package com.adnan.exercisespring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.adnan.exercisespring.dto.CreateLoanApplicationRequest;
import com.adnan.exercisespring.dto.LoanApplicationResponse;
import com.adnan.exercisespring.service.LoanApplicationService;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {
  private final LoanApplicationService service;

  @PostMapping
  public ResponseEntity<LoanApplicationResponse> createLoanApplication(
      @RequestBody CreateLoanApplicationRequest request) {
    return ResponseEntity
        .status(201)
        .body(service.createLoanApplication(request));
  }

  @GetMapping
  public ResponseEntity<List<LoanApplicationResponse>> getAllLoanApplications(
      @RequestParam(required = false) String status,
      @RequestParam(name = "customer_id", required = false) Long customerId) {
    return ResponseEntity.ok(service.getAllLoanApplications(status, customerId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getLoanApplicationById(id));
  }

  @PatchMapping("/{id}/approve")
  public ResponseEntity<LoanApplicationResponse> approveLoanApplication(@PathVariable Long id) {
    return ResponseEntity.ok(service.approveLoanApplication(id));
  }

  @PatchMapping("/{id}/reject")
  public ResponseEntity<LoanApplicationResponse> rejectLoanApplication(@PathVariable Long id) {
    return ResponseEntity.ok(service.rejectLoanApplication(id));
  }

  @PatchMapping("/{id}/cancel")
  public ResponseEntity<LoanApplicationResponse> cancelLoanApplication(@PathVariable Long id) {
    return ResponseEntity.ok(service.cancelLoanApplication(id));
  }
}
