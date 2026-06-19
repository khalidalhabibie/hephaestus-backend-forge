package com.fif.exercise02.controller;

import com.fif.exercise02.entity.Role;
import com.fif.exercise02.entity.LoanStatus;
import com.fif.exercise02.dto.*;
import com.fif.exercise02.security.*;
import com.fif.exercise02.service.LoanApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Loan Application API", description = "API untuk mengelola loan application")
@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService service = new LoanApplicationService();

    // CREATE
    @PostMapping
    @Operation(summary = "Create loan application")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<?> create(
            @RequestHeader("Authorization") String header,
            @RequestBody CreateLoanApplicationRequest request) {

        var ctx = getAuth(header);
        if (ctx == null) return unauthorized();

        if (!RoleValidator.isAllowed(ctx.getRole(), Role.ADMIN, Role.STAFF)) {
            return forbidden();
        }

        return ResponseEntity.status(201)
                .body(ErrorResponse.success(service.create(request)));
    }


    // GET ALL
    @GetMapping
    @Operation(summary = "Get all loan applications")
    public ResponseEntity<?> getAll(
            @RequestHeader("Authorization") String header,
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(name = "customer_id", required = false) String customerId) {

        var ctx = getAuth(header);
        if (ctx == null) return unauthorized();

        var data = service.getAllFiltered(status, customerId);
        return ResponseEntity.ok(ErrorResponse.success(data));
    }


    // GET BY ID
    @GetMapping("/LOAN-{id}")
    @Operation(summary = "Get loan by ID")
    public ResponseEntity<?> getById(
            @RequestHeader("Authorization") String header,
            @PathVariable String id) {

        var ctx = getAuth(header);
        if (ctx == null) return unauthorized();

        var loan = service.getById(id);
        if (loan == null) return notFound();

        return ResponseEntity.ok(ErrorResponse.success(loan));
    }


    // APPROVE
    @PatchMapping("/LOAN-{id}/approve")
    @Operation(summary = "Approve loan")
    public ResponseEntity<?> approve(
            @RequestHeader("Authorization") String header,
            @PathVariable String id) {

        var ctx = getAuth(header);
        if (ctx == null) return unauthorized();

        var loan = service.getEntity("LOAN-" + id);
        if (loan == null) return notFound();

        Role role = ctx.getRole();

        // ADMIN & APPROVER bebas
        if (role == Role.ADMIN || role == Role.APPROVER) {
            return approveSuccess("LOAN-" + id);
        }

        // MANAGER rule
        if (role == Role.MANAGER) {
            if (loan.getAmount() != null && loan.getAmount() > 5_000_000) {
                return approveSuccess("LOAN-" + id);
            }
            return ResponseEntity.status(403)
                    .body(ErrorResponse.error(
                        "403",
                        "MANAGER_CAN_ONLY_APPROVE_HIGH_AMOUNT",
                        null));
        }

        return forbidden();
    }


    // REJECT
    @PatchMapping("/LOAN-{id}/reject")
    @Operation(summary = "Reject loan")
    public ResponseEntity<?> reject(
            @RequestHeader("Authorization") String header,
            @PathVariable String id) {

        var ctx = getAuth(header);
        if (ctx == null) return unauthorized();

        if (!RoleValidator.isAllowed(ctx.getRole(), Role.ADMIN, Role.APPROVER)) {
            return forbidden();
        }

        var loan = service.updateStatus(id, LoanStatus.REJECTED);
        if (loan == null) return notFound();

        return ResponseEntity.ok(ErrorResponse.success(loan));
    }


    // CANCEL
    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel loan")
    public ResponseEntity<?> cancel(
        @RequestHeader("Authorization") String header,
        @PathVariable String id) {

        var ctx = getAuth(header);
        if (ctx == null) return unauthorized();

        if (!RoleValidator.isAllowed(ctx.getRole(), Role.ADMIN, Role.STAFF)) {
            return forbidden();
        }

        var loan = service.cancel(id);
        if (loan == null) return notFound();

        return ResponseEntity.ok(ErrorResponse.success(loan));
    }


    // HELPERS

    private AuthContext getAuth(String header) {
        return AuthUtil.parseToken(AuthUtil.extractToken(header));
    }

    private ResponseEntity<?> unauthorized() {
        return ResponseEntity.status(401)
                .body(ErrorResponse.error("401", "UNAUTHORIZED", null));
    }

    private ResponseEntity<?> forbidden() {
        return ResponseEntity.status(403)
                .body(ErrorResponse.error("403", "FORBIDDEN", null));
    }

    private ResponseEntity<?> notFound() {
        return ResponseEntity.status(404)
                .body(ErrorResponse.error("404", "NOT_FOUND", null));
    }

    private ResponseEntity<?> approveSuccess(String id) {
        var loan = service.updateStatus(id, LoanStatus.APPROVED);
        return ResponseEntity.ok(ErrorResponse.success(loan));
    }
}