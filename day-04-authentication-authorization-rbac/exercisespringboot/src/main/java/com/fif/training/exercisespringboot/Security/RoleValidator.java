package com.fif.training.exercisespringboot.Security;

import org.springframework.stereotype.Component;

import com.fif.training.exercisespringboot.Exception.ForbiddenException;
import com.fif.training.exercisespringboot.Model.Roles;

@Component
public class RoleValidator {

    public void requireAnyRole(AuthContext authContext, Roles... allowedRoles) {
        for (Roles role : allowedRoles) {
            if (authContext.getRole() == role) {
                return;
            }
        }

        throw new ForbiddenException();
    }
}
