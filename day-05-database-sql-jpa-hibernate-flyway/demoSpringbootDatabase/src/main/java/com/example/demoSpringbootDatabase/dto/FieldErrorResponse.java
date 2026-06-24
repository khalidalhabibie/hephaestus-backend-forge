package com.example.demoSpringbootDatabase.dto;

import lombok.*;

@Data @AllArgsConstructor
public class FieldErrorResponse {
    private String field;
    private String message;
}
