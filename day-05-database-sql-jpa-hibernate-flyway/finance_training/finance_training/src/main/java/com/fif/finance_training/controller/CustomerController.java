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

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerSummaryResponse>>> getAllCustomers(
            @RequestParam(value = "name", required = false) String name) {
        
        List<CustomerSummaryResponse> response;
        
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
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CustomerSummaryResponse>>> searchCustomers(
            @RequestParam("name") String name){

        List<CustomerSummaryResponse> response = customerService.searchCustomersByName(name);
        
        ApiResponse<List<CustomerSummaryResponse>> apiResponse = ApiResponse.<List<CustomerSummaryResponse>>builder()
                .success(true)
                .message("Customers search completed successfully")
                .data(response)
                .build();
                
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable("id") Long id) {
        customerService.softDeleteCustomer(id);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .message("Customer deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}