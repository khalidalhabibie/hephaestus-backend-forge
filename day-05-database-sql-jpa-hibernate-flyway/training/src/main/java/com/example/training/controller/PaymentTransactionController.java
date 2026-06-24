package com.example.training.controller;

import com.example.training.dto.*;
import com.example.training.service.PaymentTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    @PostMapping("/payment-transactions")
    public ResponseEntity<ApiResponse<PaymentTransactionResponse>> create(
            @Valid @RequestBody CreatePaymentTransactionRequest request) {
        PaymentTransactionResponse data = paymentTransactionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Payment transaction created successfully", data));
    }

    @GetMapping("/repayment-schedules/{repaymentScheduleId}/payment-transactions")
    public ResponseEntity<ApiResponse<List<PaymentTransactionResponse>>> getByRepaymentScheduleId(
            @PathVariable Long repaymentScheduleId) {
        List<PaymentTransactionResponse> data = paymentTransactionService.findByRepaymentScheduleId(repaymentScheduleId);
        return ResponseEntity.ok(ApiResponse.success("Payment transactions retrieved successfully", data));
    }
}
