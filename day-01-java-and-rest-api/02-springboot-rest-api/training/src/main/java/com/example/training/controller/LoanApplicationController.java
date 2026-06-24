package com.example.training.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.dto.WebResponse;
import com.example.training.model.LoanApplication;
import com.example.training.security.AuthHeaderUtil;
import com.example.training.security.RoleValidator;
import com.example.training.service.AuthService;
import com.example.training.service.LoanApplicationService;
import com.example.training.user.entity.Role;
import com.example.training.user.entity.User;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {
        private final LoanApplicationService loanService;
        private final AuthService authService;

        public LoanApplicationController(
                        LoanApplicationService loanService,
                        AuthService authService) {
                this.loanService = loanService;
                this.authService = authService;
        }

        @PostMapping
        public ResponseEntity<WebResponse<LoanApplicationResponse>> create(
                        @RequestHeader("Authorization") String authorization,
                        @Valid @RequestBody CreateLoanApplicationRequest entity) {
                String token = AuthHeaderUtil.extractToken(authorization);
                User user = authService.validateToken(token);

                RoleValidator.validate(user, Role.ADMIN, Role.STAFF);
                LoanApplicationResponse loanResponse = loanService.create(entity);

                return ResponseEntity.ok().body(WebResponse.success("Berhasil mendapatkan data", loanResponse));
        }

        @GetMapping
        public ResponseEntity<WebResponse<List<LoanApplicationResponse>>> findAll(
                        @RequestHeader("Authorization") String authorization,
                        @RequestParam(required = false) String status,
                        @RequestParam(required = false) UUID customerUuid) {
                String token = AuthHeaderUtil.extractToken(authorization);
                authService.validateToken(token);

                List<LoanApplicationResponse> responses;
                responses = loanService.findAll(status, customerUuid);

                return ResponseEntity.ok().body(WebResponse.success("Berhasil mendapatkan data", responses));
        }

        @GetMapping("/{id}")
        public LoanApplicationResponse findById(@PathVariable UUID id,
                        @RequestHeader("Authorization") String authorization) {
                String token = AuthHeaderUtil.extractToken(authorization);
                authService.validateToken(token);

                return toResponse(loanService.findById(id));
        }

        @PostMapping("/{id}/approve")
        public ResponseEntity<WebResponse<LoanApplicationResponse>> approve(
                        @PathVariable UUID id,
                        @RequestHeader("Authorization") String authorization) {

                String token = AuthHeaderUtil.extractToken(
                                authorization);

                User user = authService.validateToken(token);

                RoleValidator.validate(
                                user,
                                Role.ADMIN,
                                Role.APPROVER);

                return ResponseEntity.ok()
                                .body(WebResponse.success("Berhasil approve data", loanService.approve(id)));
        }

        @PostMapping("/{id}/reject")
        public ResponseEntity<WebResponse<LoanApplicationResponse>> reject(
                        @PathVariable UUID id,
                        @RequestHeader("Authorization") String authorization) {

                String token = AuthHeaderUtil.extractToken(
                                authorization);

                User user = authService.validateToken(token);

                RoleValidator.validate(
                                user,
                                Role.ADMIN,
                                Role.APPROVER);

                return ResponseEntity.ok()
                                .body(WebResponse.success("Berhasil reject data", loanService.reject(id)));
        }

        @PostMapping("/{id}/cancel")
        public ResponseEntity<WebResponse<LoanApplicationResponse>> cancel(
                        @PathVariable UUID id,
                        @RequestHeader("Authorization") String authorization) {

                String token = AuthHeaderUtil.extractToken(
                                authorization);

                User user = authService.validateToken(token);

                RoleValidator.validate(
                                user,
                                Role.ADMIN,
                                Role.APPROVER);

                return ResponseEntity.ok()
                                .body(WebResponse.success("Berhasil cancel data", loanService.cancel(id)));
        }

        public LoanApplicationResponse toResponse(LoanApplication loan) {
                return new LoanApplicationResponse(loan.getId(), loan.getCustomerId(), loan.getLoanAmount(),
                                loan.getTenorMonth(), loan.getPurpose(), loan.getStatus());
        }

}
