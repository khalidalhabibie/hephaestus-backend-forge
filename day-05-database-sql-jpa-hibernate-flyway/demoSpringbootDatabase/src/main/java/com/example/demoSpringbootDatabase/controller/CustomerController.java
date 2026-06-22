package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.CreateCustomerRequest;
import com.example.demoSpringbootDatabase.dto.CustomerResponse;
import com.example.demoSpringbootDatabase.service.CustomerService;
import com.example.demoSpringbootDatabase.service.RepaymentScheduleService; // Import service pendukung
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final RepaymentScheduleService repaymentScheduleService; // Deklarasi variabel service

    // Constructor Injection untuk meng-inject kedua jenis bean/service
    public CustomerController(CustomerService customerService, RepaymentScheduleService repaymentScheduleService) { 
        this.customerService = customerService; 
        this.repaymentScheduleService = repaymentScheduleService;
    }

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
    
    /**
     * Path diperbaiki dari "/customers/{id}/outstanding" menjadi "/{id}/outstanding"
     * URL Akhir: GET /api/v1/customers/{id}/outstanding
     */
    @GetMapping("/{id}/outstanding")
    public ResponseEntity<ApiResponse<BigDecimal>> getOutstanding(@PathVariable Long id) {
        BigDecimal outstanding = repaymentScheduleService.getCustomerOutstanding(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Outstanding balance retrieved", outstanding));
    }
}