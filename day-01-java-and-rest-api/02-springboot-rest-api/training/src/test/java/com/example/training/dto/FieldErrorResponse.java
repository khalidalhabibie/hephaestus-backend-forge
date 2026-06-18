package com.example.training.dto;

import lombok.Data;

@Data
public class FieldErrorResponse {
    private String field;
    private String message;
}
