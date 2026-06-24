package com.example.day2.service;

import com.example.day2.dto.LoginRequest;
import com.example.day2.dto.LoginResponse;
import com.example.day2.model.User;
import com.example.day2.security.AuthContext;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public LoginResponse login(LoginRequest request) {
        User user = AuthContext.getUserByUsername(request.getUsername());
        if (user != null && user.getPassword().equals(request.getPassword())) {
            return new LoginResponse(user.getToken(), user.getUsername(), user.getRole());
        }
        return null;
    }
}
