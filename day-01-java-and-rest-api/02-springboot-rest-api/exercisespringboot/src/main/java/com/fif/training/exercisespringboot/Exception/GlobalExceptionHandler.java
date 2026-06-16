package com.fif.training.exercisespringboot.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fif.training.exercisespringboot.DTO.ErrorResponse;
import com.fif.training.exercisespringboot.DTO.FieldErrorResponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> ValidationException(MethodArgumentNotValidException exception) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            FieldErrorResponse error = new FieldErrorResponse(
                    fieldError.getField(),
                    fieldError.getDefaultMessage());
            errors.add(error);
        }

        ErrorResponse response = new ErrorResponse("VALIDATION_EROR", "INVALID REQUEST!", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(CustomerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse
                        .builder()
                        .code("CUSTOMER_NOT_FOUND")
                        .message(exception.getMessage())
                        .errors(List.of())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalServerError(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse
                        .builder()
                        .code("INTERNAL_SERVER_ERROR")
                        .message(exception.getMessage())
                        .errors(List.of())
                        .build());
    }

}
