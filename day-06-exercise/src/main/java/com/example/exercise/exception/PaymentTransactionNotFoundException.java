package com.example.exercise.exception;

public class PaymentTransactionNotFoundException extends RuntimeException {
    public PaymentTransactionNotFoundException(Long id) {
        super("Payment transaction not found with repayment schedule id: " + id);
    }
}
