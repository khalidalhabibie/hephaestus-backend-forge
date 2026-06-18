package com.example.main.exceptions;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(String message) {
        super(message);
    }
}