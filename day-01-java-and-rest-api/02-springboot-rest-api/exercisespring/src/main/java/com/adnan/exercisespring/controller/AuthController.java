package com.adnan.exercisespring.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adnan.exercisespring.dto.LoginRequest;
import com.adnan.exercisespring.dto.LoginResponse;
import com.adnan.exercisespring.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
  }

  @GetMapping("/me")
  public ResponseEntity<LoginResponse> me() {
    return ResponseEntity.status(HttpStatus.OK).body(authService.me());
  }
}
