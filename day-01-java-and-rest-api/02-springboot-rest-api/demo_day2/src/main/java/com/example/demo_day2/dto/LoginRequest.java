package com.example.demo_day2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    @NotBlank
    private String password;
}
