package com.example.day2.security;

import com.example.day2.model.User;

public class AuthUtil {
    public static String extractToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7).trim();
    }

    public static User validateToken(String header) {
        String token = extractToken(header);
        if (token == null) return null;
        return AuthContext.getUserByToken(token);
    }
}
