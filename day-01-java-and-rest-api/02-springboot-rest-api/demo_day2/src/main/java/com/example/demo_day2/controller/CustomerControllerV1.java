package com.example.demo_day2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo_day2.dto.CreateCustomerRequest;
import com.example.demo_day2.dto.CustomerResponse;
import com.example.demo_day2.dto.PatchCustomerRequest;
import com.example.demo_day2.service.AuthService;
import com.example.demo_day2.dto.UpdateCustomerRequest;
import com.example.demo_day2.model.Role;
import com.example.demo_day2.model.User;
import com.example.demo_day2.security.AuthUtil;
import com.example.demo_day2.security.RoleValidator;
import com.example.demo_day2.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Customer API", description = "API for managing customers")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerControllerV1 {

    private final CustomerService customerService;
    private final AuthService authService;

    public CustomerControllerV1(CustomerService customerService, AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates a new customer with the provided details")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")

    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request,
            HttpServletRequest httpRequest) {

        User user = AuthUtil.authenticate(httpRequest, authService);

        RoleValidator.validate(user, Role.ADMIN, Role.STAFF);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(request));
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers")
    @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")

    public ResponseEntity<?> getCustomerResponse(
            @RequestParam(required = false) String email,
            HttpServletRequest httpRequest) {

        User user = AuthUtil.authenticate(httpRequest, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.STAFF, Role.APPROVER);

        if (email != null && !email.isBlank()) {
            return ResponseEntity.ok(customerService.getCustomerByEmail(email));
        }

        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieves a customer by their unique identifier")
    @ApiResponse(responseCode = "200", description = "Customer retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")

    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

        User user = AuthUtil.authenticate(httpRequest, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.STAFF, Role.APPROVER);

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer", description = "Updates the details of an existing customer")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")

    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request,
            HttpServletRequest httpRequest) {

        User user = AuthUtil.authenticate(httpRequest, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.STAFF);

        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer", description = "Deletes a customer by their unique identifier")
    @ApiResponse(responseCode = "204", description = "Customer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")

    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

        User user = AuthUtil.authenticate(httpRequest, authService);
        RoleValidator.validate(user, Role.ADMIN);

        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a customer", description = "Partially updates the details of an existing customer")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")

    public ResponseEntity<CustomerResponse> patchCustomer(
            @PathVariable Long id,
            @Valid @RequestBody PatchCustomerRequest request,
            HttpServletRequest httpRequest) {

        User user = AuthUtil.authenticate(httpRequest, authService);
        RoleValidator.validate(user, Role.ADMIN, Role.STAFF);

        return ResponseEntity.ok(customerService.patchCustomer(id, request));
    }

}