package com.fif.exercise02.security;

import com.fif.exercise02.entity.Role;

import io.jsonwebtoken.Claims;

public class AuthUtil {

    public static AuthContext parseToken(String token) {

        if (token == null)
            return null;
        
        Claims claims = JwtUtil.parseToken(token);

        if (claims == null)
            return null;

        return new AuthContext(
                claims.getSubject(),                       
                Role.valueOf(claims.get("role", String.class)),         
                claims.get("username", String.class)      
        );


        // switch (token) {
        //     case "token-admin":
        //         return new AuthContext("USR-001", "ADMIN","admin");

        //     case "token-staff":
        //         return new AuthContext("USR-002", "STAFF","staff");

        //     case "token-approver":
        //         return new AuthContext("USR-003", "APPROVER","approver");

        //     case "token-manager":
        //         return new AuthContext("USR-004", "MANAGER", "manager");

        //     default:
        //         return null;
        // }
    }

    public static String extractToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }
}