package com.example.spring_boot_database.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.example.spring_boot_database.dto.ErrorResponse;
import com.example.spring_boot_database.dto.FieldErrorResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception){

        List<FieldErrorResponse> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> FieldErrorResponse.builder()
                        .field(err.getField())
                        .message(err.getDefaultMessage())
                        .build()
                )
                .collect(Collectors.toList());

        ErrorResponse<?> response = ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message("Invalid request")
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


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