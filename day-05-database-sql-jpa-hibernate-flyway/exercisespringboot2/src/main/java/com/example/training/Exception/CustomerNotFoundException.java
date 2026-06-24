// Error kalau customer tidak ketemu → otomatis jadi HTTP 404.

package com.example.training.Exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Customer not found with id: " + id);
    }
}