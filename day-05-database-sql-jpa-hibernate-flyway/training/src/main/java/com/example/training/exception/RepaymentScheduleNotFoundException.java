package com.example.training.exception;

public class RepaymentScheduleNotFoundException extends RuntimeException {
    public RepaymentScheduleNotFoundException(String message) {
        super(message);
    }
}
