package com.example.main.exceptions;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(Long id) {
        super("Customer not found with id: " + id);
    }
}