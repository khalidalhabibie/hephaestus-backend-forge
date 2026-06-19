package com.example.training.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.helper.ValidateTokenRoleHelper;
import com.example.training.model.LoanStatus;
import com.example.training.model.Role;
import com.example.training.service.AuthService;
import com.example.training.service.CustomerService;
import com.example.training.service.LoanApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Creates loan application.", description = "Creates new loan application.")
    @ApiResponse(responseCode = "200", description = "Loan application created successfully.")
    @ApiResponse(responseCode = "400", description = "Bad Request.")
    @ApiResponse(responseCode = "401", description = "Unauthorized.")
    @ApiResponse(responseCode = "403", description = "Forbidden.")
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(@Valid @RequestBody CreateLoanApplicationRequest request, HttpServletRequest requestContext) {
        
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF);

        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Shows loan applications.", description = "Displays all loan applications data.")
    @ApiResponse(responseCode = "200", description = "Loan applications data retrieved successfully.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<List<LoanApplicationResponse>> getLoanApplication(@RequestParam(required = false) LoanStatus status, @RequestParam(required = false) Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF, Role.APPROVER);
        
        List<LoanApplicationResponse> responses;
        if(status != null){
            responses = loanApplicationService.searchSubmittedApplication(status);
            return ResponseEntity.ok().body(responses);
        } else if(id != null) {
            responses = loanApplicationService.searchCustomerId(id);
            return ResponseEntity.ok().body(responses);
        }
        return ResponseEntity.ok(loanApplicationService.getLoanApplication());
    }
    
    @GetMapping("/{uuid}")
    @Operation(summary = "Shows loan application by ID.", description = "Displays loan application data based on ID.")
    @ApiResponse(responseCode = "200", description = "Loan application data retrieved successfully.")
    @ApiResponse(responseCode = "404", description = "Loan application not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable UUID uuid, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF, Role.APPROVER);
        
        List<LoanApplicationResponse> responses;
        return ResponseEntity.ok(loanApplicationService.getLoanApplicationById(uuid));
    }

    @PatchMapping("/{uuid}/approve")
    @Operation(summary = "Approves loan application.", description = "Updates loan applications status to APPROVED.")
    @ApiResponse(responseCode = "200", description = "Loan application data updated to APPROVED successfully.")
    @ApiResponse(responseCode = "403", description = "Forbidden.")
    @ApiResponse(responseCode = "404", description = "Loan application not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<LoanApplicationResponse> approveLoanApplication(@PathVariable UUID uuid, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER);

        return ResponseEntity.ok(loanApplicationService.approveLoanApplication(uuid));
    }

    @PatchMapping("/{uuid}/reject")
    @Operation(summary = "Rejects loan application.", description = "Updates loan applications status to REJECTED.")
    @ApiResponse(responseCode = "200", description = "Loan application data updated to REJECTED successfully.")
    @ApiResponse(responseCode = "403", description = "Forbidden.")
    @ApiResponse(responseCode = "404", description = "Loan application not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<LoanApplicationResponse> rejectLoanApplication(@PathVariable UUID uuid, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER);

        return ResponseEntity.ok(loanApplicationService.rejectLoanApplication(uuid));
    }

    @PatchMapping("/{uuid}/cancel")
    @Operation(summary = "Cancels loan application.", description = "Updates loan applications status to CANCELLED.")
    @ApiResponse(responseCode = "200", description = "Loan application data updated to CANCELLED successfully.")
    @ApiResponse(responseCode = "403", description = "Forbidden.")
    @ApiResponse(responseCode = "404", description = "Loan application not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<LoanApplicationResponse> cancelLoanApplication(@PathVariable UUID uuid, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER);

        return ResponseEntity.ok(loanApplicationService.cancelLoanApplication(uuid));
    }
}
