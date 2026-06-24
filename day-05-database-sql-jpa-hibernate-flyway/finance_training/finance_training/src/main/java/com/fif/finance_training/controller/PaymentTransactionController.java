package com.fif.finance_training.controller;

import com.fif.finance_training.dto.ApiResponse;
import com.fif.finance_training.dto.CreatePaymentTransactionRequest;
import com.fif.finance_training.dto.PaymentTransactionResponse;
import com.fif.finance_training.service.PaymentTransactionService;
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
    public ResponseEntity<ApiResponse<PaymentTransactionResponse>> createPayment(
            @Valid @RequestBody CreatePaymentTransactionRequest request) {
        
        PaymentTransactionResponse response = paymentTransactionService.createPayment(request);
        
        ApiResponse<PaymentTransactionResponse> apiResponse = ApiResponse.<PaymentTransactionResponse>builder()
                .success(true)
                .message("Payment processed successfully")
                .data(response)
                .build();
                
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/repayment-schedules/{repayment_schedule_id}/payment-transactions")
    public ResponseEntity<ApiResponse<List<PaymentTransactionResponse>>> getPaymentsByScheduleId(
            @PathVariable("repayment_schedule_id") Long scheduleId) {
        
        List<PaymentTransactionResponse> response = paymentTransactionService.getPaymentsByScheduleId(scheduleId);
        
        ApiResponse<List<PaymentTransactionResponse>> apiResponse = ApiResponse.<List<PaymentTransactionResponse>>builder()
                .success(true)
                .message("Payment transactions retrieved successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }
}