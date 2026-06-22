package com.example.exercise.dto;

import java.util.UUID;

import com.example.exercise.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonPropertyOrder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @JsonProperty("token")
    private String token;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private Role role;
}
