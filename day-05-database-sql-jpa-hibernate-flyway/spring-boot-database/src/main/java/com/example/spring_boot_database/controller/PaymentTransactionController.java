package com.example.spring_boot_database.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.CreatePaymentTransactionRequest;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.service.PaymentTransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payment-transactions")
@RequiredArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService paymentService;

    @PostMapping
    public ApiResponse<PaymentTransactionResponse> create(
            @Valid @RequestBody CreatePaymentTransactionRequest request) {

        return ApiResponse.success(
                paymentService.create(request),
                "Payment transaction created successfully"
        );
    }
}
