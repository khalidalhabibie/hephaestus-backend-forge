package com.example.training.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.example.training.dto.LoginRequest;
import com.example.training.dto.LoginResponse;
import com.example.training.dto.UserResponse;
import com.example.training.helper.ValidateTokenRoleHelper;
import com.example.training.model.Role;
import com.example.training.model.User;
import com.example.training.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "AuthController", description = "API for authentication process.")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login.", description = "Checks user input for login access.")
    @ApiResponse(responseCode = "201", description = "Login input submitted successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid user input.")
    public ResponseEntity<LoginResponse> userLogin(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    @Operation(summary = "Shows user data.", description = "Displays user data when logged in.")
    @ApiResponse(responseCode = "200", description = "Customer data retrieved successfully.")
    @ApiResponse(responseCode = "401", description = "Unauthorized.")
    public ResponseEntity<UserResponse> userData(HttpServletRequest request) { // Pakai HttpServletRequest
        // public ResponseEntity<UserResponse> userData(@RequestHeader("Authorization") String authorizationHeader) { // Pakai @RequestHeader
        
        // 1. Panggil helper di awal method untuk membungkus/memproteksi API ini
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        User currentUser = tokenHelper.validateTokenRole(request, authService.getUsers(), Role.ADMIN, Role.APPROVER, Role.STAFF);

        // 2. Jika lolos validasi, kode di bawah ini baru akan berjalan
        UserResponse response = authService.getCurrentUser(currentUser);
        return ResponseEntity.ok(response);
    }
        
}
