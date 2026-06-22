package com.example.demoSpringbootDatabase.exception;

import com.example.demoSpringbootDatabase.dto.ErrorResponse;
import com.example.demoSpringbootDatabase.dto.FieldErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse.builder().success(false).code("CUSTOMER_NOT_FOUND").message(ex.getMessage()).errors(new ArrayList<>()).build()
        );
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse.builder().success(false).code("LOAN_APPLICATION_NOT_FOUND").message(ex.getMessage()).errors(new ArrayList<>()).build()
        );
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScheduleNotFound(RepaymentScheduleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse.builder().success(false).code("REPAYMENT_SCHEDULE_NOT_FOUND").message(ex.getMessage()).errors(new ArrayList<>()).build()
        );
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateCustomerException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse.builder().success(false).code("DUPLICATE_DATA").message(ex.getMessage()).errors(new ArrayList<>()).build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new FieldErrorResponse(err.getField(), err.getDefaultMessage()))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse.builder().success(false).code("VALIDATION_ERROR").message("Invalid request").errors(errors).build()
        );
    }
}