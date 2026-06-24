package com.fif.loanapplication.dto.common;

import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class ApiResponseDto<T> {

    Boolean success;
    String message;
    T data;
    @JsonProperty("correlation_id")
    String correlationId;

    public static <T> ApiResponseDto<T> success(String message, T data) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .correlationId(MDC.get("correlation_id"))
                .build();
    }
}
