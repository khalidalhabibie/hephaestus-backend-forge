package com.example.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import org.jspecify.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;

    @JsonProperty("correlation_id")
    private String correlationId;

    private List<FieldErrorResponse> errors;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code("200")
                .message("Success")
                .data(data)
                .build();
    }

    public static ApiResponse<Void> error(String code, String message, @Nullable List<FieldErrorResponse> errors) {
        return ApiResponse.<Void>builder()
                .success(false)
                .code(code)
                .message(message)
                .errors(errors == null ? Collections.emptyList() : errors)
                .build();
    }

    public static ApiResponse<Void> error(String code, String message, @Nullable String correlationId, @Nullable List<FieldErrorResponse> errors) {
        return ApiResponse.<Void>builder()
                .success(false)
                .code(code)
                .message(message)
                .correlationId(correlationId)
                .errors(errors == null ? Collections.emptyList() : errors)
                .build();
    }
}