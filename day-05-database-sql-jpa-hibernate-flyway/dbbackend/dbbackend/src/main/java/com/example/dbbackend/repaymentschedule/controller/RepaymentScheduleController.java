package com.example.dbbackend.repaymentschedule.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dbbackend.common.dto.ApiResponse;
import com.example.dbbackend.repaymentschedule.dto.RepaymentScheduleResponse;
import com.example.dbbackend.repaymentschedule.service.RepaymentScheduleService;

@RestController
@RequestMapping("/api/v1")
public class RepaymentScheduleController {

    private final RepaymentScheduleService service;

    public RepaymentScheduleController(RepaymentScheduleService service) {
        this.service = service;
    }

    @GetMapping("/loan-applications/{loanId}/repayment-schedules")
    public ApiResponse<List<RepaymentScheduleResponse>> getByLoan(
            @PathVariable Long loanId) {

        return ApiResponse.success(
                "Repayment schedules retrieved successfully",
                service.getByLoanId(loanId));
    }

    @GetMapping("/repayment-schedules/{id}")
    public ApiResponse<RepaymentScheduleResponse> getById(
            @PathVariable Long id) {

        return ApiResponse.success(
                "Repayment schedule retrieved successfully",
                service.getById(id));
    }
}
