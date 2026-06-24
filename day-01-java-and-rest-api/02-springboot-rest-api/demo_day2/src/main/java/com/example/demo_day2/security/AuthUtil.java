package com.example.demo_day2.security;

import com.example.demo_day2.exception.UnauthorizedException;
import com.example.demo_day2.model.User;
import com.example.demo_day2.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtil {

    public static User authenticate(HttpServletRequest request, AuthService authService) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new UnauthorizedException("Authentication is required");
        }

        String token = header.replace("Bearer ", "");
        User user = authService.getByToken(token);

        if (user == null) {
            throw new UnauthorizedException("Invalid token");
        }

        return user;
    }
}