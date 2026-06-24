// Handle endpoint repayment: GET by loan ID, GET by ID, filter by status.

package com.example.training.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.DTO.ApiResponse;
import com.example.training.DTO.RepaymentScheduleResponse;
import com.example.training.Service.RepaymentScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;

    // ========== Filter Repayment Schedule Berdasarkan Status PAID / UNPAID (START) ========== //
    @GetMapping("/loan-applications/{loanApplicationId}/repayment-schedules")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponse>>> getByLoanApplicationId(
            @PathVariable Long loanApplicationId,
            @RequestParam(required = false) String status
    ) {
        List<RepaymentScheduleResponse> list;
        if (status != null && !status.isBlank()) {
            list = repaymentScheduleService.getByLoanIdAndStatus(loanApplicationId, status);
        } else {
            list = repaymentScheduleService.getByLoanApplicationId(loanApplicationId);
        }
        return ResponseEntity.ok(ApiResponse.success("Repayment schedules retrieved successfully", list));
    }
    // ========== Filter Repayment Schedule Berdasarkan Status PAID / UNPAID (END) ========== //

    @GetMapping("/repayment-schedules/{id}")
    public ResponseEntity<ApiResponse<RepaymentScheduleResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Repayment schedule retrieved successfully",
                repaymentScheduleService.getById(id)));
    }
}