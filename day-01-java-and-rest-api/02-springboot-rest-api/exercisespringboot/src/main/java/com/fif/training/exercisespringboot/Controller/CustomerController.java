package com.fif.training.exercisespringboot.Controller;

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

import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.DTO.PatchCustomerRequest;
import com.fif.training.exercisespringboot.DTO.UpdateCustomerRequest;
import com.fif.training.exercisespringboot.Model.User;
import com.fif.training.exercisespringboot.Security.AuthContext;
import com.fif.training.exercisespringboot.Security.AuthUtil;
import com.fif.training.exercisespringboot.Security.RoleValidator;
import com.fif.training.exercisespringboot.Service.CustomerService;
import com.fif.training.exercisespringboot.exception.ForbiddenException;
import com.fif.training.exercisespringboot.exception.UnauthorizedException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Customer API", description = "Customer API Documentation")
@RequestMapping("/api/v1/customers")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private User validateToken(HttpServletRequest request) {
        String token = AuthUtil.getToken(request);
        User user = AuthContext.getUserByToken(token);
        if (user == null) {
            throw new UnauthorizedException("Authentication is required");
        }
        return user;
    }

    private void checkRole(User user, String... allowedRoles) {
        if (!RoleValidator.allow(user.getRole(), allowedRoles)) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
    }

    @GetMapping
    @Operation(summary = "Get All Customer", description = "Get All Customer API")
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(HttpServletRequest request) {
        validateToken(request);
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @PostMapping
    @Operation(summary = "Create Customer", description = "Create a new customer")
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request,
            HttpServletRequest httpRequest) {
        User user = validateToken(httpRequest);
        checkRole(user, "ADMIN", "STAFF");
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Customer by ID", description = "Get a customer by their ID")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id, HttpServletRequest request) {
        validateToken(request);
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Customer by ID", description = "Delete a customer by their ID")
    public ResponseEntity<CustomerResponse> deleteCustomerById(@PathVariable Long id, HttpServletRequest request) {
        User user = validateToken(request);
        checkRole(user, "ADMIN");
        CustomerResponse response = customerService.deleteCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Customer by ID", description = "Update a customer by their ID")
    public ResponseEntity<CustomerResponse> putCustomerById(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request,
            HttpServletRequest httpRequest) {
        User user = validateToken(httpRequest);
        checkRole(user, "ADMIN", "STAFF");
        CustomerResponse response = customerService.putCustomerById(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially Update Customer by ID", description = "Partially update a customer by their ID")
    public ResponseEntity<CustomerResponse> patchCustomerById(
            @PathVariable Long id,
            @Valid @RequestBody PatchCustomerRequest request,
            HttpServletRequest httpRequest) {
        User user = validateToken(httpRequest);
        checkRole(user, "ADMIN", "STAFF");
        CustomerResponse response = customerService.patchCustomerById(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search Customer by Email", description = "Search customer by email using query parameter")
    public ResponseEntity<List<CustomerResponse>> searchCustomerByEmail(
            @RequestParam String email,
            HttpServletRequest request) {
        validateToken(request);
        List<CustomerResponse> response = customerService.searchCustomerByEmail(email);
        return ResponseEntity.ok(response);
    }
}