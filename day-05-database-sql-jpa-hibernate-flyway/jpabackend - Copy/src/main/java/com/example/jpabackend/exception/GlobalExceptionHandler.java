package com.example.jpabackend.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.jpabackend.dto.common.ErrorResponse;
import com.example.jpabackend.dto.common.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> FieldErrorResponse.builder()
            .field(fieldError.getField())
            .message(fieldError.getDefaultMessage())
            .build())
            .toList();
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("VALIDATION_ERROR")
            .message("Invalid request")
            .errors(errors)
            .build();
    return ResponseEntity.badRequest()
            .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("INTERNAL_SERVER_ERROR")
            .message("Unexpected error occurred")
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("CUSTOMER_NOT_FOUND")
            .message(ex.getMessage())
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCustomer(DuplicateCustomerException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("DUPLICATE_CUSTOMER")
            .message(ex.getMessage())
            .errors(List.of())
            .build();
    return ResponseEntity.badRequest()
            .body(response);
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("LOAN_APPLICATION_NOT_FOUND")
            .message(ex.getMessage())
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
    }

    @ExceptionHandler(PaymentTransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFound(PaymentTransactionNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("PAYMENT_TRANSACTION_NOT_FOUND")
            .message(ex.getMessage())
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScheduleNotFound(RepaymentScheduleNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("REPAYMENT_SCHEDULE_NOT_FOUND")
            .message(ex.getMessage())
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
    }
}
