package com.example.jpabackend.controller;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.service.PaymentTransactionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Payment Transaction API", description = "API untuk mengelola payment transaction")
@RestController
@RequestMapping("/api/v1")
public class PaymentTransactionController {

    private final PaymentTransactionService service;

    public PaymentTransactionController(PaymentTransactionService service) {
        this.service = service;
    }

    // CREATE PAYMENT
    @PostMapping("/payment-transactions")
    @Operation(summary = "Create payment transaction")
    public ResponseEntity<ApiResponse<PaymentTransactionResponse>> create(
            @Valid @RequestBody CreatePaymentTransactionRequest request) {

        PaymentTransactionResponse response = service.createPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Payment created successfully", response));
    }

    // GET BY REPAYMENT SCHEDULE ID
    @GetMapping("/repayment-schedules/{scheduleId}/payment-transactions")
    @Operation(summary = "Get payment transactions by repayment schedule ID")
    public ResponseEntity<ApiResponse<List<PaymentTransactionResponse>>> getByScheduleId(
            @PathVariable Long scheduleId) {

        List<PaymentTransactionResponse> response = service.getByScheduleId(scheduleId);

        return ResponseEntity.ok(
                ApiResponse.success("Payment transactions retrieved successfully", response));
    }
}