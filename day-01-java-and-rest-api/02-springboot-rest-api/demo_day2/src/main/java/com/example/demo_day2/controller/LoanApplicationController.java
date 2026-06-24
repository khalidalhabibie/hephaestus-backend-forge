package com.example.demo_day2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_day2.dto.CreateLoanApplicationRequest;
import com.example.demo_day2.model.LoanStatus;
import com.example.demo_day2.model.Role;
import com.example.demo_day2.model.User;
import com.example.demo_day2.security.AuthUtil;
import com.example.demo_day2.security.RoleValidator;
import com.example.demo_day2.service.AuthService;
import com.example.demo_day2.service.LoanApplicationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService service;
    private final AuthService authService;

    public LoanApplicationController(LoanApplicationService service, AuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody CreateLoanApplicationRequest req,
            HttpServletRequest request) {

        User user = AuthUtil.authenticate(request, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.STAFF);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(name = "customer_id", required = false) Long customerId,
            HttpServletRequest request) {
        User user = AuthUtil.authenticate(request, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.STAFF, Role.APPROVER);

        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        User user = AuthUtil.authenticate(request, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.STAFF, Role.APPROVER);

        return ResponseEntity.ok(service.getById(id));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id, HttpServletRequest request) {

        User user = AuthUtil.authenticate(request, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.APPROVER, Role.MANAGER);

        return ResponseEntity.ok(service.approve(id, user));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id, HttpServletRequest request) {
        User user = AuthUtil.authenticate(request, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.APPROVER);

        return ResponseEntity.ok(service.reject(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id, HttpServletRequest request) {

        User user = AuthUtil.authenticate(request, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.STAFF, Role.MANAGER);

        return ResponseEntity.ok(service.cancel(id));
    }

}
