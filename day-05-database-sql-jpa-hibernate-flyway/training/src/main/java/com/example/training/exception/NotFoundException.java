package com.example.training.exception;

public class NotFoundException extends RuntimeException {

    private final String code;

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public NotFoundException(String message) {
        super(message);
        this.code = "NOT_FOUND";
    }

    public String getCode() {
        return code;
    }
}
