package com.example.jpabackend.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.jpabackend.dto.common.ApiResponse;
import com.example.jpabackend.dto.request.CreateCustomerRequest;
import com.example.jpabackend.dto.request.UpdateCustomerRequest;
import com.example.jpabackend.dto.response.CustomerResponse;
import com.example.jpabackend.dto.response.CustomerSummaryResponse;
import com.example.jpabackend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;
    @PostMapping
    public ApiResponse<CustomerResponse> create(@RequestBody @Valid CreateCustomerRequest request) {
        return ApiResponse.success("Created", service.createCustomer(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> get(@PathVariable Long id) {
        return ApiResponse.success("OK", service.getCustomerById(id));
    }

    @GetMapping
    public ApiResponse<List<CustomerSummaryResponse>> all() {
        return ApiResponse.success("OK", service.getAll());
    }

    @GetMapping("/search")
    public ApiResponse<List<CustomerSummaryResponse>> search(@RequestParam String name) {
        return ApiResponse.success("OK", service.searchCustomer(name));
    }

    @PutMapping("/{id}")
    public ApiResponse<CustomerResponse> update(
        @PathVariable Long id,
        @RequestBody @Valid UpdateCustomerRequest request) {
        return ApiResponse.success("Updated", service.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.deleteCustomer(id);
        return ApiResponse.success("Deleted", null);
    }
}