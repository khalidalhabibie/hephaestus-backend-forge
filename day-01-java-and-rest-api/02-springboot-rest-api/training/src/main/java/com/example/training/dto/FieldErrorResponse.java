package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//menyederhanakan getter setter
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@JsonPropertyOrder
public class FieldErrorResponse<T> {
    private String field;
    private String message;

    
    public static FieldErrorResponse<Void> error(String field, String message) {
        return FieldErrorResponse.<Void>builder()
            .field(field)
            .message(message)
            .build();
    }
}