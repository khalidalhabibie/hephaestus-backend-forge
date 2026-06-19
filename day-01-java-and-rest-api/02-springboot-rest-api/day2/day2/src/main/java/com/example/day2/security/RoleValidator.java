package com.example.day2.security;

import java.util.Arrays;

public class RoleValidator {
    public static boolean hasAccess(String userRole, String... allowedRoles) {
        if ("ADMIN".equals(userRole)) {
            return true;
        }
        return Arrays.asList(allowedRoles).contains(userRole);
    }
}
