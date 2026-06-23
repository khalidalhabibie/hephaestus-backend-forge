package com.fif.exercise2.dto;

import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonProperty;

// import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    @JsonProperty("correlation_id")
    private String correlationId;

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setCorrelationId(MDC.get("correlation_id"));
        return response;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        
        return response;
    }
}