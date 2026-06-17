package com.adnan.exercisespring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adnan.exercisespring.dto.CreateCustomerRequest;
import com.adnan.exercisespring.dto.UpdateCustomerRequest;
import com.adnan.exercisespring.dto.CustomerResponse;
import com.adnan.exercisespring.dto.PatchCustomerRequest;
import com.adnan.exercisespring.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer API", description = "API for managing customers")
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  @Operation(summary = "Create customer", description = "Create a new customer")
  @ApiResponse(responseCode = "201", description = "Customer created")
  @ApiResponse(responseCode = "400", description = "Invalid request")
  public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
    CustomerResponse response = customerService.createCustomer(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  @Operation(summary = "Get customer list", description = "Get all customers")
  @ApiResponse(responseCode = "200", description = "Customer list returned")
  public ResponseEntity<List<CustomerResponse>> getAllCustomers(@RequestParam(required = false) String name) {
    return ResponseEntity.ok(customerService.getAllCustomers(name));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get customer by id", description = "Get one customer by id")
  @ApiResponse(responseCode = "200", description = "Customer found")
  @ApiResponse(responseCode = "404", description = "Customer not found")
  public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable long id) {
    return ResponseEntity.ok(customerService.getCustomerById(id));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update customer", description = "Update all customer fields")
  @ApiResponse(responseCode = "200", description = "Customer updated")
  @ApiResponse(responseCode = "400", description = "Invalid request")
  @ApiResponse(responseCode = "404", description = "Customer not found")
  public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable long id,
      @RequestBody @Valid UpdateCustomerRequest request) {
    CustomerResponse response = customerService.updateCustomer(id, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Patch customer", description = "Update provided customer fields only")
  @ApiResponse(responseCode = "200", description = "Customer patched")
  @ApiResponse(responseCode = "404", description = "Customer not found")
  public ResponseEntity<CustomerResponse> patchCustomer(@PathVariable long id,
      @RequestBody @Valid PatchCustomerRequest request) {
    CustomerResponse response = customerService.patchCustomer(id, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete customer", description = "Delete customer by id")
  @ApiResponse(responseCode = "200", description = "Customer deleted")
  @ApiResponse(responseCode = "404", description = "Customer not found")
  public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable long id) {
    CustomerResponse response = customerService.deleteCustomer(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
