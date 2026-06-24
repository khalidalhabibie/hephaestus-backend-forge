package com.example.demo_day2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo_day2.dto.LoginRequest;
import com.example.demo_day2.dto.LoginResponse;
import com.example.demo_day2.dto.UserResponse;
import com.example.demo_day2.dto.WebResponse;
import com.example.demo_day2.model.User;
import com.example.demo_day2.security.AuthUtil;
import com.example.demo_day2.service.AuthService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        var user = authService.login(request.getUsername(), request.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(WebResponse.error("UNAUTHORIZED", "Invalid username or password", null));
        }

        return ResponseEntity.ok(
                new LoginResponse(user.getToken(), user.getUsername(), user.getRole()));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(HttpServletRequest request) {

        User user = AuthUtil.authenticate(request, authService);

        return ResponseEntity.ok(
                new UserResponse(user.getUsername(), user.getRole()));
    }
}
