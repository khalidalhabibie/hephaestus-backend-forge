package com.example.day2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.day2.dto.CreateCustomerRequest;
import com.example.day2.dto.CustomerResponse;
import com.example.day2.dto.ErrorResponse;
import com.example.day2.dto.PatchCustomerRequest;
import com.example.day2.dto.PutCustomerRequest;
import com.example.day2.dto.WebResponse;
import com.example.day2.security.AuthUtil;
import com.example.day2.security.RoleValidator;
import com.example.day2.service.CustomerServiceV2;
import com.example.day2.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Customer API v2", description = "")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerControllerV2 {

    private final CustomerServiceV2 customerService;

    public CustomerControllerV2(CustomerServiceV2 customerService) {
        this.customerService = customerService;
    }

    
    @Operation(
        summary = "Get all customers",
        description = "Retrieve a list of all customers"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched customers"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping
    public ResponseEntity<WebResponse<List<CustomerResponse>>> getAllCustomers() {
        List<CustomerResponse> data = customerService.getAllCustomer();
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched all customers", data));
    }

    
    @Operation(
        summary = "Create a new customer",
        description = "Create a new customer using provided request body"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Customer successfully created"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @PostMapping
    public ResponseEntity<WebResponse<CustomerResponse>> createCustomer(@RequestHeader(value = "Authorization", required = false) String authHeader, 
                @Valid @RequestBody CreateCustomerRequest request) {        
        CustomerResponse data = customerService.createCustomer(request);
        
        User user = AuthUtil.validateToken(authHeader);
        if (!RoleValidator.hasAccess(user.getRole(), "ADMIN", "STAFF")) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        return new ResponseEntity<>(createWebResponse(HttpStatus.CREATED, "Successfully created new customer", data), HttpStatus.CREATED);
    }
    

    
    @Operation(
        summary = "Get customer by ID",
        description = "Retrieve customer details by customer ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found"),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<CustomerResponse>> getCustomerById(@PathVariable Long id) {
        CustomerResponse data = customerService.getCustomerById(id);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched customer", data));
    }


    
@Operation(
        summary = "Update customer (PUT)",
        description = "Replace customer data entirely"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<CustomerResponse>> updateCustomer(@PathVariable Long id, @Valid @RequestBody PutCustomerRequest request) {
        CustomerResponse data = customerService.putCustomer(id, request);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully updated customer", data));
    }


    
    @Operation(
        summary = "Update customer partially (PATCH)",
        description = "Update specific customer fields"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })

    @PatchMapping("/{id}")
    public ResponseEntity<WebResponse<CustomerResponse>> updateCustomer(@PathVariable Long id, @Valid @RequestBody PatchCustomerRequest request) {
        CustomerResponse data = customerService.patchCustomer(id, request);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully updated customer", data));
    }


    
    @Operation(
        summary = "Delete customer",
        description = "Delete a customer by ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<Void>> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully deleted customer", null));
    }


    
    @Operation(
        summary = "Search customers",
        description = "Search customers by name (optional query param)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customers successfully retrieved"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/search")
    public ResponseEntity<WebResponse<List<CustomerResponse>>> getAllOrSearchCustomers(@RequestParam(value = "name", required = false) String keyword) {
        List<CustomerResponse> data = customerService.searchCustomers(keyword);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully filtered customers", data));
    }

    private <T> WebResponse<T> createWebResponse(HttpStatus status, String message, T data) {
        return WebResponse.<T>builder()
                .code(String.valueOf(status.value()))
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}