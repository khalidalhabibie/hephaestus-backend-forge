package com.fif.finance_training.controller;
import com.fif.finance_training.dto.ApiResponse;
import com.fif.finance_training.dto.CreateCustomerRequest;
import com.fif.finance_training.dto.CustomerResponse;
import com.fif.finance_training.dto.CustomerSummaryResponse;
import com.fif.finance_training.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    // 1. Endpoint untuk membuat Customer Baru (POST /api/customers)
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        
        ApiResponse<CustomerResponse> apiResponse = ApiResponse.<CustomerResponse>builder()
                .success(true)
                .message("Customer created successfully")
                .data(response)
                .build();
                
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    // 2. Endpoint untuk mengambil Customer berdasarkan ID (GET /api/customers/{id})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable("id") Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        
        ApiResponse<CustomerResponse> apiResponse = ApiResponse.<CustomerResponse>builder()
                .success(true)
                .message("Customer retrieved successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }

    // 3. Endpoint untuk mengambil semua Customer ATAU mencari berdasarkan nama (GET /api/customers?name=...)
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerSummaryResponse>>> getAllCustomers(
            @RequestParam(value = "name", required = false) String name) {
        
        List<CustomerSummaryResponse> response;
        
        // Jika ada query param ?name=..., lakukan pencarian
        if (name != null && !name.trim().isEmpty()) {
            response = customerService.searchCustomersByName(name);
        } else {
            response = customerService.getAllCustomers();
        }
        
        ApiResponse<List<CustomerSummaryResponse>> apiResponse = ApiResponse.<List<CustomerSummaryResponse>>builder()
                .success(true)
                .message("Customers retrieved successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }
    
    // mengambil ID berdasarkan search Name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CustomerSummaryResponse>>> searchCustomers(
            @RequestParam(name = "name", required = false) String name){

        List<CustomerSummaryResponse> response = customerService.searchCustomersByName(name);
        
        ApiResponse<List<CustomerSummaryResponse>> apiResponse = ApiResponse.<List<CustomerSummaryResponse>>builder()
                .success(true)
                .message("Customers search completed successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }
}
