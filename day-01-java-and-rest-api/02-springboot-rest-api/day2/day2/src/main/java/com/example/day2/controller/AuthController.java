package com.example.day2.controller;

import com.example.day2.dto.LoginRequest;
import com.example.day2.dto.LoginResponse;
import com.example.day2.dto.RegisterRequest;
import com.example.day2.dto.UserResponse;
import com.example.day2.dto.WebResponse;
import com.example.day2.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication dan User Management")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
        summary = "Login",
        description = "Login dengan username dan password. Response berisi JWT token untuk request selanjutnya."
    )
    public ResponseEntity<WebResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse data = authService.login(request);
        return ResponseEntity.ok(WebResponse.<LoginResponse>builder()
                .code("SUCCESS")
                .message("Login successful")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register user baru",
        description = "Hanya ADMIN yang dapat mendaftarkan user baru. Role yang tersedia: STAFF, APPROVER, ADMIN."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<WebResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse data = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(WebResponse.<UserResponse>builder()
                        .code("SUCCESS")
                        .message("User registered successfully")
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/me")
    @Operation(
        summary = "Get current user",
        description = "Mengambil data user yang sedang login berdasarkan JWT token."
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<WebResponse<UserResponse>> getCurrentUser() {
        UserResponse data = authService.getCurrentUser();
        return ResponseEntity.ok(WebResponse.<UserResponse>builder()
                .code("SUCCESS")
                .message("Current user fetched successfully")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
