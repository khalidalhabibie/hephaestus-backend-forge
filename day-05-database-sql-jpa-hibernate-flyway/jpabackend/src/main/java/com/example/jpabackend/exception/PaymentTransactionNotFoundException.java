package com.example.jpabackend.exception;

public class PaymentTransactionNotFoundException extends RuntimeException {
    public PaymentTransactionNotFoundException(String message) {
        super(message);
    }
}