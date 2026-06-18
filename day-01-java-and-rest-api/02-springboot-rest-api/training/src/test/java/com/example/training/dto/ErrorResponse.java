package com.example.training.dto;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;
    private List<FieldErrorResponse> errors;
}