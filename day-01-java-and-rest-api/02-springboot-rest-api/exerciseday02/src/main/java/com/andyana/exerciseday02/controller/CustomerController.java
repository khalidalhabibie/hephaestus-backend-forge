package com.andyana.exerciseday02.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andyana.exerciseday02.dto.CreateCustomerRequest;
import com.andyana.exerciseday02.dto.CustomerResponse;
import com.andyana.exerciseday02.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody //artinya bahwa data yang dikirim oleh klien dalam body request akan diikat (bind) ke objek CreateCustomerRequest
        @Valid //artinya bahwa objek CreateCustomerRequest yang diterima dari body request akan divalidasi berdasarkan anotasi validasi yang ada pada kelas CreateCustomerRequest. 
        CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody @Valid CreateCustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}