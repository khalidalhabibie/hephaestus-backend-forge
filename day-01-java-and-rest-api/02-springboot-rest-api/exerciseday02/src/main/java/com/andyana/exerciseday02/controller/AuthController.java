package com.andyana.exerciseday02.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andyana.exerciseday02.dto.ErrorResponse;
import com.andyana.exerciseday02.dto.LoginRequest;
import com.andyana.exerciseday02.dto.LoginResponse;
import com.andyana.exerciseday02.dto.UserResponse;
import com.andyana.exerciseday02.model.User;
import com.andyana.exerciseday02.service.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse response = authService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        if (response == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(
                                ErrorResponse.error(
                                        "UNAUTHORIZED",
                                        "Invalid username or password",
                                        null
                                )
                        );
        }

        return ResponseEntity.ok(
                ErrorResponse.success(response)
        );
}

        @GetMapping("/me")
        public UserResponse me(
        @RequestAttribute("currentUser") User user) {
        return new UserResponse(
                user.getUsername(),
                user.getRole().toString()
        );
}

}

