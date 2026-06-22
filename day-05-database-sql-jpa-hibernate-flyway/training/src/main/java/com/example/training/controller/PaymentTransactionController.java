package com.example.training.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.dto.ApiResponse;
import com.example.training.dto.CreatePaymentTransactionRequest;
import com.example.training.dto.PaymentTransactionResponse;
import com.example.training.service.PaymentTransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    @PostMapping("/payment-transactions")
    public ApiResponse<PaymentTransactionResponse> create(
            @Valid @RequestBody CreatePaymentTransactionRequest request) {
        return ApiResponse.success(paymentTransactionService.create(request));
    }

    @GetMapping("/repayment-schedules/{repaymentScheduleId}/payment-transactions")
    public ApiResponse<List<PaymentTransactionResponse>> getByRepaymentScheduleId(
            @PathVariable UUID repaymentScheduleId) {
        return ApiResponse.success(
                paymentTransactionService.findByRepaymentScheduleId(repaymentScheduleId));
    }
}
