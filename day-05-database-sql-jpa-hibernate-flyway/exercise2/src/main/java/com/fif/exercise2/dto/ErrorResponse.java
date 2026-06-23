package com.fif.exercise2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // correlation_id tidak muncul kalau null
public class ErrorResponse {

    private boolean success;
    private String code;
    private String message;
    @JsonProperty("correlation_id")
    private String correlationId;

    private List<FieldErrorResponse> errors;

    // Constructor lama tetap ada agar tidak ada breaking change di test
    public ErrorResponse(boolean success, String code, String message, List<FieldErrorResponse> errors) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }
}