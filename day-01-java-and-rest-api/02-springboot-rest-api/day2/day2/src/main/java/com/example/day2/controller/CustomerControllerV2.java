package com.example.day2.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer V1 Management", description = "Kumpulan API untuk mengelola data customer versi 2 menggunakan H2 database")
public class CustomerControllerV2 {

    private final CustomerServiceV2 customerService;

    public CustomerControllerV2(CustomerServiceV2 customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "Mengambil data customer dengan Pagination", description = "Mendapatkan daftar customer yang dibagi per halaman menggunakan parameter page, size, dan sort")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Berhasil mengambil data customer dengan pagination",
            content = @Content(schema = @Schema(implementation = WebResponse.class, subTypes = {Page.class})))
    })
    public ResponseEntity<WebResponse<Page<CustomerResponse>>> getAllCustomers(
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        
        Page<CustomerResponse> data = customerService.getAllCustomerWithPage(pageable);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched paged customers", data));
    }

    @PostMapping
    @Operation(summary = "Membuat customer baru", description = "Menambahkan data customer baru ke database dengan validasi mandatory field")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Customer berhasil dibuat",
            content = @Content(schema = @Schema(implementation = WebResponse.class, subTypes = {CustomerResponse.class}))),
        @ApiResponse(responseCode = "400", description = "Request tidak valid / Gagal validasi data input", 
            content = @Content(schema = @Schema(implementation = WebResponse.class)))
    })
    public ResponseEntity<WebResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {        
        CustomerResponse data = customerService.createCustomer(request);
        return new ResponseEntity<>(createWebResponse(HttpStatus.CREATED, "Successfully created new customer", data), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Mencari customer berdasarkan ID", description = "Mengembalikan data satu customer yang sesuai dengan ID yang dicari")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customer ditemukan",
            content = @Content(schema = @Schema(implementation = WebResponse.class, subTypes = {CustomerResponse.class}))),
        @ApiResponse(responseCode = "404", description = "Customer dengan ID tersebut tidak ditemukan", 
            content = @Content(schema = @Schema(implementation = WebResponse.class)))
    })
    public ResponseEntity<WebResponse<CustomerResponse>> getCustomerById(@PathVariable Long id) {
        CustomerResponse data = customerService.getCustomerById(id);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched customer", data));
    }


    @GetMapping("email/{email}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customer ditemukan",
            content = @Content(schema = @Schema(implementation = WebResponse.class, subTypes = {CustomerResponse.class}))),
        @ApiResponse(responseCode = "404", description = "Customer dengan Email tersebut tidak ditemukan", 
            content = @Content(schema = @Schema(implementation = WebResponse.class)))
    })
    public ResponseEntity<WebResponse<CustomerResponse>> getCustomerById(@PathVariable String email) {
        CustomerResponse data = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully fetched customer", data));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Memperbarui seluruh data customer (PUT)", description = "Mengganti seluruh data customer lama dengan data baru berdasarkan ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customer berhasil diperbarui",
            content = @Content(schema = @Schema(implementation = WebResponse.class, subTypes = {CustomerResponse.class}))),
        @ApiResponse(responseCode = "400", description = "Request tidak valid", 
            content = @Content(schema = @Schema(implementation = WebResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan", 
            content = @Content(schema = @Schema(implementation = WebResponse.class)))
    })
    public ResponseEntity<WebResponse<CustomerResponse>> updateCustomer(@PathVariable Long id, @Valid @RequestBody PutCustomerRequest request) {
        CustomerResponse data = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully updated customer", data));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Memperbarui sebagian data customer (PATCH)", description = "Hanya memperbarui field yang dikirimkan di request body tanpa menghapus data lama")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customer berhasil di-patch",
            content = @Content(schema = @Schema(implementation = WebResponse.class, subTypes = {CustomerResponse.class}))),
        @ApiResponse(responseCode = "400", description = "Format data request tidak valid", 
            content = @Content(schema = @Schema(implementation = WebResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan", 
            content = @Content(schema = @Schema(implementation = WebResponse.class)))
    })
    public ResponseEntity<WebResponse<CustomerResponse>> patchCustomer(@PathVariable Long id, @Valid @RequestBody PatchCustomerRequest request) {
        CustomerResponse data = customerService.patchCustomer(id, request); 
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully updated customer", data));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Menghapus customer", description = "Menghapus data customer dari database secara permanen berdasarkan ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customer berhasil dihapus",
            content = @Content(schema = @Schema(implementation = WebResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan", 
            content = @Content(schema = @Schema(implementation = WebResponse.class)))
    })
    public ResponseEntity<WebResponse<Void>> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok(createWebResponse(HttpStatus.OK, "Successfully deleted customer", null));
    }

    @GetMapping("/search")
    @Operation(summary = "Mencari customer dengan kata kunci", description = "Mencari customer berdasarkan kemiripan nama, email, atau nomor telepon")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Berhasil memfilter data customer",
            content = @Content(schema = @Schema(implementation = WebResponse.class, subTypes = {CustomerResponse.class})))
    })
    public ResponseEntity<WebResponse<List<CustomerResponse>>> getAllOrSearchCustomers(@RequestParam(value = "name", required = false) String keyword) {
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
