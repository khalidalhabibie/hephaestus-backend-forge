// Error kalau repayment tidak ketemu → otomatis jadi HTTP 404.

package com.example.training.Exception;

public class RepaymentScheduleNotFoundException extends RuntimeException {
    public RepaymentScheduleNotFoundException(Long id) {
        super("Repayment schedule not found with id: " + id);
    }
}
