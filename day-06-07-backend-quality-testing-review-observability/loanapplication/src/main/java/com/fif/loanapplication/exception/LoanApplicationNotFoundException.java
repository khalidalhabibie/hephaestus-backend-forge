package com.fif.loanapplication.exception;

import java.util.UUID;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(UUID uid) {
        super("Loan data not found with unique identifier : " + uid);
    }

}
