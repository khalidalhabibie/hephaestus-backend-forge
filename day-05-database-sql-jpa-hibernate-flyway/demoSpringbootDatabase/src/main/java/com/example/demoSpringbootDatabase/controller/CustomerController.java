package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.CreateCustomerRequest;
import com.example.demoSpringbootDatabase.dto.CustomerResponse;
import com.example.demoSpringbootDatabase.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) { this.customerService = customerService; }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> create(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse res = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Customer created successfully", res));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer retrieved successfully", customerService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "All customers retrieved successfully", customerService.getAll()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> search(@RequestParam("name") String name) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Filtered customers retrieved successfully", customerService.searchByName(name)));
    }
}
