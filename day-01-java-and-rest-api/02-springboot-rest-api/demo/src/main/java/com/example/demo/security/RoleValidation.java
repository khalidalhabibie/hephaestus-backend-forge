package com.example.demo.security;

import org.springframework.stereotype.Component;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.InMemoryUserStore;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RoleValidation {
    private final AuthUtil authUtil;
    private final InMemoryUserStore userRepository;


    public void assign(Role... roles) {
        String username = authUtil.currentUsername();

        if (username == null || username.isBlank()) {
            throw new ForbiddenException("Unauthenticated");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ForbiddenException("User not found");
        }

        for (Role role : roles) {
            if (user.getRole().equals(role)) {
                return;
            }
        }

        throw new ForbiddenException("You do not have permission to access this resource");
    }
    
}
