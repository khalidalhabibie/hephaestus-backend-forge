package com.example.training.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.dto.ApiResponse;
import com.example.training.dto.CreateCustomerRequest;
import com.example.training.auth.AuthContext;
import com.example.training.dto.CustomerResponse;
import com.example.training.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request) {
        return ApiResponse.success(customerService.create(request, new AuthContext("system", "STAFF")));
    }

    @GetMapping
    public ApiResponse<List<CustomerResponse>> getAllCustomers() {
        return ApiResponse.success(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable UUID id) {
        return ApiResponse.success(customerService.findById(id));
    }

    @GetMapping("/search")
    public ApiResponse<List<CustomerResponse>> search(@RequestParam String name) {
        return ApiResponse.success(customerService.searchByName(name));
    }
}
