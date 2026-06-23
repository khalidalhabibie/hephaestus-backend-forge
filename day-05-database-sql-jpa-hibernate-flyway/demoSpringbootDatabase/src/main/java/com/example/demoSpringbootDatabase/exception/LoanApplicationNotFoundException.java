package com.example.demoSpringbootDatabase.exception;

public class LoanApplicationNotFoundException extends ResourceNotFoundException {
    public LoanApplicationNotFoundException(Long id) { super("Loan application not found with id: " + id); }
}
