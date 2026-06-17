package com.andyana.exerciseday02.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.andyana.exerciseday02.dto.ApiResponses;
import com.andyana.exerciseday02.dto.CreateCustomerRequest;
import com.andyana.exerciseday02.dto.CustomerResponse;
import com.andyana.exerciseday02.dto.PatchCustomerRequest;
import com.andyana.exerciseday02.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Management", description = "API untuk mengelola data customer") //artinya bahwa kelas ini akan memiliki tag "Customer API" dengan deskripsi "API untuk mengelola data customer" dalam dokumentasi OpenAPI yang dihasilkan oleh Springdoc. Tag ini dapat digunakan untuk mengelompokkan endpoint-endpoint yang terkait dengan customer dalam dokumentasi API.
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(summary = "Membuat Customer Baru", description = "Membuat data customer baru berdasarkan informasi yang diberikan dalam request body") //artinya bahwa metode createCustomer akan memiliki ringkasan "Membuat Customer Baru" dan deskripsi "Membuat data customer baru berdasarkan informasi yang diberikan dalam request body" dalam dokumentasi OpenAPI yang dihasilkan oleh Springdoc. Ringkasan dan deskripsi ini memberikan informasi tambahan tentang tujuan dan fungsi dari endpoint tersebut dalam dokumentasi API.
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Data Customer Berhasil Dibuat"),
        @ApiResponse(responseCode = "400", description = "Permintaan tidak sesuai"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody //artinya bahwa data yang dikirim oleh klien dalam body request akan diikat (bind) ke objek CreateCustomerRequest
        @Valid //artinya bahwa objek CreateCustomerRequest yang diterima dari body request akan divalidasi berdasarkan anotasi validasi yang ada pada kelas CreateCustomerRequest. 
        CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Mendapatkan Customer Berdasarkan ID", description = "Mendapatkan data customer berdasarkan ID yang diberikan") //artinya bahwa metode getCustomerById akan memiliki ringkasan "Mendapatkan Customer Berdasarkan ID" dan deskripsi "Mendapatkan data customer berdasarkan ID yang diberikan" dalam dokumentasi OpenAPI yang dihasilkan oleh Springdoc.
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Data Customer Ditemukan"),
        @ApiResponse(responseCode = "404", description = "Data Tidak Ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Mendapatkan Semua Customer", description = "Mendapatkan daftar semua customer") //artinya bahwa metode getAllCustomers akan memiliki ringkasan "Mendapatkan Semua Customer" dan deskripsi "Mendapatkan daftar semua customer" dalam dokumentasi OpenAPI yang dihasilkan oleh Springdoc.
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Data Customer Ditemukan"),
        @ApiResponse(responseCode = "404", description = "Data Tidak Ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Memperbarui Customer", description = "Memperbarui data customer berdasarkan ID yang diberikan") //artinya bahwa metode updateCustomer akan memiliki ringkasan "Memperbarui Customer" dan deskripsi "Memperbarui data customer berdasarkan ID yang diberikan" dalam dokumentasi OpenAPI yang dihasilkan oleh Springdoc.
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Data Customer Berhasil Diperbarui"),
        @ApiResponse(responseCode = "404", description = "Data Tidak Ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody @Valid CreateCustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Memperbarui Sebagian Data Customer", description = "Memperbarui sebagian data customer berdasarkan ID yang diberikan") //artinya bahwa metode patchCustomer akan memiliki ringkasan "Memperbarui Sebagian Data Customer" dan deskripsi "Memperbarui sebagian data customer berdasarkan ID yang diberikan" dalam dokumentasi OpenAPI yang dihasilkan oleh Springdoc.
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Data Customer Berhasil Diperbarui"),
        @ApiResponse(responseCode = "404", description = "Data Tidak Ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    public ResponseEntity<CustomerResponse> patchCustomer(@PathVariable Long id, @RequestBody @Valid PatchCustomerRequest request) {
        CustomerResponse response = customerService.patchCustomer(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Menghapus Customer", description = "Menghapus data customer berdasarkan ID yang diberikan") //artinya bahwa metode deleteCustomer akan memiliki ringkasan "Menghapus Customer" dan deskripsi "Menghapus data customer berdasarkan ID yang diberikan" dalam dokumentasi OpenAPI yang dihasilkan oleh Springdoc.
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Data Customer Berhasil Dihapus"),
        @ApiResponse(responseCode = "404", description = "Data Tidak Ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}