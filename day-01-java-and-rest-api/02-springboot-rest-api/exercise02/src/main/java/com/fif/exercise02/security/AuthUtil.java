package com.fif.exercise02.security;

public class AuthUtil {

    public static AuthContext parseToken(String token) {

        if (token == null)
            return null;

        switch (token) {
            case "token-admin":
                return new AuthContext("USR-001", "ADMIN","admin");

            case "token-staff":
                return new AuthContext("USR-002", "STAFF","staff");

            case "token-approver":
                return new AuthContext("USR-003", "APPROVER","approver");

            default:
                return null;
        }
    }

    public static String extractToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }
}