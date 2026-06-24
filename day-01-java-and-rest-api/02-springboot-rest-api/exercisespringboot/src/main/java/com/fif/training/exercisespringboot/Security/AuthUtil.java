package com.fif.training.exercisespringboot.Security;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtil {
    public static String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }
}