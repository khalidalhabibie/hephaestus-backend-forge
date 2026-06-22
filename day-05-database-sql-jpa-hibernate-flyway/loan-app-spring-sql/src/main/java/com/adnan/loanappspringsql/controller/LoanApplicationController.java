package com.adnan.loanappspringsql.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adnan.loanappspringsql.dto.CreateLoanApplicationRequest;
import com.adnan.loanappspringsql.dto.LoanApplicationResponse;
import com.adnan.loanappspringsql.dto.RepaymentScheduleResponse;
import com.adnan.loanappspringsql.dto.UpdateLoanStatusRequest;
import com.adnan.loanappspringsql.dto.api.ApiResponse;
import com.adnan.loanappspringsql.enums.LoanStatusEnum;
import com.adnan.loanappspringsql.service.LoanApplicationService;
import com.adnan.loanappspringsql.service.RepaymentScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {
        private final LoanApplicationService loanApplicationService;
        private final RepaymentScheduleService repaymentScheduleService;

        @PostMapping
        public ResponseEntity<ApiResponse<LoanApplicationResponse>> create(
                        @Valid @RequestBody CreateLoanApplicationRequest request) {
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ApiResponse.success(
                                                "Loan application created successfully",
                                                loanApplicationService.create(request)));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<LoanApplicationResponse>> findById(
                        @PathVariable Long id) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Loan application retrieved successfully",
                                                loanApplicationService.findById(id)));
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> findAll(
                        @RequestParam(required = false) LoanStatusEnum status) {
                if (status != null) {
                        return ResponseEntity.ok(
                                        ApiResponse.success(
                                                        "Loan applications retrieved successfully",
                                                        loanApplicationService.findByStatus(status)));
                }

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Loan applications retrieved successfully",
                                                loanApplicationService.findAll()));
        }

        @PatchMapping("/{id}/status")
        public ResponseEntity<ApiResponse<LoanApplicationResponse>> updateStatus(
                        @PathVariable Long id,
                        @Valid @RequestBody UpdateLoanStatusRequest request) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Loan status updated successfully",
                                                loanApplicationService.updateStatus(id, request)));
        }

        @GetMapping("/{loanApplicationId}/repayment-schedules")
        public ResponseEntity<ApiResponse<List<RepaymentScheduleResponse>>> findByLoanApplicationId(
                        @PathVariable Long loanApplicationId) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Repayment schedules retrieved successfully",
                                                repaymentScheduleService
                                                                .findByLoanApplicationId(
                                                                                loanApplicationId)));
        }
}
