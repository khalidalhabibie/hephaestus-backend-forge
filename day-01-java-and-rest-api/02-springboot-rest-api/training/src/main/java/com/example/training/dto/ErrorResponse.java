
package com.example.training.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String code;
    private String message;
    private List<FieldErrorResponse> errors;
    
    public ErrorResponse(String code, String message, List<FieldErrorResponse> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    
}
