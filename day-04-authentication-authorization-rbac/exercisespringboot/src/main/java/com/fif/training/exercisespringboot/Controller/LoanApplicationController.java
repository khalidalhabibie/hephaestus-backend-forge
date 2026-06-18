package com.fif.training.exercisespringboot.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fif.training.exercisespringboot.DTO.ApiResponseDto;
import com.fif.training.exercisespringboot.DTO.Loan.LoanApplicationRequest;
import com.fif.training.exercisespringboot.DTO.Loan.LoanApplicationResponse;
import com.fif.training.exercisespringboot.Model.Roles;
import com.fif.training.exercisespringboot.Security.AuthContext;
import com.fif.training.exercisespringboot.Security.AuthUtil;
import com.fif.training.exercisespringboot.Security.RoleValidator;
import com.fif.training.exercisespringboot.Service.LoanApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Loan Application API", description = "API for managing loan applications")
@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;
    private final AuthUtil authUtil;
    private final RoleValidator roleValidator;

    public LoanApplicationController(
            LoanApplicationService loanApplicationService,
            AuthUtil authUtil,
            RoleValidator roleValidator) {
        this.loanApplicationService = loanApplicationService;
        this.authUtil = authUtil;
        this.roleValidator = roleValidator;
    }

    // POST LOAN APPLICATION
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create loan application", description = "Create a new loan application")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<LoanApplicationResponse> createLoanApplication(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody LoanApplicationRequest request) {

        AuthContext authContext = authUtil.getAuthContext(authorization);

        roleValidator.requireAnyRole(authContext, Roles.STAFF, Roles.ADMIN);

        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
        return new ApiResponseDto<>("Loan Application Created!", response);
    }

    // GET ALL LOANS
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LoanApplicationResponse> getAllLoanApplications(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization) {

        AuthContext authContext = authUtil.getAuthContext(authorization);
        roleValidator.requireAnyRole(authContext, Roles.ADMIN, Roles.STAFF, Roles.APPROVER);

        return loanApplicationService.getAllLoanApplications();
    }

    // GET Loan By Id
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanApplicationResponse getLoanApplicationById(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {

        AuthContext authContext = authUtil.getAuthContext(authorization);
        roleValidator.requireAnyRole(authContext, Roles.ADMIN, Roles.STAFF, Roles.APPROVER);

        return loanApplicationService.getLoanApplicationById(id);
    }

    // PATCH Approve Loan By Id
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/approve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<LoanApplicationResponse> approveLoanApplication(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {

        AuthContext authContext = authUtil.getAuthContext(authorization);
        roleValidator.requireAnyRole(authContext, Roles.ADMIN, Roles.APPROVER);

        LoanApplicationResponse response = loanApplicationService.approveLoanApplication(
                id,
                authContext.getUsername());

        return new ApiResponseDto<>("Loan Application Approved!", response);
    }

    // PATCH Reject Loan By Id
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/reject/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<LoanApplicationResponse> rejectLoanApplication(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {

        AuthContext authContext = authUtil.getAuthContext(authorization);
        roleValidator.requireAnyRole(authContext, Roles.ADMIN, Roles.APPROVER);

        LoanApplicationResponse response = loanApplicationService.rejectLoanApplication(
                id,
                authContext.getUsername());

        return new ApiResponseDto<>("Loan Application Rejected!", response);
    }
}