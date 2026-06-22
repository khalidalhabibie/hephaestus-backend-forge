package com.example.jpabackend.controller;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.service.LoanApplicationService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Tag(name = "Loan Application API", description = "API untuk mengelola loan application")
@RestController
@RequestMapping("/api/v1")
public class LoanApplicationController {

    private final LoanApplicationService service;

    public LoanApplicationController(LoanApplicationService service) {
        this.service = service;
    }

    // CREATE LOAN
    @PostMapping("/loan-applications")
    @Operation(summary = "Create loan application")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> create(
            @Valid @RequestBody CreateLoanApplicationRequest request) {

        LoanApplicationResponse response = service.createLoan(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Loan created successfully", response));
    }

    // GET BY ID
    @GetMapping("/loan-applications/{id}")
    @Operation(summary = "Get loan application by ID (with customer)")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> getById(
            @PathVariable Long id) {

        LoanApplicationResponse response = service.getById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Loan retrieved successfully", response));
    }

    // GET ALL
    @GetMapping("/loan-applications")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success("All loans retrieved",
                        service.getAll()));
    }

    // GET BY STATUS
    @GetMapping("/loan-applications/filter")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getByStatus(
            @RequestParam String status) {

        return ResponseEntity.ok(
                ApiResponse.success("Filtered loans",
                        service.getByStatus(status)));
    }

    // GET PAGINATION
    @GetMapping("/loan-applications/page")
    public ResponseEntity<ApiResponse<Page<LoanApplicationResponse>>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                ApiResponse.success("Loans retrieved",
                        service.getAll(page, size)));
    }

    // GET BY DATE
    @GetMapping("/loan-applications/filter-date")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> filterByDate(
            @RequestParam ZonedDateTime start,
            @RequestParam ZonedDateTime end) {

        return ResponseEntity.ok(
                ApiResponse.success("Filtered loans",
                        service.filterByDate(start, end)));
    }

    // GET LOAN BY CUSTOMER ID
    @GetMapping("/customers/{customerId}/loan-applications")
    @Operation(summary = "Get loan applications by customer ID")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getByCustomerId(
            @PathVariable Long customerId) {

        List<LoanApplicationResponse> response = service.getByCustomerId(customerId);

        return ResponseEntity.ok(
                ApiResponse.success("Customer loans retrieved successfully", response));
    }

    // UPDATE STATUS
    @PatchMapping("/loan-applications/{id}/status")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");

        LoanApplicationResponse response = service.updateStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success("Status updated", response));
    }

    // GET SUMMARY BY STATUS
    @GetMapping("/loan-applications/summary")
    public ResponseEntity<ApiResponse<List<LoanSummaryDTO>>> summary() {

        return ResponseEntity.ok(
                ApiResponse.success("Loan summary retrieved",
                        service.getLoanSummaryDTO()));
    }

    // // GET ALL + FILTER STATUS
    // @GetMapping("/loan-applications")
    // @Operation(summary = "Get all loan applications / filter by status")
    // public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getAll(
    // @RequestParam(value = "status", required = false) String status) {

    // List<LoanApplicationResponse> response;

    // if (status != null) {
    // response = service.getByStatus(status);
    // } else {
    // response = service.getAll();
    // }

    // return ResponseEntity.ok(
    // ApiResponse.success("Loans retrieved successfully", response));
    // }
}