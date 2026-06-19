package com.example.training.service;

import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.example.training.dto.LoginRequest;
import com.example.training.dto.LoginResponse;
import com.example.training.dto.UserResponse;
import com.example.training.exception.UnauthorizedException;
import com.example.training.model.Role;
import com.example.training.model.User;

@Service
public class AuthService {
    private final Map<String, User> users = Map.of(
        "admin", new User(UUID.randomUUID(), "admin", "admin123", Role.ADMIN, "token-admin"),
        "staff", new User(UUID.randomUUID(), "staff", "staff123", Role.STAFF, "token-staff"),
        "approver", new User(UUID.randomUUID(), "approver", "approver123", Role.APPROVER, "token-approver")        
    );

    public Map<String, User> getUsers() {
        return this.users;
    }

    public LoginResponse login(LoginRequest request) {
        User userLogin = users.get(request.getUsername());
        if (userLogin == null || !userLogin.getPassword().equals(request.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        LoginResponse response = new LoginResponse();
        response.setToken(userLogin.getToken());
        response.setUsername(userLogin.getUsername());
        response.setRole(userLogin.getRole());

        return response;
    }

    public UserResponse getCurrentUser(User user) {
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return response;
    }
}