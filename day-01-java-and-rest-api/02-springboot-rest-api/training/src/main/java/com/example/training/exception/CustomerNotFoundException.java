package com.example.training.exception;

public class CustomerNotFoundException extends RuntimeException {
    public String code;
    public String message;
    public String errors;
    public CustomerNotFoundException(String code, String message, String errors){
        super(message);
    }
    
}
