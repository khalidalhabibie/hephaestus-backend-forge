package com.adnan.exercisespring.security;

import com.adnan.exercisespring.enums.RoleEnum;
import com.adnan.exercisespring.exception.UnauthorizedException;
import com.adnan.exercisespring.model.User;
import com.adnan.exercisespring.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
    private final UserService userService;

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Authentication is required");
        }
        return userService.findById(UUID.fromString(authentication.getName()));
    }

    public boolean hasRole(RoleEnum role) {
        return currentUser().getRole() == role;
    }
}