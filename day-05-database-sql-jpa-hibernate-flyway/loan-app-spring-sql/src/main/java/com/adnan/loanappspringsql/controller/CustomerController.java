package com.adnan.loanappspringsql.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.adnan.loanappspringsql.dto.CreateCustomerRequest;
import com.adnan.loanappspringsql.dto.CustomerResponse;
import com.adnan.loanappspringsql.dto.LoanApplicationResponse;
import com.adnan.loanappspringsql.dto.api.ApiResponse;
import com.adnan.loanappspringsql.service.CustomerService;
import com.adnan.loanappspringsql.service.LoanApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {
        private final CustomerService customerService;
        private final LoanApplicationService loanApplicationService;

        @PostMapping
        public ResponseEntity<ApiResponse<CustomerResponse>> create(
                        @Valid @RequestBody CreateCustomerRequest request) {
                CustomerResponse response = customerService.create(request);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(ApiResponse.success(
                                                "Customer created successfully",
                                                response));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<CustomerResponse>> findById(
                        @PathVariable Long id) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Customer retrieved successfully",
                                                customerService.findById(id)));
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<CustomerResponse>>> findAll() {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Customers retrieved successfully",
                                                customerService.findAll()));
        }

        @GetMapping("/search")
        public ResponseEntity<ApiResponse<List<CustomerResponse>>> search(
                        @RequestParam String name) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Customers retrieved successfully",
                                                customerService.search(name)));
        }

        @GetMapping("/{customerId}/loan-applications")
        public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> findByCustomerId(
                        @PathVariable Long customerId) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Loan applications retrieved successfully",
                                                loanApplicationService.findByCustomerId(customerId)));
        }
}