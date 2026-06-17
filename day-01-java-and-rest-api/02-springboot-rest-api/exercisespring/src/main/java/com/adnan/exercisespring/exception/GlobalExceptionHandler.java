package com.adnan.exercisespring.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adnan.exercisespring.dto.ErrorResponse;
import com.adnan.exercisespring.dto.FieldErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse<Void>> badRequest(MethodArgumentNotValidException exception) {
                List<FieldErrorResponse> errors = exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> new FieldErrorResponse(
                                                toSnakeCase(error.getField()),
                                                error.getDefaultMessage()))
                                .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ErrorResponse.error("VALIDATION_ERROR", "Invalid request", errors));
        }

        @ExceptionHandler(CustomerNotFoundException.class)
        public ResponseEntity<ErrorResponse<Void>> notFound(CustomerNotFoundException exception) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ErrorResponse.error("CUSTOMER_NOT_FOUND",
                                                exception.getMessage() != null ? exception.getMessage()
                                                                : "Data not found",
                                                null));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse<Void>> generic(Exception exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ErrorResponse.error("INTERNAL_SERVER_ERROR", "Unexpected error occurred", null));
        }

        public static String toSnakeCase(String value) {
                return value
                                .replaceAll("([a-z])([A-Z])", "$1_$2")
                                .toLowerCase();
        }
}