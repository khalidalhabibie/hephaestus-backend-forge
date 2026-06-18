package com.andyana.exerciseday02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import org.jspecify.annotations.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private List<FieldErrorResponse> errors;
    private T data;

    public static <T> ErrorResponse <T> success (T data) {
        return ErrorResponse.<T>builder()
                .success(true)
                .code("200")
                .message("Success")
                .data(data)
                .build();
    }

    public static ErrorResponse<Void> error(String code, String message, @Nullable List<FieldErrorResponse> errors) {
        return ErrorResponse.<Void>builder()
                .success(false)
                .code(code)
                .message(message)
                .errors(errors == null ? Collections.emptyList() : errors)
                .build();
    } 

}
