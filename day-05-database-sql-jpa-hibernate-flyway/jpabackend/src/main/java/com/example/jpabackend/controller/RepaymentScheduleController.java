package com.example.jpabackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.jpabackend.dto.common.ApiResponse;
import com.example.jpabackend.dto.response.RepaymentScheduleResponse;
import com.example.jpabackend.service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RepaymentScheduleController {
    private final RepaymentScheduleService service;

    @GetMapping("/repayment-schedules/{id}")
    public ApiResponse<RepaymentScheduleResponse> get(@PathVariable Long id) {
        return ApiResponse.success("OK", service.getScheduleById(id));
    }

    @GetMapping("/loan-applications/{id}/repayment-schedules")
    public ApiResponse<List<RepaymentScheduleResponse>> byLoan(@PathVariable Long id) {
        return ApiResponse.success("OK", service.getSchedulesByLoanApplication(id));
    }
}