package com.fif.finance_training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String code;
    private String message;

    @JsonProperty("correlation_id")
    private String correlationId;

    private List<FieldErrorResponse> errors;
}