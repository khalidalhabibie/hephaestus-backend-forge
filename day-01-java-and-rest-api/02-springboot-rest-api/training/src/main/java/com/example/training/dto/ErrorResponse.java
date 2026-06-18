package com.example.training.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String code;
    private String message;
    private List<FieldErrorResponse> errors;

    public ErrorResponse(String code, String message, List<FieldErrorResponse> errors) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.message = message;
        this.errors = errors;
    }
}
