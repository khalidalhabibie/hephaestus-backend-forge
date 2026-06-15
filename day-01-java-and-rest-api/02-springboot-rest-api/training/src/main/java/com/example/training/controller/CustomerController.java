package com.example.training.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.training.service.CustomerService;
import jakarta.validation.Valid;
import com.example.training.dto.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Class ini adalah REST Controller untuk menangani semua request HTTP yang berhubungan dengan data Customer,
// diakses melalui base URL "/api/v3/customers"
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
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Method ini menangani request GET ke "/api/v3/customers", fungsinya untuk mengambil semua data Customer
    // yang ada, lalu dikembalikan dalam bentuk List dengan HTTP status 200 OK
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(){
        return ResponseEntity.ok(customerService.getCustomers());
    }

    // Method ini menangani request GET ke "/api/v3/customers/{id}", fungsinya untuk mengambil satu data Customer
    // berdasarkan ID yang dikirim lewat URL, lalu dikembalikan dengan HTTP status 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // Method ini menangani request DELETE ke "/api/v3/customers/{id}", fungsinya untuk menghapus data Customer
    // berdasarkan ID yang dikirim lewat URL.
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomerById(@PathVariable Long id) {
        return (ResponseEntity<CustomerResponse>) ResponseEntity.ok();
    }
}