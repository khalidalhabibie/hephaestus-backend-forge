package com.example.spring_boot_database.exception;

public class DuplicateCustomerException extends RuntimeException {
    public DuplicateCustomerException(String field, String value) {
        super("Customer already exists with " + field + ": " + value);
    }
}
