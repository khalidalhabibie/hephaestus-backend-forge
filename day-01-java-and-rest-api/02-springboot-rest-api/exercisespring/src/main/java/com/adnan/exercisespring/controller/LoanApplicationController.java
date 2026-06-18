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
  public ResponseEntity<LoanApplicationResponse> create(
      @RequestBody CreateLoanApplicationRequest request) {

    return ResponseEntity
        .status(201)
        .body(service.createLoanApplication(request));
  }

  @GetMapping
  public ResponseEntity<List<LoanApplicationResponse>> getAll() {
    return ResponseEntity.ok(service.getAllLoanApplications());
  }

  @GetMapping("/{id}")
  public ResponseEntity<LoanApplicationResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getLoanApplicationById(id));
  }

  @PatchMapping("/{id}/approve")
  public ResponseEntity<LoanApplicationResponse> approve(@PathVariable Long id) {
    return ResponseEntity.ok(service.approve(id));
  }

  @PatchMapping("/{id}/reject")
  public ResponseEntity<LoanApplicationResponse> reject(@PathVariable Long id) {
    return ResponseEntity.ok(service.reject(id));
  }
}
