package com.fif.loanapplication.dto.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    String code;
    String message;
    @JsonProperty("correlation_id")
    private String correlationId;
    List<FieldErrorResponse> errors;

}