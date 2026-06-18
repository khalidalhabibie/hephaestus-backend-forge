package com.example.demo.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.dto.FieldErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception){

        List<FieldErrorResponse> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> FieldErrorResponse.builder()
                        .field(err.getField())
                        .message(err.getDefaultMessage())
                        .build()
                )
                .collect(Collectors.toList());

        ErrorResponse response = ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message("Invalid request")
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> customerNotFoundException(CustomerNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.builder().code("CUSTOMER_NOT_FOUND").message(exception.getMessage()).errors(List.of()).build());

    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> loanApplicationNotFoundException(LoanApplicationNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.builder().code("LOAN_APPLICATION_NOT_FOUND").message(exception.getMessage()).errors(List.of()).build());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.builder().code("INTERNAL_SERVER_ERROR").message(exception.getMessage()).errors(List.of()).build());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenException(ForbiddenException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse.builder().code("FORBIDDEN").message(exception.getMessage()).errors(List.of()).build());
    }
    
}

