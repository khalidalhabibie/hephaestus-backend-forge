// Handle endpoint /api/v1/loan-applications: POST create, GET by ID, GET list (paginated + filter), GET by customer, GET by status, PATCH update status, GET summary, GET outstanding.

package com.example.training.Controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.DTO.ApiResponse;
import com.example.training.DTO.CreateLoanApplicationRequest;
import com.example.training.DTO.LoanApplicationResponse;
import com.example.training.DTO.UpdateLoanStatusRequest;
import com.example.training.Service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoanApplicationController {

        private final LoanApplicationService loanApplicationService;

        @PostMapping("/loan-applications")
        public ResponseEntity<ApiResponse<LoanApplicationResponse>> create(
                        @Valid @RequestBody CreateLoanApplicationRequest request) {
                LoanApplicationResponse dto = loanApplicationService.create(request);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ApiResponse.success("Loan application created successfully", dto));
        }

        @GetMapping("/loan-applications/{id}")
        public ResponseEntity<ApiResponse<LoanApplicationResponse>> getById(@PathVariable Long id) {
                return ResponseEntity.ok(ApiResponse.success("Loan application retrieved successfully",
                                loanApplicationService.getById(id)));
        }

        // ========== Pagination untuk List Loan Application (START) ========== //
        @GetMapping("/loan-applications")
        public ResponseEntity<ApiResponse<Page<LoanApplicationResponse>>> getAll(
                        @RequestParam(required = false) String status,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,    // ========== Filter Loan Berdasarkan Tanggal Pengajuan ========== //
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate,      // ========== Filter Loan Berdasarkan Tanggal Pengajuan ========== //
                        // @RequestParam(defaultValue = "0") int page,
                        // @RequestParam(defaultValue = "10") int size)
                        @PageableDefault
                        (page = 0, size = 10, sort = "id") Pageable pageable) {
                return ResponseEntity.ok(ApiResponse.success("Loan applications retrieved successfully",
                                loanApplicationService.getAllPaginated(status, startDate, endDate, pageable)));
        }
        // ========== Pagination untuk List Loan Application (END) ========== //

        @GetMapping("/customers/{customerId}/loan-applications")
        public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getByCustomerId(
                        @PathVariable Long customerId) {
                return ResponseEntity.ok(ApiResponse.success("Loan applications retrieved successfully",
                                loanApplicationService.getByCustomerId(customerId)));
        }

        @GetMapping("/loan-applications-by-status")
        public ResponseEntity<ApiResponse<List<LoanApplicationResponse>>> getByStatus(
                        @RequestParam String status) {
                return ResponseEntity.ok(ApiResponse.success("Loan applications filtered by status",
                                loanApplicationService.getByStatus(status)));
        }

        @PatchMapping("/loan-applications/{id}/status")
        public ResponseEntity<ApiResponse<LoanApplicationResponse>> updateStatus(
                        @PathVariable Long id,
                        @Valid @RequestBody UpdateLoanStatusRequest request) {
                return ResponseEntity.ok(ApiResponse.success("Loan status updated successfully",
                                loanApplicationService.updateStatus(id, request)));
        }

        // ========== Endpoint Summary Total Loan by Status (START) ========== //
        @GetMapping("/loan-applications/summary/status")
        public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSummaryByStatus() {
                return ResponseEntity.ok(ApiResponse.success("Summary retrieved successfully",
                                loanApplicationService.getSummaryByStatus()));
        }
        // ========== Endpoint Summary Total Loan by Status (END) ========== //

        // ========== Endpoint Outstanding Amount per Customer (START) ========== //
        @GetMapping("/loan-applications/outstanding")
        public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getOutstanding() {
                return ResponseEntity.ok(ApiResponse.success("Outstanding amounts retrieved successfully",
                                loanApplicationService.getOutstandingPerCustomer()));
        }
        // ========== Endpoint Outstanding Amount per Customer (END) ========== //
}