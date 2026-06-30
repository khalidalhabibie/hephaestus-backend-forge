package com.andyana.exerciseday02.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.andyana.exerciseday02.dto.ErrorResponse;
import com.andyana.exerciseday02.dto.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(this::format)
            .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.error("VALIDATION_ERROR", "Invalid request", errors));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse<Void>> handleMethodValidation(HandlerMethodValidationException ex) {
        List<FieldErrorResponse> errors = ex.getBeanResults().stream()
            .map(parameterResult -> {
                FieldErrorResponse response = new FieldErrorResponse();
                response.setField(toSnakeCase(parameterResult.getMethodParameter().getParameterName()));
                String message = parameterResult.getResolvableErrors().stream()
                    .map(MessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
                
                response.setMessage(message);
                return response;
            })
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.error("VALIDATION_ERROR", "Invalid request", errors));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse<Void>> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.error("CUSTOMER_NOT_FOUND", ex.getMessage(), null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.error("CUSTOMER_NOT_FOUND", ex.getMessage(), null));
}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Void>> handleGlobal(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.error("INTERNAL_SERVER_ERROR", "Internal server error: " + ex.getMessage(), null));
    }

    private FieldErrorResponse format(FieldError error) {
        FieldErrorResponse response = new FieldErrorResponse();
        response.setField(toSnakeCase(error.getField()));
        response.setMessage(error.getDefaultMessage());
        return response;
    }

    private String toSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}