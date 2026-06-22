package com.example.jpabackend.dto;

import java.util.List;

public class ErrorResponse {

    private boolean success;
    private String code;
    private String message;
    private List<FieldErrorResponse> errors;

    public ErrorResponse(boolean success, String code, String message, List<FieldErrorResponse> errors) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static ErrorResponse error(String code, String message) {
        return new ErrorResponse(false, code, message, List.of());
    }

    public static ErrorResponse validation(String message, List<FieldErrorResponse> errors) {
        return new ErrorResponse(false, "VALIDATION_ERROR", message, errors);
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

    public List<FieldErrorResponse> getErrors() {
        return errors;
    }
}