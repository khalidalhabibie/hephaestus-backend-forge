// Error kalau loan tidak ketemu → otomatis jadi HTTP 404.

package com.example.training.Exception;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(Long id) {
        super("Loan application not found with id: " + id);
    }
}