package com.example.jpabackend.controller;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Customer API", description = "API untuk mengelola customer")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Create customer", description = "Membuat customer baru")
    public ResponseEntity<com.example.jpabackend.dto.ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {

        CustomerResponse response = service.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Customer created successfully", response));
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<com.example.jpabackend.dto.ApiResponse<CustomerResponse>> getById(
            @PathVariable Long id) {

        CustomerResponse response = service.getById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Customer retrieved successfully", response));
    }

    // GET ALL
    @GetMapping
    @Operation(summary = "Get all customers")
    public ResponseEntity<com.example.jpabackend.dto.ApiResponse<List<CustomerResponse>>> getAll() {

        List<CustomerResponse> response = service.getAll();

        return ResponseEntity.ok(
                ApiResponse.success("Customers retrieved successfully", response));
    }

    // SEARCH
    @GetMapping("/search")
    @Operation(summary = "Search customers by name")
    public ResponseEntity<com.example.jpabackend.dto.ApiResponse<List<CustomerResponse>>> searchByName(
            @RequestParam String name) {

        List<CustomerResponse> response = service.searchByName(name);

        return ResponseEntity.ok(
                ApiResponse.success("Customers search result", response));
    }

    //SOFT DELETE CUSTOMER
    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete customer by ID")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long id) {

        service.deleteCustomer(id);

        return ResponseEntity.ok(
                ApiResponse.success("Customer deleted successfully"));
    }
}