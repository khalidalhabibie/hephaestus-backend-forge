// Tangkap semua error dan convert ke ErrorResponse dengan HTTP status yang sesuai (404, 400, 409, 500).

package com.example.training.Exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.training.DTO.ErrorResponse;
import com.example.training.DTO.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("CUSTOMER_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("LOAN_APPLICATION_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRepaymentNotFound(RepaymentScheduleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("REPAYMENT_SCHEDULE_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCustomer(DuplicateCustomerException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of("DUPLICATE_CUSTOMER", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("VALIDATION_ERROR", "Invalid request", errors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("INVALID_ARGUMENT", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("INTERNAL_ERROR", ex.getMessage()));
    }

    private FieldErrorResponse mapFieldError(FieldError error) {
        return FieldErrorResponse.builder()
                .field(error.getField())
                .message(error.getDefaultMessage())
                .build();
    }
}