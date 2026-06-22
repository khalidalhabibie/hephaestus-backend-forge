package com.fif.exercise02.controller;


import com.fif.exercise02.dto.*;
import com.fif.exercise02.service.AuthService;
import com.fif.exercise02.security.AuthUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API", description = "Authentication and current user API")
public class AuthController {

    private final AuthService service = new AuthService();

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user using username and password and return token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login success"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(service.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(ErrorResponse.error("401", "UNAUTHORIZED", null));
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get detail of currently authenticated user from token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized or token invalid")
    })
    public ResponseEntity<?> me(@RequestHeader("Authorization") String header) {

        String token = AuthUtil.extractToken(header);
        var ctx = AuthUtil.parseToken(token);

        if (ctx == null) {
            return ResponseEntity.status(401)
                    .body(ErrorResponse.error("401", "UNAUTHORIZED", null));
        }

        return ResponseEntity.ok(
                new UserResponse(
                        ctx.getUserId(),
                        ctx.getUsername(),
                        ctx.getRole()));
    }
}
