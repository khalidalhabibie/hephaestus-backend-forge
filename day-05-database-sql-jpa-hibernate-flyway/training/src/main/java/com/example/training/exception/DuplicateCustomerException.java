package com.example.training.exception;

public class DuplicateCustomerException extends RuntimeException {

    private final String code;

    public DuplicateCustomerException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}