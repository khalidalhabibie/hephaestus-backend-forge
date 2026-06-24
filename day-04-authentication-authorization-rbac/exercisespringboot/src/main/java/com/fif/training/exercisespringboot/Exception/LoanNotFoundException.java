package com.fif.training.exercisespringboot.Exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long id) {
        super("Loan data not found with id: " + id);
    }
}
