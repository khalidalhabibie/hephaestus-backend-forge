package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.RepaymentScheduleResponse;
import com.example.demoSpringbootDatabase.service.RepaymentScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RepaymentScheduleController {
    
    // Kita gunakan nama scheduleService secara konsisten
    private final RepaymentScheduleService scheduleService;
    
    public RepaymentScheduleController(RepaymentScheduleService scheduleService) { 
        this.scheduleService = scheduleService; 
    }

    /**
     * Gabungan Method Pencarian: Mendukung GET biasa ATAU menggunakan filter status PAID/UNPAID
     * URL Contoh: 
     * - GET /api/v1/loan-applications/1/repayment-schedules
     * - GET /api/v1/loan-applications/1/repayment-schedules?status=UNPAID
     */
    @GetMapping("/loan-applications/{loanId}/repayment-schedules")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponse>>> getByLoan(
            @PathVariable Long loanId,
            @RequestParam(value = "status", required = false) String status) {
        
        List<RepaymentScheduleResponse> res = scheduleService.getByLoanIdAndStatus(loanId, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Repayment schedules retrieved successfully", res));
    }

    @GetMapping("/repayment-schedules/{id}")
    public ResponseEntity<ApiResponse<RepaymentScheduleResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Repayment schedule retrieved successfully", scheduleService.getById(id)));
    }
}