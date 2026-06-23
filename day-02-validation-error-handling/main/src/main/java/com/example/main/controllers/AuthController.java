package com.example.main.controllers;

import com.example.main.dto.LoginRequest;
import com.example.main.dto.LoginResponse;
import com.example.main.dto.UserMeResponse;
import com.example.main.security.UserRole;
import com.example.main.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.main.security.RequiresRoles;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF, UserRole.APPROVER})
    public ResponseEntity<UserMeResponse> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        UserMeResponse response = userService.getCurrentUser(token);
        return ResponseEntity.ok(response);
    }
}