package com.adnan.loanappspringsql.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adnan.loanappspringsql.dto.PaymentTransactionResponse;
import com.adnan.loanappspringsql.dto.RepaymentScheduleResponse;
import com.adnan.loanappspringsql.dto.api.ApiResponse;
import com.adnan.loanappspringsql.service.PaymentTransactionService;
import com.adnan.loanappspringsql.service.RepaymentScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/repayment-schedules")
@RequiredArgsConstructor
public class RepaymentScheduleController {
        private final RepaymentScheduleService repaymentScheduleService;
        private final PaymentTransactionService paymentTransactionService;

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<RepaymentScheduleResponse>> findById(@PathVariable Long id) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Repayment schedule retrieved successfully",
                                                repaymentScheduleService
                                                                .findById(id)));
        }

        @GetMapping("/{repaymentScheduleId}/payment-transactions")
        public ResponseEntity<ApiResponse<List<PaymentTransactionResponse>>> findByRepaymentScheduleId(
                        @PathVariable Long repaymentScheduleId) {

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Payment transactions retrieved successfully",
                                                paymentTransactionService.findByRepaymentScheduleId(
                                                                repaymentScheduleId)));
        }
}
