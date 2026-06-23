// Bungkus response API (success, message, data, timestamp).

package com.example.training.DTO;

import java.time.ZonedDateTime;

import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonProperty("timestamp")
    private ZonedDateTime timestamp;

    @JsonProperty("correlation_id")
    private String correlationId;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .correlationId(MDC.get("correlation_id"))
                .timestamp(ZonedDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("Success", data);
    }
}