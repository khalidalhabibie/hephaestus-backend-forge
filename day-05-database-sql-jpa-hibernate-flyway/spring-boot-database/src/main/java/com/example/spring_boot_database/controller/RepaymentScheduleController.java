package com.example.spring_boot_database.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.service.RepaymentScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repayment-schedules")
@RequiredArgsConstructor
public class RepaymentScheduleController {

    private final RepaymentScheduleService scheduleService;

    @GetMapping("/{id}")
    public ApiResponse<RepaymentScheduleResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(
                scheduleService.findById(id),
                "Repayment schedule retrieved successfully"
        );
    }

    @GetMapping("/{id}/payment-transactions")
    public ApiResponse<List<PaymentTransactionResponse>> getPayments(
            @PathVariable Long id) {

        return ApiResponse.success(
                scheduleService.findPaymentTransactionByRepaymentId(id),
                "Payment transactions retrieved successfully"
        );
    }

    @GetMapping
    public ApiResponse<List<RepaymentScheduleResponse>> getAll(
            @RequestParam(required = false) String status) {

        return ApiResponse.success(
                scheduleService.findAll(status),
                "Repayment schedules retrieved"
        );
    }
}