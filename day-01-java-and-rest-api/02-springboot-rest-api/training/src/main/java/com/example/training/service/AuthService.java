package com.example.training.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.training.dto.LoginRequest;
import com.example.training.dto.LoginResponse;
import com.example.training.dto.UserResponse;
import com.example.training.exception.UnauthorizedException;
import com.example.training.model.User;
import com.example.training.security.AuthUtil;

@Service
public class AuthService {

    private final List<User> users = List.of (
        new User (
            "admin",
            "admin123",
            "ADMIN",
            "token-admin"
        ),

        new User (
            "staff",
            "staff123",
            "STAFF",
            "token-staff"
        ),

        new User (
            "approver",
            "approver123",
            "APPROVER",
            "token-approver"
        ),
        
        new User(
            "manager",
            "manager123",
            "MANAGER",
            "token-manager"
        )
    );
    
    public LoginResponse login(LoginRequest request) {
        for (User user : users) {
            if (user.getUsername().equals(request.getUsername())
                && user.getPassword().equals(request.getPassword())) {
                return LoginResponse.builder()
                    .token(user.getToken())
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build();
                }
            }
            throw new UnauthorizedException("Invalid username or password");
        }
        
    public UserResponse getCurrentUser(String authorization) {
        String token = AuthUtil.extractToken(authorization);
        for (User user : users) {
            if (user.getToken().equals(token)) {
                return UserResponse.builder()
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build();
                }
            }
            throw new UnauthorizedException("Authentication is required");
        }
        
    public User findByToken(String token) {
        for (User user : users) {
            if (user.getToken().equals(token)) {
                return user;
            }
        }
        throw new UnauthorizedException("Authentication is required");
    }
}