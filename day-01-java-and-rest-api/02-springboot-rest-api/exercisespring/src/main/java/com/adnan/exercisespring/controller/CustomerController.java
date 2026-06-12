package com.adnan.exercisespring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adnan.exercisespring.dto.CreateCustomerRequest;
import com.adnan.exercisespring.dto.CustomerResponse;
import com.adnan.exercisespring.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
    CustomerResponse response = customerService.createCustomer(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> getAllCustomers(@RequestParam(required = false) String name) {
    return ResponseEntity.ok(customerService.getAllCustomers(name));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable long id) {
    return ResponseEntity.ok(customerService.getCustomerById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable long id,
      @RequestBody CreateCustomerRequest request) {
    CustomerResponse response = customerService.updateCustomer(id, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable long id) {
    CustomerResponse response = customerService.deleteCustomer(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
