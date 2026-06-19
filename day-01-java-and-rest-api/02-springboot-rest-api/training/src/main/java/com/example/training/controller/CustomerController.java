package com.example.training.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.training.service.AuthService;
import com.example.training.service.CustomerService;
import jakarta.validation.Valid;
import com.example.training.dto.CustomerResponse;
import com.example.training.dto.PatchCustomerRequest;
import com.example.training.dto.UpdateCustomerRequest;
import com.example.training.model.Customer;
import com.example.training.model.User;
import com.example.training.security.AuthUtil;
import com.example.training.security.RoleValidator;
import com.example.training.dto.CreateCustomerRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
@RestController
@RequestMapping("/api/v1/customers")
@Validated
public class CustomerController {
    private final CustomerService customerService;
    private final AuthService authService;
    
    public CustomerController(
        CustomerService customerService,
        AuthService authService) {
            this.customerService = customerService;
            this.authService = authService;
    }
        
    private User authenticate(String authorization) {
        String token = AuthUtil.extractToken(authorization);
        return authService.findByToken(token);
    }

    private CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    @PostMapping
    @Operation(summary = "Create customer")
    @ApiResponse(responseCode = "201", description = "Customer created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public ResponseEntity<CustomerResponse> createCustomer(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody CreateCustomerRequest request) {
        User user = authenticate(authorization);
        RoleValidator.validate(user, "ADMIN", "STAFF");
        Customer customer = customerService.createCustomer(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(customer));
    }
    
    @GetMapping
    @Operation(summary = "Get all customers")
    @ApiResponse(responseCode = "200", description = "Customer list returned")
    public ResponseEntity<List<CustomerResponse>> getCustomers(
            @RequestHeader("Authorization") String authorization) {
        User user = authenticate(authorization);
        RoleValidator.validate(user, "ADMIN", "STAFF", "APPROVER");
        List<CustomerResponse> response = customerService.getAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        User user = authenticate(authorization);
        RoleValidator.validate(user, "ADMIN", "STAFF", "APPROVER");
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(toResponse(customer));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {
        Customer customer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(toResponse(customer));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update customer")
    public ResponseEntity<CustomerResponse> patchCustomer(
            @PathVariable Long id,
            @Valid @RequestBody PatchCustomerRequest request) {
        Customer customer = customerService.patchCustomer(id, request);
        return ResponseEntity.ok(toResponse(customer));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}