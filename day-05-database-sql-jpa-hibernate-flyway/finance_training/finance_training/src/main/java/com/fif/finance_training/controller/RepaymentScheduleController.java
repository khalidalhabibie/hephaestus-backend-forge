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

    @GetMapping("/loan-applications/{loan_id}/repayment-schedules")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponse>>> getSchedulesByLoanId(
            @PathVariable("loan_id") Long loanId,
            @RequestParam(value = "status", required = false) String status) {

        List<RepaymentScheduleResponse> response;
        if (status != null && !status.trim().isEmpty()) {
            response = repaymentScheduleService.getSchedulesByLoanIdAndStatus(loanId, status);
        } else {
            response = repaymentScheduleService.getSchedulesByLoanId(loanId);
        }

        ApiResponse<List<RepaymentScheduleResponse>> apiResponse = ApiResponse.<List<RepaymentScheduleResponse>>builder()
                .success(true)
                .message("Repayment schedules retrieved successfully")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

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