package com.example.demoSpringbootDatabase.dto;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String code;
    private String message;
    private List<FieldErrorResponse> errors;
}
