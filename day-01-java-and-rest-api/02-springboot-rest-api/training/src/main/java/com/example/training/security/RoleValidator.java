package com.example.training.security;

import com.example.training.exception.ForbiddenException;
import com.example.training.model.User;

public class RoleValidator {
    public static void validate(User user, String... allowedRoles) {
        if (user == null) {
            throw new ForbiddenException("You don't have permission.");
        }
        for (String role : allowedRoles) {
            if (role.equalsIgnoreCase(user.getRole())) {
                return;
            }
        }
        throw new ForbiddenException("You don't have permission.");
    }
}