package com.example.day2.dto;

import com.example.day2.enum_auth.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LoginResponse {
    private String token;
    private Role role;
    private String username;
}
