package com.fif.exercise02.security;


public class RoleValidator {

    public static boolean isAllowed(String role, String... allowedRoles) {
        for (String allowed : allowedRoles) {
            if (allowed.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
