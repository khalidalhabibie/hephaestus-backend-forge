package com.example.day2.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

import com.example.day2.dto.CreateCustomerRequest;
import com.example.day2.dto.CustomerResponse;
import com.example.day2.dto.PatchCustomerRequest;
import com.example.day2.dto.PutCustomerRequest;
import com.example.day2.dto.WebResponse;
import com.example.day2.service.CustomerServiceV2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer V1 Management", description = "API untuk mengelola data customer (H2 DB)")
@SecurityRequirement(name = "bearerAuth")
public class CustomerControllerV2 {

    private final CustomerServiceV2 customerService;

    public CustomerControllerV2(CustomerServiceV2 customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'APPROVAL', 'ADMIN')")
    @Operation(summary = "Mengambil seluruh data customer")
    public ResponseEntity<WebResponse<List<CustomerResponse>>> getAllCustomers() {
        List<CustomerResponse> data = customerService.getAllCustomer();
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched all customers", data));
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasAnyRole('STAFF', 'APPROVER', 'ADMIN')")
    @Operation(summary = "Mengambil data customer dengan Pagination")
    public ResponseEntity<WebResponse<Page<CustomerResponse>>> getAllCustomers(
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        Page<CustomerResponse> data = customerService.getAllCustomerWithPage(pageable);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched paged customers", data));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    @Operation(summary = "Membuat customer baru")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Customer berhasil dibuat"),
        @ApiResponse(responseCode = "400", description = "Request tidak valid")
    })
    public ResponseEntity<WebResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse data = customerService.createCustomer(request);
        return new ResponseEntity<>(createWebResponse(HttpStatus.CREATED, "Successfully created new customer", data), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'APPROVER', 'ADMIN')")
    @Operation(summary = "Mencari customer berdasarkan ID")
    public ResponseEntity<WebResponse<CustomerResponse>> getCustomerById(@PathVariable Long id) {
        CustomerResponse data = customerService.getCustomerById(id);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched customer", data));
    }

    @GetMapping("email/{email}")
    @PreAuthorize("hasAnyRole('STAFF', 'APPROVER', 'ADMIN')")
    public ResponseEntity<WebResponse<CustomerResponse>> getCustomerByEmail(@PathVariable String email) {
        CustomerResponse data = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched customer", data));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    @Operation(summary = "Memperbarui seluruh data customer (PUT)")
    public ResponseEntity<WebResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody PutCustomerRequest request) {
        CustomerResponse data = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully updated customer", data));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    @Operation(summary = "Memperbarui sebagian data customer (PATCH)")
    public ResponseEntity<WebResponse<CustomerResponse>> patchCustomer(
            @PathVariable Long id,
            @Valid @RequestBody PatchCustomerRequest request) {
        CustomerResponse data = customerService.patchCustomer(id, request);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully updated customer", data));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Menghapus customer (Admin only)")
    public ResponseEntity<WebResponse<Void>> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully deleted customer", null));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('STAFF', 'APPROVER', 'ADMIN')")
    @Operation(summary = "Mencari customer dengan kata kunci")
    public ResponseEntity<WebResponse<List<CustomerResponse>>> getAllOrSearchCustomers(
            @RequestParam(value = "name", required = false) String keyword) {
        List<CustomerResponse> data = customerService.searchCustomers(keyword);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully filtered customers", data));
    }

    private <T> WebResponse<T> createWebResponse(HttpStatus status, String message, T data) {
        return WebResponse.<T>builder()
                .code(String.valueOf(status.value()))
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
