package com.fif.training.exercisespringboot.Controller;

import com.fif.training.exercisespringboot.DTO.ApiResponse;
import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.Service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.fif.training.exercisespringboot.DTO.UpdateCustomerRequest;

import com.fif.training.exercisespringboot.DTO.PatchCustomerRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Customer API", description = "Customer API Documentation")
@RequestMapping("/api/v1/customers")
public class CustomerController {

    CustomerService service = new CustomerService();

    // GET All Customer API
    @GetMapping
    @Operation(summary = "Get All Customer", description = "Get All Customer API")
    @ResponseStatus(HttpStatus.OK)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved all customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomer() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCustomer());
    }

    // POST Customer API
    @PostMapping
    @Operation(summary = "Create Customer", description = "Create a new customer")
    @ResponseStatus(HttpStatus.CREATED)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Customer created successfully")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = service.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Customer Created!", response));
    }

    // GET Customer By ID API
    @GetMapping("/{id}")
    @Operation(summary = "Get Customer by ID", description = "Get a customer by their ID")
    @ResponseStatus(HttpStatus.OK)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved the customer")
    public ResponseEntity<CustomerResponse> getCustomerbyId(@PathVariable Long id) {
        CustomerResponse response = service.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // DELETE Customer By ID API
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Customer by ID", description = "Delete a customer by their ID")
    @ResponseStatus(HttpStatus.OK)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer deleted successfully")
    public ResponseEntity<ApiResponse<CustomerResponse>> deleteCustomerById(@PathVariable Long id) {
        CustomerResponse response = service.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Customer Deleted!", response));
    }

    // PUT Customer By ID API
    @PutMapping("/{id}")
    @Operation(summary = "Update Customer by ID", description = "Update a customer by their ID")
    @ResponseStatus(HttpStatus.OK)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer data updated successfully")
    public ResponseEntity<ApiResponse<CustomerResponse>> putCustomerById(@PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {

        CustomerResponse response = service.putCustomerById(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer data updated successfully", response));
    }

    // PATCH Customer By ID API
    @PatchMapping("/{id}")
    @Operation(summary = "Partially Update Customer by ID", description = "Partially update a customer by their ID")
    @ResponseStatus(HttpStatus.OK)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer data updated successfully")
    public ResponseEntity<ApiResponse<CustomerResponse>> patchCustomerById(@PathVariable Long id,
            @Valid @RequestBody PatchCustomerRequest request) {

        CustomerResponse response = service.patchCustomerById(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer data updated successfully", response));
    }

    // GET Customer By Email API (Query Param)
    @GetMapping("/search")
    @Operation(summary = "Search Customer by Email", description = "Search customer by email using query parameter")
    @ResponseStatus(HttpStatus.OK)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved customers by email")
    public ResponseEntity<List<CustomerResponse>> searchCustomerByEmail(
            @RequestParam String email) {

        List<CustomerResponse> response = service.searchCustomerByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}