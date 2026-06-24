package com.adnan.exercisespring.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adnan.exercisespring.dto.LoginRequest;
import com.adnan.exercisespring.dto.LoginResponse;
import com.adnan.exercisespring.exception.UnauthorizedException;
import com.adnan.exercisespring.model.User;
import com.adnan.exercisespring.security.JwtTokenProvider;
import com.adnan.exercisespring.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUtil securityUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userService.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return new LoginResponse(jwtTokenProvider.generate(user), user.getId(), user.getEmail(), user.getRole());
    }

    public LoginResponse me() {
        User user = securityUtil.currentUser();
        return new LoginResponse(null, user.getId(), user.getEmail(), user.getRole());
    }
}
