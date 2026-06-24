package com.example.spring_boot_database.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(Long id){
        super("Loan application not found with id: " + id);
    }
 
}
