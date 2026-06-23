package com.example.dbbackend.paymenttransaction.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.dbbackend.common.dto.ApiResponse;
import com.example.dbbackend.paymenttransaction.dto.CreatePaymentTransactionRequest;
import com.example.dbbackend.paymenttransaction.dto.PaymentTransactionResponse;
import com.example.dbbackend.paymenttransaction.service.PaymentTransactionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class PaymentTransactionController {

    private final PaymentTransactionService service;

    public PaymentTransactionController(PaymentTransactionService service) {
        this.service = service;
    }

    // ✅ CREATE PAYMENT
    @PostMapping("/payment-transactions")
    public ApiResponse<PaymentTransactionResponse> create(
            @Valid @RequestBody CreatePaymentTransactionRequest request) {

        return ApiResponse.success(
                "Payment created successfully",
                service.createPayment(request));
    }

    @GetMapping("/repayment-schedules/{scheduleId}/payment-transactions")
    public ApiResponse<List<PaymentTransactionResponse>> getBySchedule(
            @PathVariable Long scheduleId) {

        return ApiResponse.success(
                "Payment transactions retrieved successfully",
                service.getBySchedule(scheduleId));
    }
}
