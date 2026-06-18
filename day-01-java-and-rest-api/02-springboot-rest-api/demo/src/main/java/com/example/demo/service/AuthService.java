package com.example.demo.service;

import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.User;
import com.example.demo.security.AuthUtil;
import com.example.demo.security.JwtTokenProvider;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;
    private final InMemoryUserStore userRepository;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new BadRequestException("Invalid username or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = jwtTokenProvider.generate(user);

        return new LoginResponse(user.getUsername(), user.getRole(), token);
    }

    public LoginResponse me() {
        String username = authUtil.currentUsername();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new BadRequestException("User not found");
        }

        return new LoginResponse(user.getUsername(), user.getRole(), null);
    }
}
