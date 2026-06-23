package com.fif.loanapplication.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(UUID uid) {
        super("Customer not found with unique identifier : " + uid);
    }

}
