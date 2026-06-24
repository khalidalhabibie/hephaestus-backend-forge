package com.example.jpabackend.dto.common;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String code;
    private String message;
    private List<FieldErrorResponse> errors;

    public static ErrorResponse of(String code, String message, List<FieldErrorResponse> errors) {
        return ErrorResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .errors(errors)
                .build();
    }
}