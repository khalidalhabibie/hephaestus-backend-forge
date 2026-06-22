package com.example.training.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.dto.ApiResponse;
import com.example.training.dto.RepaymentScheduleResponse;
import com.example.training.service.RepaymentScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;

    // --- read schedules by loan ---

    @GetMapping("/loan-applications/{loanApplicationId}/repayment-schedules")
    public ApiResponse<List<RepaymentScheduleResponse>> getByLoanApplicationId(
            @PathVariable UUID loanApplicationId) {
        return ApiResponse.success(repaymentScheduleService.findByLoanApplicationId(loanApplicationId));
    }

    // --- single schedule ---

    @GetMapping("/repayment-schedules/{id}")
    public ApiResponse<RepaymentScheduleResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(repaymentScheduleService.findById(id));
    }
}
