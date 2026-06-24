package com.example.exercise.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.exercise.dto.CreateCustomerRequest;
import com.example.exercise.dto.CreatePaymentTransactionRequest;
import com.example.exercise.dto.CustomerResponse;
import com.example.exercise.dto.PaymentTransactionResponse;
import com.example.exercise.enums.Role;
import com.example.exercise.helper.ValidateTokenRoleHelper;
import com.example.exercise.repository.PaymentTransactionRepository;
import com.example.exercise.service.AuthService;
import com.example.exercise.service.PaymentTransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/payment-transactions")
public class PaymentTransactionController {

    PaymentTransactionRepository paymentTransactionRepository;
    PaymentTransactionService paymentTransactionService;
    AuthService authService;

    public PaymentTransactionController(PaymentTransactionRepository paymentTransactionRepository,
        PaymentTransactionService paymentTransactionService, AuthService authService) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.paymentTransactionService = paymentTransactionService;
        this.authService = authService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new payment transaction.", description = "Creates a new payment transaction with details as shown.")
    @ApiResponse(responseCode = "201", description = "New customer created.")
    @ApiResponse(responseCode = "400", description = "Invalid user input.")
    public ResponseEntity<PaymentTransactionResponse> createPaymentTransaction(
            @Valid @RequestBody CreatePaymentTransactionRequest request, 
            HttpServletRequest requestContext) {
        
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF);

        PaymentTransactionResponse response = paymentTransactionService.createPaymentTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
