package com.example.jpabackend.exception;

public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException(String message) {
        super(message);
    }
}
