package com.example.training.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id) {
        super("Customer not found with id: " + id);
    }

    public CustomerNotFoundException(UUID uuid) {
        super("Loan Application not found with id: " + uuid);
    }
}
