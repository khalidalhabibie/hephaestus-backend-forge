package com.example.jpabackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.jpabackend.dto.common.ApiResponse;
import com.example.jpabackend.dto.request.CreatePaymentTransactionRequest;
import com.example.jpabackend.dto.response.PaymentTransactionResponse;
import com.example.jpabackend.service.PaymentTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentTransactionController {
    private final PaymentTransactionService service;
    @PostMapping("/payment-transactions")
    public ApiResponse<PaymentTransactionResponse> create(
        @RequestBody @Valid CreatePaymentTransactionRequest request) {
        return ApiResponse.success("Created", service.createPaymentTransaction(request));
    }

    @GetMapping("/repayment-schedules/{id}/payment-transactions")
    public ApiResponse<List<PaymentTransactionResponse>> get(@PathVariable Long id) {
        return ApiResponse.success("OK", service.getPaymentsBySchedule(id));
    }
}