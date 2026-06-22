package com.example.spring_boot_database.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFound(CustomerNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<?> handleLoanNotFound(LoanNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "LOAN_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<?> handleRepaymentNotFound(RepaymentScheduleNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "REPAYMENT_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateCustomerException ex) {
        return build(HttpStatus.BAD_REQUEST, "DUPLICATE_DATA", ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex) {
        return build(HttpStatus.BAD_REQUEST, "BAD_REQUEST", ex.getMessage());
    }

    private ResponseEntity<?> build(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status).body(
                Map.of(
                        "success", false,
                        "code", code,
                        "message", message
                )
        );
    }
}