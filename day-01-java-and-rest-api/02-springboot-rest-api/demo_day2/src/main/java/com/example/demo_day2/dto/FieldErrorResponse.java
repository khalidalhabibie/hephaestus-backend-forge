package com.example.demo_day2.dto;

import lombok.Data;

@Data
public class FieldErrorResponse {

    private String field;
    private String message;
}
