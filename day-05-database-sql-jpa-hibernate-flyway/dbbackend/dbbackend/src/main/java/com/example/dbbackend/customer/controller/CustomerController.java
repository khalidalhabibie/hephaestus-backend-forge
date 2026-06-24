package com.example.dbbackend.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dbbackend.common.dto.ApiResponse;
import com.example.dbbackend.customer.dto.CreateCustomerRequest;
import com.example.dbbackend.customer.dto.CustomerResponse;
import com.example.dbbackend.customer.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);

        return ApiResponse.success("Customer created successfully", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Long id) {

        CustomerResponse response = customerService.getCustomerById(id);

        return ApiResponse.success("Customer retrieved successfully", response);
    }
}
