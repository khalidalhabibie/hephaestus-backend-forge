package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/customers")
class CustomerControllerV2{
    private final CustomerService customerService;

    public CustomerControllerV2(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers(@Valid @RequestParam(required = false) String name) {

        if (name != null) {
            return ResponseEntity.ok(customerService.getCustomerResponsesByName(name));
        }

        return ResponseEntity.ok(
            customerService.getCustomers()
        );
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomersById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerResponseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.deleteCustomerResponseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> putCustomerById(@PathVariable Long id, @Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.putCustomerResponseById(id, request));
    }



}
