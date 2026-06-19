package com.fif.exercise02.security;

import com.fif.exercise02.entity.Role;

public class RoleValidator {

    public static boolean isAllowed(Role role, Role... allowedRoles) {
        for (Role allowed : allowedRoles) {
            if (role == allowed) {
                return true;
            }
        }
        return false;
    }
}
