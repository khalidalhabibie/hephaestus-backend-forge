package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.RepaymentScheduleResponse;
import com.example.demoSpringbootDatabase.service.RepaymentScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RepaymentScheduleController {
    private final RepaymentScheduleService scheduleService;
    public RepaymentScheduleController(RepaymentScheduleService scheduleService) { this.scheduleService = scheduleService; }

    @GetMapping("/loan-applications/{loanApplicationId}/repayment-schedules")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponse>>> getByLoan(@PathVariable Long loanApplicationId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Repayment schedules retrieved successfully", scheduleService.getByLoanId(loanApplicationId)));
    }

    @GetMapping("/repayment-schedules/{id}")
    public ResponseEntity<ApiResponse<RepaymentScheduleResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Repayment schedule retrieved successfully", scheduleService.getById(id)));
    }
}
