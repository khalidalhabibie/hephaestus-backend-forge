package com.example.jpabackend.exception;

public class RepaymentScheduleNotFoundException extends RuntimeException {
    public RepaymentScheduleNotFoundException(String message) {
        super(message);
    }
}