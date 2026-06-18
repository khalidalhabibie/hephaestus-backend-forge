package com.fif.training.exercisespringboot.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
//import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fif.training.exercisespringboot.DTO.ErrorResponse;
import com.fif.training.exercisespringboot.DTO.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldErrorResponse)
                .collect(Collectors.toList());
        ErrorResponse response = new ErrorResponse(
                "VALIDATION_ERROR",
                "Invalid request",
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "CUSTOMER_NOT_FOUND",
                ex.getMessage(),
                Collections.emptyList());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "Unexpected error occurred",
                Collections.emptyList());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private FieldErrorResponse toFieldErrorResponse(FieldError error) {
        return new FieldErrorResponse(error.getField(), error.getDefaultMessage());
    }
}