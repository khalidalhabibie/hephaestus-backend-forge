package com.fif.training.exercisespringboot.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fif.training.exercisespringboot.DTO.CreateLoanApplicationRequest;
import com.fif.training.exercisespringboot.DTO.LoanApplicationResponse;
import com.fif.training.exercisespringboot.Model.User;
import com.fif.training.exercisespringboot.Security.AuthContext;
import com.fif.training.exercisespringboot.Security.AuthUtil;
import com.fif.training.exercisespringboot.Security.RoleValidator;
import com.fif.training.exercisespringboot.Service.LoanApplicationService;
import com.fif.training.exercisespringboot.exception.ForbiddenException;
import com.fif.training.exercisespringboot.exception.UnauthorizedException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/loan-applications")
@SecurityRequirement(name = "bearerAuth")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    private User validateToken(HttpServletRequest request) {
        String token = AuthUtil.getToken(request);
        User user = AuthContext.getUserByToken(token);
        if (user == null) {
            throw new UnauthorizedException("Authentication is required");
        }
        return user;
    }

    private void checkRole(User user, String... allowedRoles) {
        if (!RoleValidator.allow(user.getRole(), allowedRoles)) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
    }

    @PostMapping
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(
            @Valid @RequestBody CreateLoanApplicationRequest request,
            HttpServletRequest httpRequest) {
        User user = validateToken(httpRequest);
        checkRole(user, "ADMIN", "STAFF");
        LoanApplicationResponse response = loanApplicationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanApplicationResponse>> getAllLoanApplications(HttpServletRequest request) {
        validateToken(request);
        return ResponseEntity.ok(loanApplicationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(
            @PathVariable Long id,
            HttpServletRequest request) {
        validateToken(request);
        LoanApplicationResponse response = loanApplicationService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<LoanApplicationResponse> approveLoanApplication(
            @PathVariable Long id,
            HttpServletRequest request) {
        User user = validateToken(request);
        checkRole(user, "ADMIN", "APPROVER");
        LoanApplicationResponse response = loanApplicationService.approve(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<LoanApplicationResponse> rejectLoanApplication(
            @PathVariable Long id,
            HttpServletRequest request) {
        User user = validateToken(request);
        checkRole(user, "ADMIN", "APPROVER");
        LoanApplicationResponse response = loanApplicationService.reject(id);
        return ResponseEntity.ok(response);
    }
}