package com.example.demo_day2.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;
    private String message;
    private String errors;
}
