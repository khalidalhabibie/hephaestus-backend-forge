package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.CreatePaymentTransactionRequest;
import com.example.demoSpringbootDatabase.dto.PaymentTransactionResponse;
import com.example.demoSpringbootDatabase.service.PaymentTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PaymentTransactionController {
    private final PaymentTransactionService transactionService;
    public PaymentTransactionController(PaymentTransactionService transactionService) { this.transactionService = transactionService; }

    @PostMapping("/payment-transactions")
    public ResponseEntity<ApiResponse<PaymentTransactionResponse>> create(@Valid @RequestBody CreatePaymentTransactionRequest request) {
        PaymentTransactionResponse res = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Payment recorded successfully", res));
    }

    @GetMapping("/repayment-schedules/{repaymentScheduleId}/payment-transactions")
    public ResponseEntity<ApiResponse<List<PaymentTransactionResponse>>> getBySchedule(@PathVariable Long repaymentScheduleId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment transactions retrieved successfully", transactionService.getByScheduleId(repaymentScheduleId)));
    }
}
