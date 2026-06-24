package com.adnan.exercisespring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import com.adnan.exercisespring.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    @JsonProperty("user_id")
    private UUID userId;
    private String email;
    private RoleEnum role;
}
