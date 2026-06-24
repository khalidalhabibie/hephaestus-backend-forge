package com.example.demoSpringbootDatabase.exception;

public class RepaymentScheduleNotFoundException extends ResourceNotFoundException {
    public RepaymentScheduleNotFoundException(Long id) { super("Repayment schedule not found with id: " + id); }
}
