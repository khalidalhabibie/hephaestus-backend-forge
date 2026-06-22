// Error kalau NIK/email sudah ada → otomatis jadi HTTP 409.

package com.example.training.Exception;

public class DuplicateCustomerException extends RuntimeException {
    public DuplicateCustomerException(String field, String value) {
        super("Customer already exists with " + field + ": " + value);
    }
}