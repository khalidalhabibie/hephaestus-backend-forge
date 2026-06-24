package com.fif.loanapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fif.loanapplication.dto.common.ApiResponseDto;
import com.fif.loanapplication.dto.payment.CreatePaymentTransactionRequest;
import com.fif.loanapplication.dto.payment.PaymentTransactionResponse;
import com.fif.loanapplication.service.PaymentTransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payment-transactions")
@RequiredArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<PaymentTransactionResponse> createPaymentTransaction(
            @Valid @RequestBody CreatePaymentTransactionRequest request) {
        PaymentTransactionResponse response = paymentTransactionService.createPayments(request);
        return new ApiResponseDto<>(true, "Payments Success!", response);
    }

}
