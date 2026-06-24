package com.example.jpabackend.dto.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("message")
    private String message;

    @JsonProperty("correlation_id")
    private String correlationId;

    private List<FieldErrorResponse> errors;

    public static ErrorResponse of(String code, String message, List<FieldErrorResponse> errors) {
        return ErrorResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .errors(errors)
                .build();
    }
}