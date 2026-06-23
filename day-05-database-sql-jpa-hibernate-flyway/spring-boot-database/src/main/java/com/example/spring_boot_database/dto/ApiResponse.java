package com.example.spring_boot_database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import org.slf4j.MDC;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    
    private String message;
    
    private T data;
    
    private String error;
    
    private List<String> details;

    private String correlationId;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder().success(true).message(message).data(data).correlationId(MDC.get("correlation_id")).build();
    }

    public static ApiResponse<Void> error(String error, String message, List<String> details) {
        return ApiResponse.<Void>builder()
                .success(false)
                .error(error)
                .message(message)
                .details(details == null ? Collections.emptyList() : details)
                .correlationId(MDC.get("correlation_id"))
                .build();
    }
}
