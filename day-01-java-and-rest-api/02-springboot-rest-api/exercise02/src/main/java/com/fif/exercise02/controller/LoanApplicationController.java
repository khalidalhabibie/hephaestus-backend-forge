package com.fif.exercise02.controller;


import com.fif.exercise02.dto.*;
import com.fif.exercise02.security.*;
import com.fif.exercise02.service.LoanApplicationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService service = new LoanApplicationService();

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader("Authorization") String header,
            @RequestBody CreateLoanApplicationRequest request) {
    
        var ctx = getAuth(header);
        if (ctx == null)
            return unauthorized();
    
        if (!RoleValidator.isAllowed(ctx.getRole(), "ADMIN", "STAFF")) {
            return forbidden();
        }
    
        return ResponseEntity.status(201)
                .body(ErrorResponse.success(service.create(request)));
    }
    

    // =========================
    // GET ALL
    // =========================
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader("Authorization") String header,
            @RequestParam(required = false) String status,
            @RequestParam(name = "customer_id", required = false) String customerId) {

        var ctx = getAuth(header);
        if (ctx == null)
            return unauthorized();

        var data = service.getAllFiltered(status, customerId);

        return ResponseEntity.ok(ErrorResponse.success(data));
    }

    // =========================
    // GET BY ID
    // =========================
    @GetMapping("/LOAN-{id}")
    public ResponseEntity<?> getById(
            @RequestHeader("Authorization") String header,
            @PathVariable String id) {

        var ctx = getAuth(header);
        if (ctx == null)
            return unauthorized();

        var loan = service.getById(id);
        if (loan == null)
            return notFound();

        return ResponseEntity.ok(
            ErrorResponse.success(loan)
    );
    }

    // =========================
    // APPROVE
    // =========================
    @PatchMapping("/LOAN-{id}/approve")
    public ResponseEntity<?> approve(
            @RequestHeader("Authorization") String header,
            @PathVariable String id) {

        var ctx = getAuth(header);
        if (ctx == null)
            return unauthorized();

        if (!RoleValidator.isAllowed(ctx.getRole(), "ADMIN", "APPROVER")) {
            return forbidden();
        }

        var loan = service.updateStatus(id, "APPROVED");
        if (loan == null)
            return notFound();

        return ResponseEntity.ok(
                ErrorResponse.success(loan)  );
    }

    // =========================
    // REJECT
    // =========================
    @PatchMapping("/LOAN-{id}/reject")
    public ResponseEntity<?> reject(
            @RequestHeader("Authorization") String header,
            @PathVariable String id) {

        var ctx = getAuth(header);
        if (ctx == null)
            return unauthorized();

        if (!RoleValidator.isAllowed(ctx.getRole(), "ADMIN", "APPROVER")) {
            return forbidden();
        }

        var loan = service.updateStatus(id, "REJECTED");
        if (loan == null)
            return notFound();

        return ResponseEntity.ok(loan);
    }

    // =========================
    // HELPERS
    // =========================

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



    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(
        @RequestHeader("Authorization") String header,
        @PathVariable String id) {

    var ctx = getAuth(header);
    if (ctx == null) return unauthorized();

    if (!RoleValidator.isAllowed(ctx.getRole(), "ADMIN", "STAFF")) {
        return forbidden();
    }

    var loan = service.cancel(id);
    if (loan == null) return notFound();

    return ResponseEntity.ok(ErrorResponse.success(loan));
}

}
