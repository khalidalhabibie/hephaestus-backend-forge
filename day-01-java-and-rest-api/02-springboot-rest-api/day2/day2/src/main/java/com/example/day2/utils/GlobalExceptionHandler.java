package com.example.day2.utils;

import com.example.day2.dto.FieldErrorResponse;
import com.example.day2.dto.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> FieldErrorResponse.builder()
                        .field(e.getField().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase())
                        .message(e.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Void>builder()
                        .code("VALIDATION_ERROR")
                        .message("Input data tidak valid")
                        .errors(fieldErrors)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<WebResponse<Void>> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                WebResponse.<Void>builder()
                        .code("NOT_FOUND")
                        .message(ex.getMessage())
                        .errors(Collections.emptyList())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<WebResponse<Void>> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                WebResponse.<Void>builder()
                        .code("LOAN_APPLICATION_NOT_FOUND")
                        .message(ex.getMessage())
                        .errors(Collections.emptyList())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<WebResponse<Void>> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(422)).body(
                WebResponse.<Void>builder()
                        .code("INVALID_STATE")
                        .message(ex.getMessage())
                        .errors(Collections.emptyList())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<WebResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                WebResponse.<Void>builder()
                        .code("UNAUTHORIZED")
                        .message("Invalid username or password")
                        .errors(Collections.emptyList())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<WebResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                WebResponse.<Void>builder()
                        .code("FORBIDDEN")
                        .message("You do not have permission to access this resource")
                        .errors(Collections.emptyList())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<WebResponse<Void>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<Void>builder()
                        .code("BAD_REQUEST")
                        .message(ex.getMessage())
                        .errors(Collections.emptyList())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                WebResponse.<Void>builder()
                        .code("INTERNAL_SERVER_ERROR")
                        .message("Terjadi kesalahan internal pada server")
                        .errors(Collections.emptyList())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
