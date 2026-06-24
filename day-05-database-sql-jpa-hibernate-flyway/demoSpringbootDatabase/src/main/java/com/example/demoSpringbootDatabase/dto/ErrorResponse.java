package com.example.demoSpringbootDatabase.dto;

import lombok.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String code;
    private String message;
    @JsonProperty("correlation_id") // Biar di Postman keluar sebagai correlation_id
    private String correlationId;
    private List<FieldErrorResponse> errors;
}
