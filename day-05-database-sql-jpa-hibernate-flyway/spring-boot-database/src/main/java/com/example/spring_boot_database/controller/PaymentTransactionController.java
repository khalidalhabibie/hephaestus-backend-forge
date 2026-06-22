package com.example.spring_boot_database.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.CreatePaymentTransactionRequest;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.service.PaymentTransactionService;
import com.example.spring_boot_database.service.RepaymentScheduleSevice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payment-transactions")
@RequiredArgsConstructor

public class PaymentTransactionController {
    private final RepaymentScheduleSevice repaymentScheduleSevice;
    private final PaymentTransactionService paymentTransactionService;

    @PostMapping
    public ApiResponse<PaymentTransactionResponse> create(@Valid @RequestBody CreatePaymentTransactionRequest request) {
        return ApiResponse.success(paymentTransactionService.createPaymentTransaction(request), "Loan application retrieved successfully ");
    }
}
