package com.example.spring_boot_database.controller;


import lombok.RequiredArgsConstructor;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.CreateCustomerRequest;
import com.example.spring_boot_database.dto.CustomerResponse;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.service.CustomerService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor

public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request) throws BadRequestException {
        return ApiResponse.success(customerService.createCustomer(request), "Customer retrieved successfully ");
    }

    @GetMapping
    public ApiResponse<List<CustomerResponse>> getByName(@RequestParam(required = false) String name) {
        return ApiResponse.success(customerService.findCustomer(name), "Customer Found");
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(customerService.findById(id), "Customer Found");
    }

    @GetMapping("/{id}/loan-applications")
    public ApiResponse<List<LoanApplicationResponse>> getLoanApplicationById(@PathVariable Long id) {
        return ApiResponse.success(customerService.findLoanApplicationByCustomerId(id), "Customer Found");
    }

}
