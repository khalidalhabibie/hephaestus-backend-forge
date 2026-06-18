package com.fif.training.exercisespringboot.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fif.training.exercisespringboot.DTO.ApiResponseDto;
import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.DTO.PatchCustomerRequest;
import com.fif.training.exercisespringboot.DTO.UpdateCustomerRequest;
import com.fif.training.exercisespringboot.Service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestHeader;

import com.fif.training.exercisespringboot.Model.Roles;
import com.fif.training.exercisespringboot.Security.AuthContext;
import com.fif.training.exercisespringboot.Security.AuthUtil;
import com.fif.training.exercisespringboot.Security.RoleValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Tag(name = "Customer API", description = "API for managing customers")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthUtil authUtil;
    private final RoleValidator roleValidator;

    public CustomerController(CustomerService customerService, AuthUtil authUtil, RoleValidator roleValidator) {
        this.customerService = customerService;
        this.authUtil = authUtil;
        this.roleValidator = roleValidator;
    }

    // GET All Customer API
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get customer list", description = "Get all customers if query param not exist")
    @ApiResponse(responseCode = "200", description = "Customer list returned")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CustomerResponse>> getAllCustomer(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {

        AuthContext authContext = authUtil.getAuthContext(authorization);
        roleValidator.requireAnyRole(authContext, Roles.STAFF, Roles.ADMIN, Roles.APPROVER);

        List<CustomerResponse> response = customerService.getAllCustomer(name, email);

        return new ApiResponseDto<>("List of Customers", response);
    }

    // POST Customer API
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create customer", description = "Create a new customer")
    @ApiResponse(responseCode = "201", description = "Customer created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<CustomerResponse> createCustomer(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody CreateCustomerRequest request) {

        AuthContext authContext = authUtil.getAuthContext(authorization);

        roleValidator.requireAnyRole(authContext, Roles.STAFF, Roles.ADMIN);
        CustomerResponse response = customerService.createCustomer(request);
        return new ApiResponseDto<>("Customer Created!", response);
    }

    // GET Customer By ID API
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get customer by id", description = "Get one customer by id")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CustomerResponse> getCustomerbyId(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {

        AuthContext authContext = authUtil.getAuthContext(authorization);

        roleValidator.requireAnyRole(authContext, Roles.STAFF, Roles.ADMIN, Roles.APPROVER);
        CustomerResponse response = customerService.getCustomerById(id);
        return new ApiResponseDto<>("List of Customers", response);
    }

    // DELETE Customer By ID API
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete customer", description = "Delete Existing customer by id")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerById(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {

        AuthContext authContext = authUtil.getAuthContext(authorization);
        roleValidator.requireAnyRole(authContext, Roles.STAFF, Roles.ADMIN);
        customerService.deleteCustomerById(id);

    }

    // PUT/UPDATE Customer By ID API
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update customer", description = "Update all customer fields")
    @ApiResponse(responseCode = "200", description = "Customer updated")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CustomerResponse> editCustomerById(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {

        AuthContext authContext = authUtil.getAuthContext(authorization);
        roleValidator.requireAnyRole(authContext, Roles.STAFF, Roles.ADMIN);

        CustomerResponse response = customerService.editCustomerById(id, request);
        return new ApiResponseDto<>("Customer data updated successfully", response);
    }

    // Patch Customer By ID API
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Patch customer", description = "Update provided customer fields only")
    @ApiResponse(responseCode = "200", description = "Customer patched")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CustomerResponse> patchCustomerById(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody PatchCustomerRequest request) {
        AuthContext authContext = authUtil.getAuthContext(authorization);
        roleValidator.requireAnyRole(authContext, Roles.STAFF, Roles.ADMIN);
        CustomerResponse response = customerService.patchCustomerById(id, request);
        return new ApiResponseDto<>("Customer data updated successfully", response);
    }

}
