package com.example.spring_boot_database.exception;

public class RepaymentScheduleNotFoundException extends RuntimeException {
    public RepaymentScheduleNotFoundException(Long id) {
        super("Repayment schedule not found with id: " + id);
    }
}
