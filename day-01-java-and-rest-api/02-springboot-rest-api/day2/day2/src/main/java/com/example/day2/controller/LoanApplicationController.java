package com.example.day2.controller;

import com.example.day2.dto.CreateLoanApplicationRequest;
import com.example.day2.dto.LoanApplicationResponse;
import com.example.day2.dto.WebResponse;
import com.example.day2.enum_auth.LoanStatus;
import com.example.day2.service.LoanApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
@Tag(name = "Loan Application", description = "API untuk pengajuan dan pengelolaan pinjaman")
@SecurityRequirement(name = "bearerAuth")
public class LoanApplicationController {
    
    private final LoanApplicationService loanApplicationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    @Operation(
        summary = "Buat pengajuan pinjaman baru",
        description = "Hanya STAFF dan ADMIN yang dapat membuat pengajuan. Status awal: SUBMITTED."
    )
    public ResponseEntity<WebResponse<LoanApplicationResponse>> create(
            @Valid @RequestBody CreateLoanApplicationRequest request) {
        LoanApplicationResponse data = loanApplicationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            createWebResponse(HttpStatus.CREATED, "Loan application created successfully", data));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'APPROVAL', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Ambil semua pengajuan pinjaman (dengan filter opsional)")
    public ResponseEntity<WebResponse<List<LoanApplicationResponse>>> findAll(
            @RequestParam(value = "status", required = false) LoanStatus status,
            @RequestParam(value = "customer_id", required = false) Long customerId) {

        List<LoanApplicationResponse> data;
        
        if (status != null && customerId != null) {
            data = loanApplicationService.findByStatusAndCustomerId(status, customerId);
        } else if (status != null) {
            data = loanApplicationService.findByStatus(status);
        } else if (customerId != null) {
            data = loanApplicationService.findByCustomerId(customerId);
        } else {
            data = loanApplicationService.findAll();
        }

        return ResponseEntity.ok(
            createWebResponse(HttpStatus.OK, "Successfully fetched loan applications", data));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'APPROVER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Ambil pengajuan pinjaman berdasarkan ID")
    public ResponseEntity<WebResponse<LoanApplicationResponse>> findById(@PathVariable Long id) {
        LoanApplicationResponse data = loanApplicationService.findById(id);
        return ResponseEntity.ok(
            createWebResponse(HttpStatus.OK, "Successfully fetched loan application", data));
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('APPROVER', 'ADMIN', 'MANAGER')")
    @Operation(
        summary = "Approve pengajuan pinjaman",
        description = "APPROVER dan ADMIN dapat approve semua. MANAGER hanya boleh approve di atas 10.000.000."
    )
    public ResponseEntity<WebResponse<LoanApplicationResponse>> approve(@PathVariable Long id) {
        LoanApplicationResponse data = loanApplicationService.approve(id);
        return ResponseEntity.ok(
            createWebResponse(HttpStatus.OK, "Loan application approved", data));
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('APPROVER', 'ADMIN', 'MANAGER')")
    @Operation(
        summary = "Reject pengajuan pinjaman",
        description = "Hanya APPROVER, ADMIN, dan MANAGER yang dapat menolak pengajuan. Status harus SUBMITTED."
    )
    public ResponseEntity<WebResponse<LoanApplicationResponse>> reject(@PathVariable Long id) {
        LoanApplicationResponse data = loanApplicationService.reject(id);
        return ResponseEntity.ok(
            createWebResponse(HttpStatus.OK, "Loan application rejected", data));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    @Operation(
        summary = "Cancel pengajuan pinjaman",
        description = "Hanya STAFF dan ADMIN yang dapat cancel pengajuan. Status harus SUBMITTED."
    )
    public ResponseEntity<WebResponse<LoanApplicationResponse>> cancel(@PathVariable Long id) {
        LoanApplicationResponse data = loanApplicationService.cancel(id);
        return ResponseEntity.ok(
            createWebResponse(HttpStatus.OK, "Loan application cancelled", data));
    }

    private <T> WebResponse<T> createWebResponse(HttpStatus status, String message, T data) {
        return WebResponse.<T>builder()
                .code(String.valueOf(status.value()))
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}