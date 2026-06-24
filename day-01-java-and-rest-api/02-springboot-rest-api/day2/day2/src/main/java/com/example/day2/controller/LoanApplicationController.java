package com.example.day2.controller;

import com.example.day2.dto.*;
import com.example.day2.model.User;
import com.example.day2.security.AuthUtil;
import com.example.day2.security.RoleValidator;
import com.example.day2.service.LoanApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService loanService;

    public LoanApplicationController(LoanApplicationService loanService) { this.loanService = loanService; }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                    @RequestBody CreateLoanApplicationRequest request) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
        if (!RoleValidator.hasAccess(user.getRole(), "STAFF")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden"));

        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(request));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
        if (!RoleValidator.hasAccess(user.getRole(), "STAFF", "APPROVER")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden"));

        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable String id) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
        if (!RoleValidator.hasAccess(user.getRole(), "STAFF", "APPROVER")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden"));

        LoanApplicationResponse response = loanService.getLoanById(id);
        if (response == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Loan application not found"));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approve(@RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable String id) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
        if (!RoleValidator.hasAccess(user.getRole(), "APPROVER")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden"));

        LoanApplicationResponse response = loanService.updateStatus(id, "APPROVED");
        if (response == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Loan application not found"));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<?> reject(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                    @PathVariable String id) {
        User user = AuthUtil.validateToken(authHeader);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
        if (!RoleValidator.hasAccess(user.getRole(), "APPROVER")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden"));

        LoanApplicationResponse response = loanService.updateStatus(id, "REJECTED");
        if (response == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Loan application not found"));
        return ResponseEntity.ok(response);
    }
}
