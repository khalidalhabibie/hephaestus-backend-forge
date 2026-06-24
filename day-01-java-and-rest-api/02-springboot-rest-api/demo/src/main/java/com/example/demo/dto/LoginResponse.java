package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginResponse {

    private String username;

    private Role role;

    private String token;
}
