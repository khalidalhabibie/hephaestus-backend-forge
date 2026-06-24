package com.example.exercise.exception;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.exercise.dto.ErrorResponse;
import com.example.exercise.dto.FieldErrorResponse;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final String CUSTOMER_NOT_FOUND = "CUSTOMER_NOT_FOUND";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";
    public static final String LOAN_APPLICATION_NOT_FOUND = "LOAN_APPLICATIION_NOT_FOUND";
    public static final String REPAYMENT_SCHEDULE_NOT_FOUND = "REPAYMENT_SCHEDULE_NOT_FOUND";
    public static final String PAYMENT_TRANSACTION_NOT_FOUND = "PAYMENT_TRANSACTION_NOT_FOUND";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String DUPLICATE = "DUPLICATE";
    

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(CUSTOMER_NOT_FOUND, ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream().map(this::toFieldError).toList();
        ErrorResponse errorResponse = new ErrorResponse(VALIDATION_ERROR, "Invalid request", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, "Unexpected error occurred", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            UNAUTHORIZED,
            ex.getMessage(),
            List.of()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler (ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                FORBIDDEN,
                ex.getMessage(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    
    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(LoanApplicationNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LOAN_APPLICATION_NOT_FOUND,
                ex.getMessage(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(RepaymentScheduleNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                REPAYMENT_SCHEDULE_NOT_FOUND,
                ex.getMessage(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PaymentTransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(PaymentTransactionNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                PAYMENT_TRANSACTION_NOT_FOUND,
                ex.getMessage(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                BAD_REQUEST,
                ex.getMessage(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(DuplicateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                DUPLICATE,
                ex.getMessage(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private FieldErrorResponse toFieldError(FieldError fieldError) {
        FieldErrorResponse response = new FieldErrorResponse();
        response.setField(fieldError.getField());
        response.setMessage(fieldError.getDefaultMessage());
        return response;
    }
}