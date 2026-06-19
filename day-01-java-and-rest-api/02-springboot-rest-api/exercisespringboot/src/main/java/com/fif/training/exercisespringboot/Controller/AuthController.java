package com.fif.training.exercisespringboot.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fif.training.exercisespringboot.DTO.LoginRequest;
import com.fif.training.exercisespringboot.DTO.LoginResponse;
import com.fif.training.exercisespringboot.DTO.UserResponse;
import com.fif.training.exercisespringboot.Service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@RequestHeader("Authorization") String token) {
        String fixToken = token.replace("Bearer ", "");
        UserResponse userResponse = authService.getCurrentUser(fixToken);
        return ResponseEntity.ok(userResponse);
    }
}