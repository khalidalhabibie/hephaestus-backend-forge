package com.example.day2.security;

import java.util.Arrays;
import java.util.List;

public class RoleValidator {
    public static boolean hasAccess(String userRole, String... allowedRoles) {
        if ("ADMIN".equals(userRole)) {
            return true; // ADMIN otomatis bisa mengakses semua endpoint
        }
        List<String> roles = Arrays.asList(allowedRoles);
        return roles.contains(userRole);
    }
}
