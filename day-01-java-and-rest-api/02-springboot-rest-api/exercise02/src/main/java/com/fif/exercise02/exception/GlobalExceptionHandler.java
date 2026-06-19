package com.fif.exercise02.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fif.exercise02.dto.ErrorResponse;
import com.fif.exercise02.dto.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Void>> validation(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> FieldErrorResponse.builder()
                        .field(error.getField().replaceAll("([a-z])([A-Z])", "$1_$2")
                                .toLowerCase())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        // return
        // ResponseEntity.badRequest().body(ErrorResponse.error("VALIDATION_ERROR",
        // "Invalid request", errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.error("VALIDATION_ERROR", "invalid request", errors));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse<Void>> notFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.error("CUSTOMER_NOT_FOUND", ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Void>> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.error("INTERNAL_SERVER_ERROR", "Unexpected error occurred", null));
    }

    

}
