package com.example.training.security;

import com.example.training.exception.UnauthorizedException;

public class AuthUtil {
    private AuthUtil() {}
    
    public static String extractToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException("Authentication is required");
        }
        return authorization.substring(7);
    }
}