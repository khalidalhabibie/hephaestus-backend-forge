package com.example.jpabackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;

    public ApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // SUCCESS WITH DATA
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, "SUCCESS", message, data);
    }

    // SUCCESS WITHOUT DATA
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, "SUCCESS", message, null);
    }

    // ERROR
    public static ApiResponse<Void> error(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
