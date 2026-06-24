package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.DeleteCustomerResponse;
import com.example.demo.dto.PatchUpdateCustomerRequest;
import com.example.demo.dto.PutUpdateCustomerRequest;
import com.example.demo.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/customers")
@Tag(name = "Customer", description = "Customer management APIs")
class CustomerControllerV2 {
    private final CustomerService customerService;

    public CustomerControllerV2(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(summary = "Create new customer", description = "Create a new customer in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieve all customers or filter by name or by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    public ResponseEntity<List<CustomerResponse>> getCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        return ResponseEntity.ok(customerService.getCustomers(name, email));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieve a specific customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerResponse> getCustomersById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerResponseById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer by ID", description = "Delete a specific customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DeleteCustomerResponse> deleteCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.deleteCustomerResponseById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer (full)", description = "Fully update customer data by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerResponse> putCustomerById(
            @PathVariable Long id,
            @Valid @RequestBody PutUpdateCustomerRequest request) {

        return ResponseEntity.ok(customerService.putCustomerResponseById(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update customer (partial)", description = "Partially update customer data by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerResponse> patchCustomerById(
            @PathVariable Long id,
            @Valid @RequestBody PatchUpdateCustomerRequest request) {

        return ResponseEntity.ok(customerService.patchCustomerResponseById(id, request));
    }
}
