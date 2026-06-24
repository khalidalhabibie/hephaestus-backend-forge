package com.fif.training.exercisespringboot.Controller;

import com.fif.training.exercisespringboot.DTO.ApiResponse;
import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.DTO.UpdateCustomerRequest;
import com.fif.training.exercisespringboot.Model.User;
import com.fif.training.exercisespringboot.Security.AuthContext;
import com.fif.training.exercisespringboot.Security.AuthUtil;
import com.fif.training.exercisespringboot.Security.RoleValidator;
import com.fif.training.exercisespringboot.Service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Customer API", description = "Customer API Documentation")
@RequestMapping("/api/v1/customers")
public class CustomerController {

    CustomerService service = new CustomerService();

    // ========================= HELPER RBAC =========================
    private User validateToken(HttpServletRequest request) {
        String token = AuthUtil.getToken(request);
        return AuthContext.getUserByToken(token);
    }

    private ResponseEntity<ApiResponse<CustomerResponse>> unauthorizedResponse() {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(
                        "UNAUTHORIZED",
                        "Authentication required",
                        List.of()));
    }

    private ResponseEntity<ApiResponse<CustomerResponse>> forbiddenResponse() {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(
                        "FORBIDDEN",
                        "You do not have permission to access this resource",
                        List.of()));
    }
    // =============================================================

    // ✅ GET ALL (ADMIN, STAFF, APPROVER)
    @GetMapping
    @Operation(summary = "Get All Customer", description = "Get All Customer API")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(HttpServletRequest request) {

        User user = validateToken(request);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCustomer());
    }

    // ✅ CREATE (ADMIN, STAFF)
    @PostMapping
    @Operation(summary = "Create Customer", description = "Create a new customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request,
            HttpServletRequest httpRequest) {

        User user = validateToken(httpRequest);

        if (user == null) {
            return unauthorizedResponse();
        }

        if (!RoleValidator.allow(user.getRole(), "ADMIN", "STAFF")) {
            return forbiddenResponse();
        }

        CustomerResponse response = service.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerResponse>builder()
                        .code("CUSTOMER_CREATED")
                        .message("Customer created successfully")
                        .data(response)
                        .build());
    }

    // ✅ GET BY ID (ADMIN, STAFF, APPROVER)
    @GetMapping("/{id}")
    @Operation(summary = "Get Customer by ID", description = "Get a customer by their ID")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCustomerbyId(@PathVariable Long id,
            HttpServletRequest request) {

        User user = validateToken(request);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<CustomerResponse>("UNAUTHORIZED", null));
        }

        CustomerResponse response = service.getCustomerById(id);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("CUSTOMER_NOT_FOUND", null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<CustomerResponse>(response));
    }

    // ✅ DELETE (ADMIN ONLY)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Customer by ID", description = "Delete a customer by their ID")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<CustomerResponse>> deleteCustomerById(
            @PathVariable Long id,
            HttpServletRequest request) {

        User user = validateToken(request);

        if (user == null) {
            return unauthorizedResponse();
        }

        if (!RoleValidator.allow(user.getRole(), "ADMIN")) {
            return forbiddenResponse();
        }

        CustomerResponse response = service.deleteCustomerById(id);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("CUSTOMER_NOT_FOUND", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer Deleted!", response));
    }

    // ✅ PUT (ADMIN, STAFF)
    @PutMapping("/{id}")
    @Operation(summary = "Update Customer by ID", description = "Update a customer by their ID")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<CustomerResponse>> putCustomerById(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request,
            HttpServletRequest httpRequest) {

        User user = validateToken(httpRequest);

        if (user == null) {
            return unauthorizedResponse();
        }

        if (!RoleValidator.allow(user.getRole(), "ADMIN", "STAFF")) {
            return forbiddenResponse();
        }

        CustomerResponse response = service.putCustomerById(id, request);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("CUSTOMER_NOT_FOUND", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer data updated successfully", response));
    }

    // ✅ PATCH (ADMIN, STAFF)
    @PatchMapping("/{id}")
    @Operation(summary = "Partially Update Customer by ID", description = "Partially update a customer by their ID")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<CustomerResponse>> patchCustomerById(
            @PathVariable Long id,
            @Valid @RequestBody com.fif.training.exercisespringboot.DTO.PatchCustomerRequest request,
            HttpServletRequest httpRequest) {

        User user = validateToken(httpRequest);

        if (user == null) {
            return unauthorizedResponse();
        }

        if (!RoleValidator.allow(user.getRole(), "ADMIN", "STAFF")) {
            return forbiddenResponse();
        }

        CustomerResponse response = service.patchCustomerById(id, request);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("CUSTOMER_NOT_FOUND", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer data updated successfully", response));
    }

    // ✅ SEARCH (ADMIN, STAFF, APPROVER)
    @GetMapping("/search")
    @Operation(summary = "Search Customer by Email", description = "Search customer by email using query parameter")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> searchCustomerByEmail(
            @RequestParam String email,
            HttpServletRequest request) {

        User user = validateToken(request);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("UNAUTHORIZED", null));
        }

        List<CustomerResponse> response = service.searchCustomerByEmail(email);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
