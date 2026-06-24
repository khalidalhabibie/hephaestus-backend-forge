package com.example.jpabackend.controller;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.service.RepaymentScheduleService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@Tag(name = "Repayment Schedule API", description = "API untuk mengelola repayment schedule")
@RestController
@RequestMapping("/api/v1")
public class RepaymentScheduleController {

    private final RepaymentScheduleService service;

    public RepaymentScheduleController(RepaymentScheduleService service) {
        this.service = service;
    }

    // GET REPAYMENT BY LOAN ID
    @GetMapping("/loan-applications/{loanId}/repayment-schedules")
    @Operation(summary = "Get repayment schedules by loan application ID")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponse>>> getByLoanId(
            @PathVariable Long loanId) {

        List<RepaymentScheduleResponse> response = service.getByLoanId(loanId);

        return ResponseEntity.ok(
                ApiResponse.success("Repayment schedules retrieved successfully", response));
    }

    // GET REPAYMENT BY ID
    @GetMapping("/repayment-schedules/{id}")
    @Operation(summary = "Get repayment schedule by ID")
    public ResponseEntity<ApiResponse<RepaymentScheduleResponse>> getById(
            @PathVariable Long id) {

        RepaymentScheduleResponse response = service.getById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Repayment schedule retrieved successfully", response));
    }

    //FILTER REPAYMENT BY STATUS
    @GetMapping("/repayment-schedules")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponse>>> getByStatus(
            @RequestParam(required = false) String status) {

        if (status != null) {
            return ResponseEntity.ok(
                    ApiResponse.success("Filtered schedules",
                            service.getByStatus(status)));
        }

        return ResponseEntity.ok(
                ApiResponse.success("All schedules",
                        service.getAll()));
    }

    //GET SUMMARY
    @GetMapping("/customers/outstanding")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> outstanding() {

        return ResponseEntity.ok(
                ApiResponse.success("Outstanding retrieved",
                        service.getOutstandingPerCustomer()));
    }

}