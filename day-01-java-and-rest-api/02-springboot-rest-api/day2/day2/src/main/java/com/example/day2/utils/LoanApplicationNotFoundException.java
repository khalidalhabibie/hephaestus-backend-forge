package com.example.day2.utils;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(Long id) {
        super("Loan application with ID " + id + " not found");
    }
}
