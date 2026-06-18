package com.example.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import com.example.main.dto.CreateCustomerRequest;
import com.example.main.dto.PatchCustomerRequest;
import com.example.main.security.RequiresRoles;
import com.example.main.security.UserRole;
import com.example.main.dto.CustomerResponse;
import com.example.main.services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Management", description = "Kumpulan API untuk mengelola data pelanggan")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF})
    @Operation(summary = "Membuat customer baru", description = "Menambahkan data customer baru ke dalam sistem in-memory.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Customer berhasil dibuat"),
        @ApiResponse(responseCode = "400", description = "Request tidak valid / Gagal validasi data")
    })
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF, UserRole.APPROVER})
    @Operation(summary = "Mendapatkan customer berdasarkan ID", description = "Mengambil data detail customer berdasarkan ID unik.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Data customer ditemukan"),
        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan")
    })
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF, UserRole.APPROVER})
    @Operation(summary = "Mendapatkan semua daftar customer", description = "Mengambil seluruh data customer yang tersimpan di dalam memory.")
    @ApiResponse(responseCode = "200", description = "Berhasil mendapatkan seluruh daftar customer")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @DeleteMapping("/{id}")
    @RequiresRoles({UserRole.ADMIN})
    @Operation(summary = "Menghapus customer berdasarkan ID", description = "Menghapus data customer dari memori berdasarkan ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "404", description = "Not Found", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF})
    @Operation(summary = "Memperbarui data customer secara penuh (PUT)", description = "Mengganti seluruh field data customer lama dengan data baru.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customer berhasil diperbarui"),
        @ApiResponse(responseCode = "400", description = "Request tidak valid"),
        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan")
    })
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id, 
            @Valid @RequestBody CreateCustomerRequest request) { 
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF})
    @Operation(summary = "Memperbarui data customer secara parsial (PATCH)", description = "Hanya memperbarui field yang dikirimkan saja.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customer berhasil diperbarui sebagian"),
        @ApiResponse(responseCode = "400", description = "Constraint validasi field tidak terpenuhi"),
        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan")
    })
    public ResponseEntity<CustomerResponse> patchCustomer(
            @PathVariable Long id, 
            @Valid @RequestBody PatchCustomerRequest request) {
        CustomerResponse response = customerService.patchCustomer(id, request);
        return ResponseEntity.ok(response);
    }
}