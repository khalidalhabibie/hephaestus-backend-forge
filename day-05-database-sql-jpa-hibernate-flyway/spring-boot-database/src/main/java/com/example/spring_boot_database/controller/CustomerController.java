package com.example.spring_boot_database.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.CreateCustomerRequest;
import com.example.spring_boot_database.dto.CustomerResponse;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.dto.OutstandingAmountResponse;
import com.example.spring_boot_database.service.CustomerService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> create(
            @Valid @RequestBody CreateCustomerRequest request) {

        return ApiResponse.success(
                customerService.createCustomer(request),
                "Customer created successfully"
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(
                customerService.findById(id),
                "Customer retrieved successfully"
        );
    }

    @GetMapping
    public ApiResponse<List<CustomerResponse>> getAll() {
        return ApiResponse.success(
                customerService.findCustomer(null),
                "Customers retrieved successfully"
        );
    }

    @GetMapping("/search")
    public ApiResponse<List<CustomerResponse>> search(@RequestParam String name) {
        return ApiResponse.success(
                customerService.findCustomer(name),
                "Customers found"
        );
    }

    @GetMapping("/{id}/loan-applications")
    public ApiResponse<List<LoanApplicationResponse>> getLoans(@PathVariable Long id) {
        return ApiResponse.success(
                customerService.findLoanByCustomer(id),
                "Loan applications retrieved successfully"
        );
    }

    @GetMapping("/{id}/outstanding-amount")
    public ApiResponse<OutstandingAmountResponse> getOutstandingAmount(@PathVariable Long id) {
        return ApiResponse.success(
                customerService.getOutstandingAmountByCustomer(id),
                "Outstanding amount retrieved successfully"
        );
    }
}