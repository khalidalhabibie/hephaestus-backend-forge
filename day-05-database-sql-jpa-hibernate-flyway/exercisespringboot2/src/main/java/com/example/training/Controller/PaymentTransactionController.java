// Handle endpoint payment: POST create, GET by schedule ID.

package com.example.training.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.DTO.ApiResponse;
import com.example.training.DTO.CreatePaymentTransactionRequest;
import com.example.training.DTO.PaymentTransactionResponse;
import com.example.training.Service.PaymentTransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentTransactionController {

        private final PaymentTransactionService paymentTransactionService;

        @PostMapping("/payment-transactions")
        public ResponseEntity<ApiResponse<PaymentTransactionResponse>> create(
                        @Valid @RequestBody CreatePaymentTransactionRequest request) {
                PaymentTransactionResponse dto = paymentTransactionService.create(request);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ApiResponse.success("Payment transaction created successfully", dto));
        }

        @GetMapping("/repayment-schedules/{repaymentScheduleId}/payment-transactions")
        public ResponseEntity<ApiResponse<List<PaymentTransactionResponse>>> getByRepaymentScheduleId(
                        @PathVariable Long repaymentScheduleId) {
                return ResponseEntity.ok(ApiResponse.success("Payment transactions retrieved successfully",
                                paymentTransactionService.getByRepaymentScheduleId(repaymentScheduleId)));
        }
}