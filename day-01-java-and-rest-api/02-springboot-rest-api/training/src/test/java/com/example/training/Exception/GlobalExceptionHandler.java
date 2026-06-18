package com.example.training.Exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.training.dto.WebResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<Void>> validation(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(this::format)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(WebResponse.error("BAD_REQUEST", "Validation failed", details));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<Void>> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebResponse.error("INTERNAL_SERVER_ERROR", ex.getMessage(), null));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<WebResponse<String>> CustomerNotFoundException(CustomerNotFoundException exeption) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(WebResponse.<String>builder().error(exeption.getMessage()).build());
    }
    
    private String format(FieldError error) {
        return error.getField() + " " + error.getDefaultMessage();
    }
}
