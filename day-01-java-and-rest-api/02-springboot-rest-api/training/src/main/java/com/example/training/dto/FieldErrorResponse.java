package com.example.training.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErrorResponse {
    private String field;
    private String message;
}
