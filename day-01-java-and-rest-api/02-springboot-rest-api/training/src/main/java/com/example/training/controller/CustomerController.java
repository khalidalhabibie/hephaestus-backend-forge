package com.example.training.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.training.service.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.training.dto.*;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

// Class ini adalah REST Controller untuk menangani semua request HTTP yang berhubungan dengan data Customer,
// diakses melalui base URL "/api/v3/customers"
@Tag(name = "User", description = "Test Swagger Tag")
@RestController
@RequestMapping("api/v3/customers")
public class CustomerController {
    
    // Deklarasi variabel customerService yang akan digunakan untuk memanggil logika bisnis Customer
    private final CustomerService customerService;
    
    // Constructor ini digunakan untuk menyuntikkan (inject) CustomerService ke dalam controller,
    // sehingga controller bisa menggunakan fungsi-fungsi yang ada di service
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    // Method ini menangani request POST ke "/api/v3/customers", fungsinya untuk membuat data Customer baru.
    // Data customer dikirim lewat request body, lalu dikembalikan response berisi data customer yang berhasil dibuat
    // dengan HTTP status 201 Created
    @Operation(summary = "Post", description = "Kite unggah data")
    @ApiResponse(responseCode = "201", description = "Berhasil Dibuat")
    @ApiResponse(responseCode = "404", description = "User tidak ditemukan")
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Method ini menangani request GET ke "/api/v3/customers", fungsinya untuk mengambil semua data Customer
    // yang ada, lalu dikembalikan dalam bentuk List dengan HTTP status 200 OK
    @Operation(summary = "Get", description = "Kite cari lu org punya data")
    @ApiResponse(responseCode = "200", description = "Berhasil")
    @ApiResponse(responseCode = "404", description = "User tidak ditemukan")
    @ApiResponse(responseCode = "400", description = "Request Invalid")
    @ApiResponse(responseCode = "500", description = "Logis lu salah")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(){
        return ResponseEntity.ok(customerService.getCustomers());
    }

    // Method ini menangani request GET ke "/api/v3/customers/{id}", fungsinya untuk mengambil satu data Customer
    // berdasarkan ID yang dikirim lewat URL, lalu dikembalikan dengan HTTP status 200 OK
    @Operation(summary = "Get by ID", description = "Kite cari data lu pake ID")
    @ApiResponse(responseCode = "200", description = "Berhasil Ditemukan")
    @ApiResponse(responseCode = "404", description = "User tidak ditemukan")
    @ApiResponse(responseCode = "400", description = "Request Invalid")
    @ApiResponse(responseCode = "500", description = "Logis lu salah")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    //Method Change
    @Operation(summary = "Put by ID", description = "Kite ngubah data")
    @ApiResponse(responseCode = "200", description = "Berhasil Diganti")
    @ApiResponse(responseCode = "404", description = "User tidak ditemukan")
    @ApiResponse(responseCode = "400", description = "Request Invalid")
    @ApiResponse(responseCode = "500", description = "Logis lu salah")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomerById(@PathVariable Long id, @Valid @RequestBody CreateCustomerRequest entity) {
        CustomerResponse response = customerService.updateCustomer(id, entity);
        return ResponseEntity.ok(response);
    }

    // Method ini menangani request DELETE ke "/api/v3/customers/{id}", fungsinya untuk menghapus data Customer
    // berdasarkan ID yang dikirim lewat URL.
    @Operation(summary = "Delete", description = "We delete your data bro")
    @ApiResponse(responseCode = "200", description = "Berhasil Dihapust")
    @ApiResponse(responseCode = "404", description = "User tidak ditemukan")
    @ApiResponse(responseCode = "400", description = "Request Invalid")
    @ApiResponse(responseCode = "500", description = "Logis lu salah")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }


    @Operation(summary = "Patch by ID", description = "We patch your data per field")
    @ApiResponse(responseCode = "200", description = "Berhasil Diganti")
    @ApiResponse(responseCode = "404", description = "User tidak ditemukan")
    @ApiResponse(responseCode = "400", description = "Request Invalid")
    @ApiResponse(responseCode = "500", description = "Logis lu salah")
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponse> patchCustomerById(@PathVariable Long id, @Valid @RequestBody PatchCustomerRequest broski) {
        CustomerResponse response = customerService.patchCustomer(id, broski);
        return ResponseEntity.ok(response);
    }
}