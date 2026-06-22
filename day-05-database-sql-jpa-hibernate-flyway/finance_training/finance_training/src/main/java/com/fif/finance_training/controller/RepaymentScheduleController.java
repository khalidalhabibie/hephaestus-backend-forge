package com.fif.finance_training.controller;

import com.fif.finance_training.dto.ApiResponse;
import com.fif.finance_training.dto.RepaymentScheduleResponse;
import com.fif.finance_training.service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RepaymentScheduleController {
    private final RepaymentScheduleService repaymentScheduleService;

    // 1. Get Repayment Schedules by Loan ID (GET /api/v1/loan-applications/{loan_id}/repayment-schedules)
    @GetMapping("/loan-applications/{loan_id}/repayment-schedules")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponse>>> getSchedulesByLoanId(
            @PathVariable("loan_id") Long loanId) {

        List<RepaymentScheduleResponse> response = repaymentScheduleService.getSchedulesByLoanId(loanId);

        ApiResponse<List<RepaymentScheduleResponse>> apiResponse = ApiResponse.<List<RepaymentScheduleResponse>>builder()
                .success(true)
                .message("Repayment schedules retrieved successfully")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // 2. Get Single Repayment Schedule by ID (GET /api/v1/repayment-schedules/{id})
    @GetMapping("/repayment-schedules/{id}")
    public ResponseEntity<ApiResponse<RepaymentScheduleResponse>> getScheduleById(
            @PathVariable("id") Long id) {

        RepaymentScheduleResponse response = repaymentScheduleService.getScheduleById(id);

        ApiResponse<RepaymentScheduleResponse> apiResponse = ApiResponse.<RepaymentScheduleResponse>builder()
                .success(true)
                .message("Repayment schedule detail retrieved successfully")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
