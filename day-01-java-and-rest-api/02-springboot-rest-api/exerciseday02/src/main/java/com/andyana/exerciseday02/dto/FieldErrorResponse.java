package com.andyana.exerciseday02.dto;


import lombok.Data;

@Data
public class FieldErrorResponse {
    private String field;
    private String message;
}
