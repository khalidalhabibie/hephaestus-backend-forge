package com.fif.training.exercisespringboot.Security;

public class RoleValidator {
    public static boolean allow(String role, String... allowedRoles) {
        if (role == null) return false;
        for (String allowed : allowedRoles) {
            if (role.equals(allowed)) {
                return true;
            }
        }
        return false;
    }
}