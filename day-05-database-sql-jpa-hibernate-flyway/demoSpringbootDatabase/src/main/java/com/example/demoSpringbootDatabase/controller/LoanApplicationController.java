package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.CreateLoanApplicationRequest;
import com.example.demoSpringbootDatabase.dto.LoanApplicationResponse;
import com.example.demoSpringbootDatabase.dto.UpdateLoanStatusRequest;
import com.example.demoSpringbootDatabase.dto.LoanStatusSummaryDto; // Import DTO projection report
import com.example.demoSpringbootDatabase.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LoanApplicationController {
    
    private final LoanApplicationService loanService;
    
    public LoanApplicationController(LoanApplicationService loanService) { 
        this.loanService = loanService; 
    }

    @PostMapping("/loan-applications")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> create(@Valid @RequestBody CreateLoanApplicationRequest request) {
        LoanApplicationResponse res = loanService.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Loan application submitted successfully", res));
    }

    @GetMapping("/loan-applications/{id}")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Loan application retrieved successfully", loanService.getById(id)));
    }

    /**
     * Mengubah Get All menjadi Pagination, Filter Status, & Filter Rentang Tanggal Pengajuan
     */
    @GetMapping("/loan-applications")
    public ResponseEntity<ApiResponse<Page<LoanApplicationResponse>>> getAll(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        // Atur parameter rentang waktu default jika tidak diisi dari request
        LocalDateTime start = (startDate != null) ? startDate : LocalDateTime.now().minusYears(3);
        LocalDateTime end = (endDate != null) ? endDate : LocalDateTime.now();
        
        // Membuat object pageable dengan sorting berdasarkan data terbaru (ID Descending)
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        
        Page<LoanApplicationResponse> res = loanService.getLoansWithPagination(status, start, end, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Loan applications retrieved successfully", res));
    }

    @GetMapping("/customers/{customerId}/loan-applications")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer loan applications retrieved successfully", loanService.getByCustomerId(customerId)));
    }

    @PatchMapping("/loan-applications/{id}/status")
    public ResponseEntity<ApiResponse<LoanApplicationResponse>> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateLoanStatusRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Loan application status updated successfully", loanService.updateStatus(id, request.getStatus())));
    }

    /**
     * Endpoint Opsional Tambahan: Summary total loan grouped by status (Reporting)
     */
    @GetMapping("/loan-applications/summary")
    public ResponseEntity<ApiResponse<List<LoanStatusSummaryDto>>> getLoanSummary() {
        List<LoanStatusSummaryDto> summary = loanService.getStatusSummary();
        return ResponseEntity.ok(new ApiResponse<>(true, "Loan status summary retrieved successfully", summary));
    }
}