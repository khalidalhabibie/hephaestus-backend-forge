package com.example.spring_boot_database.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.service.PaymentTransactionService;
import com.example.spring_boot_database.service.RepaymentScheduleSevice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/repayment-schedules")
@RequiredArgsConstructor

public class RepaymentScheduleController {
    private final RepaymentScheduleSevice repaymentScheduleSevice;
    private final PaymentTransactionService paymentTransactionService;

    @GetMapping("/{id}")
    public ApiResponse<RepaymentScheduleResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(repaymentScheduleSevice.findById(id), "Customer Found");
    }

    @GetMapping("/{repayment_schedule_id}/payment-transactions")
    public ApiResponse<List<PaymentTransactionResponse>> getPaymentTransactionByRepaymentId(@PathVariable Long repayment_schedule_id) {
        return ApiResponse.success(repaymentScheduleSevice.findPaymentTransactionByRepaymentId(repayment_schedule_id), "Customer Found");
    }
}
