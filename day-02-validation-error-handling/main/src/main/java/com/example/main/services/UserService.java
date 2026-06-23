package com.example.main.services;

import com.example.main.dto.request.LoginRequest;
import com.example.main.dto.response.LoginResponse;
import com.example.main.dto.response.UserMeResponse;
import com.example.main.exceptions.UnauthorizedException;
import com.example.main.models.User;
import com.example.main.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User foundUser = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        return new LoginResponse(
                foundUser.getToken(),
                foundUser.getUsername(),
                foundUser.getRole().name() 
        );
    }

    public UserMeResponse getCurrentUser(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("Authentication is required"));

        return new UserMeResponse(user.getUsername(), user.getRole().name());
    }
}