package com.example.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse<T> {
    @JsonProperty
    private String code;
    private String message;
    private List<FieldErrorResponse> fieldErrorResponses;
    
    
    public static ErrorResponse<Void> error(String code, String message, List<FieldErrorResponse> responses) {
        return ErrorResponse.<Void>builder()
            .code(code)
            .message(message)
            .fieldErrorResponses(responses)
            .build();
    }
}
