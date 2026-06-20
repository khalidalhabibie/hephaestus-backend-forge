package com.example.main.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.main.dto.response.FieldErrorResponse;
import com.example.main.template.Response;
import com.example.main.utils.FormattingText;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorResponse(
                        FormattingText.toSnakeCase(error.getField()), 
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        Response<Object> response = Response.errorSpec("VALIDATION_ERROR", "Invalid request", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response<Object>> handleUnauthorizedException(UnauthorizedException ex) {
        Response<Object> response = Response.errorSpec(
                "UNAUTHORIZED", 
                ex.getMessage(), 
                new ArrayList<>()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Forbidden
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Response<Object>> handleForbiddenException(ForbiddenException ex) {
        Response<Object> response = Response.errorSpec(
                "FORBIDDEN", 
                "You do not have permission to access this resource", 
                new ArrayList<>()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // Customer Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Object>> handleCustomerNotFoundException(NotFoundException ex) {
        
        Response<Object> response = Response.errorSpec("NOT_FOUND", ex.getMessage(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Loan Application Not Found
    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<Response<Object>> handleLoanApplicationNotFoundException(
            LoanApplicationNotFoundException ex) {
        
        Response<Object> response = Response.errorSpec(
                "LOAN_APPLICATION_NOT_FOUND", 
                ex.getMessage(), 
                new java.util.ArrayList<>()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Duplicate Data
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Response<Object>> handleDuplicateException(DuplicateException ex) {
        Response<Object> response = Response.errorSpec(
                "DUPLICATE_DATA", 
                ex.getMessage(), 
                new ArrayList<>()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response<Object>> handleBadRequestException(BadRequestException ex) {
        Response<Object> response = Response.errorSpec(
                "BAD_REQUEST", 
                ex.getMessage(), 
                new ArrayList<>()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleGenericException(Exception ex) {
        
        Response<Object> response = Response.errorSpec("INTERNAL_SERVER_ERROR", "Unexpected error occurred", new ArrayList<>());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}