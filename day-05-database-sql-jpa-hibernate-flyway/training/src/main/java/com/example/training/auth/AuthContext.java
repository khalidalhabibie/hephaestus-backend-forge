package com.example.training.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthContext {
    private String userId;
    private String role;
}
