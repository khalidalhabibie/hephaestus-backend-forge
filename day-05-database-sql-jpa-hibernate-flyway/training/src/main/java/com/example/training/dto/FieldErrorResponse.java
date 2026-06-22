package com.example.training.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FieldErrorResponse {
    private String field;
    private String message;
}