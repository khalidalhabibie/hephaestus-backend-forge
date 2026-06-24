package com.fif.finance_training.exception;

public class RepaymentScheduleNotFoundException extends RuntimeException {
    public RepaymentScheduleNotFoundException(String message) {
        super(message);
    }
}