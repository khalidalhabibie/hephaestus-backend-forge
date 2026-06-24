package com.example.demoSpringbootDatabase.exception;

public class DuplicateCustomerException extends RuntimeException {
    public DuplicateCustomerException(String message) { super(message); }
}
