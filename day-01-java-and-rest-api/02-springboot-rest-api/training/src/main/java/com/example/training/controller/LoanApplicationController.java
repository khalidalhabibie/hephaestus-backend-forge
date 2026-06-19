package com.example.training.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.helper.ValidateTokenRoleHelper;
import com.example.training.model.Role;
import com.example.training.service.AuthService;
import com.example.training.service.CustomerService;
import com.example.training.service.LoanApplicationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;
    private final AuthService authService; 
    
    public LoanApplicationController(LoanApplicationService loanAppService, AuthService authService) {
        this.loanApplicationService = loanAppService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(@Valid @RequestBody CreateLoanApplicationRequest request, HttpServletRequest requestContext) {
        
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF);

        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanApplicationResponse>> getLoanApplication(HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF, Role.APPROVER);
        
        List<LoanApplicationResponse> responses;
        return ResponseEntity.ok(loanApplicationService.getLoanApplication());
    }
    
    @GetMapping("/{uuid}")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable UUID uuid, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF, Role.APPROVER);
        
        List<LoanApplicationResponse> responses;
        return ResponseEntity.ok(loanApplicationService.getLoanApplicationById(uuid));
    }

    @PatchMapping("/{uuid}/approve")
    public ResponseEntity<LoanApplicationResponse> approveLoanApplication(@PathVariable UUID uuid, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER);

        return ResponseEntity.ok(loanApplicationService.approveLoanApplication(uuid));
    }

    @PatchMapping("/{uuid}/reject")
    public ResponseEntity<LoanApplicationResponse> rejectLoanApplication(@PathVariable UUID uuid, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER);

        return ResponseEntity.ok(loanApplicationService.rejectLoanApplication(uuid));
    }
}
