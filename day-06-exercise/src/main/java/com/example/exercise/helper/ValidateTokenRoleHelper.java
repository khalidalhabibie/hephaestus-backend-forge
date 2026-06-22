package com.example.exercise.helper;

import java.util.Map;

import org.springframework.web.client.HttpClientErrorException.Forbidden;

import jakarta.servlet.http.HttpServletRequest;

import com.example.exercise.enums.Role;
import com.example.exercise.exception.ForbiddenException;
import com.example.exercise.model.User;

public class ValidateTokenRoleHelper {

    public User validateTokenRole(
            HttpServletRequest request, 
            Map<String, User> users,
            Role... allowedRoles) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }

        String token = authorizationHeader.replace("Bearer ", "");

        User user = users.values()
                .stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst()
                .orElse(null);

        if (user == null) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }

        // RBAC CHECK
        for (Role role : allowedRoles) {
            if (user.getRole() == role) {
                return user;
            }
        }
        throw new ForbiddenException("You do not have permission to access this resource");
    }
}