package com.adnan.loanappspringsql.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adnan.loanappspringsql.dto.CreatePaymentTransactionRequest;
import com.adnan.loanappspringsql.dto.PaymentTransactionResponse;
import com.adnan.loanappspringsql.dto.api.ApiResponse;
import com.adnan.loanappspringsql.service.PaymentTransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payment-transactions")
@RequiredArgsConstructor
public class PaymentTransactionController {
  private final PaymentTransactionService paymentTransactionService;

  @PostMapping
  public ResponseEntity<ApiResponse<PaymentTransactionResponse>> create(
      @Valid @RequestBody CreatePaymentTransactionRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(
            "Payment transaction created successfully",
            paymentTransactionService.create(request)));
  }
}