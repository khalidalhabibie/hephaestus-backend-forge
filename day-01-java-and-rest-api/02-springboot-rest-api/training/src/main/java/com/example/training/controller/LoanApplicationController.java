package com.example.training.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.exception.ForbiddenException;
import com.example.training.model.LoanApplication;
import com.example.training.model.User;
import com.example.training.security.AuthUtil;
import com.example.training.security.RoleValidator;
import com.example.training.service.AuthService;
import com.example.training.service.LoanApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/v1/loan-applications")
@Validated
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;
    private final AuthService authService;
    
    public LoanApplicationController(
        LoanApplicationService loanApplicationService,
        AuthService authService) {
            this.loanApplicationService = loanApplicationService;
            this.authService = authService;
    }
    
    private User authenticate(String authorization) {
        String token = AuthUtil.extractToken(authorization);
        return authService.findByToken(token);
    }

    private LoanApplicationResponse toResponse(LoanApplication loan) {
        return LoanApplicationResponse.builder()
            .id(loan.getId())
            .customerId(loan.getCustomerId())
            .loanAmount(loan.getLoanAmount())
            .tenorMonth(loan.getTenorMonth())
            .purpose(loan.getPurpose())
            .status(loan.getStatus())
            .createdAt(loan.getCreatedAt())
            .updatedAt(loan.getUpdatedAt())
            .build();
    }

    @PostMapping
    @Operation(summary = "Create loan application")
    @ApiResponse(responseCode = "201", description = "Loan application created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody CreateLoanApplicationRequest request) {
        User user = authenticate(authorization);
        RoleValidator.validate(user, "ADMIN", "STAFF");
        LoanApplication loan = loanApplicationService.createLoanApplication(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(loan));
    }

    @GetMapping
    @Operation(summary = "Get all loan applications")
    @ApiResponse(responseCode = "200", description = "Loan application list returned")
    public ResponseEntity<List<LoanApplicationResponse>> getAll(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false) String status,
            @RequestParam(name = "customer_id", required = false) Long customerId) {
        User user = authenticate(authorization);
        RoleValidator.validate(user, "ADMIN", "STAFF", "APPROVER", "MANAGER");
        List<LoanApplication> loans;
        if (status != null) {
            loans = loanApplicationService.getLoanApplicationByStatus(status);
        } else if (customerId != null) {
            loans = loanApplicationService.getLoanApplicationByCustomerId(customerId);
        } else {
            loans = loanApplicationService.getAll();
        }
        List<LoanApplicationResponse> response = loans.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan application by id")
    @ApiResponse(responseCode = "200", description = "Loan application found")
    @ApiResponse(responseCode = "404", description = "Loan application not found")
    public ResponseEntity<LoanApplicationResponse> geLoanApplicationtById(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        User user = authenticate(authorization);
        RoleValidator.validate(user, "ADMIN", "STAFF", "APPROVER", "MANAGER");
        LoanApplication loan = loanApplicationService.getLoanApplicationById(id);
        return ResponseEntity.ok(toResponse(loan));
    }
    
    @PatchMapping("/{id}/approve")
    @Operation(summary = "Approve loan application")
    public ResponseEntity<LoanApplicationResponse> approve(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        User user = authenticate(authorization);
        LoanApplication loan = loanApplicationService.getLoanApplicationById(id);
        if ("MANAGER".equalsIgnoreCase(user.getRole())) {
            if (loan.getLoanAmount() < 10000000L) {
                throw new ForbiddenException("Manager can only approve loans above 10000000");
            }
        } else {
            RoleValidator.validate(user, "ADMIN", "APPROVER");
        }
        loan = loanApplicationService.approve(id);
        return ResponseEntity.ok(toResponse(loan));
    }

    @PatchMapping("/{id}/reject")
    @Operation(summary = "Reject loan application")
    public ResponseEntity<LoanApplicationResponse> reject(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        User user = authenticate(authorization);
        RoleValidator.validate(user, "ADMIN", "APPROVER");
        LoanApplication loan = loanApplicationService.reject(id);
        return ResponseEntity.ok(toResponse(loan));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel loan application")
    public ResponseEntity<LoanApplicationResponse> cancel(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        User user = authenticate(authorization);
        RoleValidator.validate(user, "ADMIN", "STAFF");
        LoanApplication loan = loanApplicationService.cancel(id);
        return ResponseEntity.ok(toResponse(loan));
    }
}