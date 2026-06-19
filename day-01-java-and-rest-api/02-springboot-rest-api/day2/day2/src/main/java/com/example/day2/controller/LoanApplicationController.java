package com.example.day2.controller;

import com.example.day2.dto.*;
import com.example.day2.model.User;
import com.example.day2.security.AuthUtil;
import com.example.day2.security.RoleValidator;
import com.example.day2.service.LoanApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList; // Pastikan import ini ditambahkan

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService loanService;

    public LoanApplicationController(LoanApplicationService loanService) { this.loanService = loanService; }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                    @RequestBody CreateLoanApplicationRequest request) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("UNAUTHORIZED", "Authentication is required", new ArrayList<>()));
        if (!RoleValidator.hasAccess(user.getRole(), "STAFF")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("FORBIDDEN", "You do not have permission to access this resource", new ArrayList<>()));

        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(request));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam (required = false) String status, @RequestParam (name = "customer_id", required = false) Long customerId, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("UNAUTHORIZED", "Authentication is required", new ArrayList<>()));
        if (!RoleValidator.hasAccess(user.getRole(), "STAFF", "APPROVER")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("FORBIDDEN", "You do not have permission to access this resource", new ArrayList<>()));

        return ResponseEntity.ok(loanService.getLoanByCustomerIdStatus(status, customerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                    @PathVariable Long id) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("UNAUTHORIZED", "Authentication is required", new ArrayList<>()));
        if (!RoleValidator.hasAccess(user.getRole(), "STAFF", "APPROVER")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("FORBIDDEN", "You do not have permission to access this resource", new ArrayList<>()));

        LoanApplicationResponse response = loanService.getLoanById(id);
        if (response == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("LOAN_APPLICATION_NOT_FOUND", "Loan application not found", new ArrayList<>()));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approve(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                    @PathVariable Long id) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("UNAUTHORIZED", "Authentication is required", new ArrayList<>()));
        if (!RoleValidator.hasAccess(user.getRole(), "APPROVER")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("FORBIDDEN", "You do not have permission to access this resource", new ArrayList<>()));

        LoanApplicationResponse response = loanService.updateStatus(id, "APPROVED");
        if (response == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("NOT_FOUND", "Data not found", new ArrayList<>()));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<?> reject(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                    @PathVariable Long id) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("UNAUTHORIZED", "Authentication is required", new ArrayList<>()));
        if (!RoleValidator.hasAccess(user.getRole(), "APPROVER")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("FORBIDDEN", "You do not have permission to access this resource", new ArrayList<>()));

        LoanApplicationResponse response = loanService.updateStatus(id, "REJECTED");
        if (response == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("NOT_FOUND", "Data not found", new ArrayList<>()));
        return ResponseEntity.ok(response);
    }
}