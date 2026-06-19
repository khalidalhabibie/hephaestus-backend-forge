package com.fif.exercise02.controller;

import com.fif.exercise02.entity.Role;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.fif.exercise02.dto.CreateCustomerRequest;
import com.fif.exercise02.dto.CustomerResponse;
import com.fif.exercise02.dto.ErrorResponse;
import com.fif.exercise02.dto.PatchCustomerRequest;
import com.fif.exercise02.security.AuthContext;
import com.fif.exercise02.security.AuthUtil;
import com.fif.exercise02.security.RoleValidator;
import com.fif.exercise02.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer API", description = "API untuk mengelola data customer")
public class CustomerControllerV2 {

        private final CustomerService customerService;

        public CustomerControllerV2(CustomerService customerService) {
                this.customerService = customerService;
        }

        @PostMapping
        @Operation(summary = "Create customer", description = "Membuat customer baru")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Customer berhasil dibuat"),
                        @ApiResponse(responseCode = "400", description = "Request tidak valid")
        })

        public ResponseEntity<?> createCustomer(
                        @RequestHeader("Authorization") String header,
                        @Valid @RequestBody CreateCustomerRequest request) {

                var ctx = getAuth(header);
                if (ctx == null)
                        return unauthorized();

                if (!RoleValidator.isAllowed(ctx.getRole(), Role.ADMIN, Role.STAFF)) {
                        return forbidden();
                }

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(customerService.createCustomer(request));
        }

        @GetMapping
        @Operation(summary = "Get all customers", description = "Mengambil semua data customer")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Berhasil mengambil data")
        })
        public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
                return ResponseEntity.ok(customerService.getAllCustomers());
        }

        @GetMapping("/pagination")
        @Operation(summary = "Get customers with pagination", description = "Mengambil data customer dengan pagination")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Berhasil mengambil data")
        })
        public ResponseEntity<List<CustomerResponse>> getAllCustomersWithPagination(
                        @RequestParam int page,
                        @RequestParam int size) {

                return ResponseEntity.ok(customerService.getAllCustomers(page, size));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get customer by ID", description = "Mengambil customer berdasarkan ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Customer ditemukan"),
                        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan")
        })
        public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String id) {
                return ResponseEntity.ok(customerService.getCustomerById(id));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete customer", description = "Menghapus customer berdasarkan ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Customer berhasil dihapus"),
                        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan")
        })
        public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
                customerService.deleteCustomer(id);
                return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update customer (PUT)", description = "Update seluruh data customer")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Customer berhasil diupdate"),
                        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan"),
                        @ApiResponse(responseCode = "400", description = "Request tidak valid")
        })
        public ResponseEntity<CustomerResponse> updateCustomer(
                        @PathVariable String id,
                        @Valid @RequestBody CreateCustomerRequest request) {

                CustomerResponse response = customerService.updateCustomer(id, request);
                return ResponseEntity.ok(response);
        }

        @PatchMapping("/{id}")
        @Operation(summary = "Patch customer", description = "Update sebagian data customer")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Customer berhasil diupdate"),
                        @ApiResponse(responseCode = "404", description = "Customer tidak ditemukan"),
                        @ApiResponse(responseCode = "400", description = "Request tidak valid")
        })
        public ResponseEntity<CustomerResponse> patchCustomer(
                        @PathVariable String id,
                        @Valid @RequestBody PatchCustomerRequest request) {

                CustomerResponse response = customerService.patchCustomer(id, request);
                return ResponseEntity.ok(response);
        }

        @GetMapping("/search")
        @Operation(summary = "Search customer by email", description = "Mengambil customer berdasarkan email")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Berhasil mengambil data"),
                        @ApiResponse(responseCode = "404", description = "Email tidak ditemukan")
        })
        public ResponseEntity<List<CustomerResponse>> getCustomerByEmail(
                        @RequestParam String email) {

                return ResponseEntity.ok(customerService.getCustomerByEmail(email));
        }

        // HELPER
        private AuthContext getAuth(String header) {
                return AuthUtil.parseToken(AuthUtil.extractToken(header));
        }

        private ResponseEntity<?> unauthorized() {
                return ResponseEntity.status(401)
                                .body(ErrorResponse.error("401", "UNAUTHORIZED", null));
        }

        private ResponseEntity<?> forbidden() {
                return ResponseEntity.status(403)
                                .body(ErrorResponse.error("403", "FORBIDDEN", null));
        }

}
