package com.fif.finance_training.exception;

public class CustomerNotFoundException extends RuntimeException {
    public String code;
    public String message;
    public String errors;

    // 1. TAMBAHKAN INI: Constructor 1 parameter agar service kamu tidak error lagi
    public CustomerNotFoundException(String message) {
        super(message);
        this.code = "CUSTOMER_NOT_FOUND";
        this.message = message;
    }

    // 2. Jaga constructor 3 parameter ini jika sewaktu-waktu dipanggil di tempat lain
    public CustomerNotFoundException(String code, String message, String errors){
        super(message);
        this.code = code;
        this.message = message;
        this.errors = errors;
    }
}