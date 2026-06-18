package com.fif.exercise02.controller;


import com.fif.exercise02.dto.*;
import com.fif.exercise02.service.AuthService;
import com.fif.exercise02.security.AuthUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service = new AuthService();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(service.login(request));
        } catch (Exception e) {

            return ResponseEntity.status(401)
                    .body(ErrorResponse.error("401", "UNAUTHORIZED", null));
                }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String header) {

        String token = AuthUtil.extractToken(header);
        var ctx = AuthUtil.parseToken(token);

        if (ctx == null) {
            return ResponseEntity.status(401)
                    .body(ErrorResponse.error("401", "UNAUTHORIZED", null));
        }

        return ResponseEntity.ok(
                new UserResponse(
                        ctx.getUserId(),ctx.getUsername(),
                        ctx.getRole()));
    }
}