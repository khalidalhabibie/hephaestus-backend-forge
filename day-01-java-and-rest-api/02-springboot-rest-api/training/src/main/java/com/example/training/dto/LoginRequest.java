package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


public class LoginRequest {
    @NotBlank
    @Getter
    @Setter
    private String username;
    @NotBlank
    @Getter
    @Setter
    private String password;
}
