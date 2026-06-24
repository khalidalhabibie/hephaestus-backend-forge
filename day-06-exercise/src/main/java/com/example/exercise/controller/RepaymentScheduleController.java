package com.example.exercise.controller;

import com.example.exercise.repository.RepaymentScheduleRepository;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exercise.dto.CustomerResponse;
import com.example.exercise.dto.PaymentTransactionResponse;
import com.example.exercise.dto.RepaymentScheduleResponse;
import com.example.exercise.enums.Role;
import com.example.exercise.helper.ValidateTokenRoleHelper;
import com.example.exercise.service.AuthService;
import com.example.exercise.service.CustomerService;
import com.example.exercise.service.LoanApplicationService;
import com.example.exercise.service.RepaymentScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/repayment-schedules")
@Tag(name = "RepaymentScheduleController", description = "API for repayment schedule.")
public class RepaymentScheduleController {
    private final RepaymentScheduleService repaymentScheduleService;
    private final AuthService authService;
    
    public RepaymentScheduleController(RepaymentScheduleService repaymentScheduleService, AuthService authService) {
        this.repaymentScheduleService = repaymentScheduleService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get repayment schedule by id.", description = "Shows repayment schedule based on id.")
    @ApiResponse(responseCode = "200", description = "Customer data retrieved successfully.")
    @ApiResponse(responseCode = "404", description = "Customer not found.")
    public ResponseEntity<RepaymentScheduleResponse> getRepaymentScheduleById (@PathVariable Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER, Role.STAFF);

        return ResponseEntity.ok(repaymentScheduleService.getRepaymentScheduleById(id));
    }

    @GetMapping("/{id}/payment-transactions")
    @Operation(summary = "Get payment transactions by repayment schedule id.", description = "Shows payment transactions based on id.")
    @ApiResponse(responseCode = "200", description = "Customer data retrieved successfully.")
    @ApiResponse(responseCode = "404", description = "Customer not found.")
    public ResponseEntity<List<PaymentTransactionResponse>> getPaymentTransactionByScheduleId (@PathVariable Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER, Role.STAFF);

        return ResponseEntity.ok(repaymentScheduleService.getScheduleIdPaymentTransaction(id));
    }
}
