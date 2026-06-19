package com.example.day2.controller;

import com.example.day2.dto.*;
import com.example.day2.model.User;
import com.example.day2.security.AuthUtil;
import com.example.day2.service.AuthService;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorResponse("UNAUTHORIZED", "Invalid username or password", new ArrayList<>()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String authHeader) {
    User user = AuthUtil.validateToken(authHeader);
    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("UNAUTHORIZED", "Authentication is required", new ArrayList<>())); 
                // Tambahkan new ArrayList<> () di parameter ketiga
    }
    return ResponseEntity.ok(new UserResponse(user.getUsername(), user.getRole()));
    }
}