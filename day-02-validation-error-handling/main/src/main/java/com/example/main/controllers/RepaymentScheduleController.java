package com.example.main.controllers;

import com.example.main.dto.request.PaymentTransactionRequest;
import com.example.main.dto.request.RepaymentScheduleRequest;
import com.example.main.dto.response.PaymentTransactionResponse;
import com.example.main.dto.response.RepaymentScheduleResponse;
import com.example.main.security.RequiresRoles;
import com.example.main.security.UserRole;
import com.example.main.services.PaymentTransactionService;
import com.example.main.services.RepaymentScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment-transactions")
@Tag(name = "Payment Transaction Management", description = "Kumpulan API untuk mencatat dan mengelola pembayaran cicilan")
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;
    private final PaymentTransactionService paymentTransactionService;

    public RepaymentScheduleController(RepaymentScheduleService repaymentScheduleService, PaymentTransactionService paymentTransactionService) {
        this.repaymentScheduleService = repaymentScheduleService;
        this.paymentTransactionService = paymentTransactionService;
    }

    @GetMapping("/{id}")
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF, UserRole.APPROVER, UserRole.MANAGER})
    @Operation(
        summary = "Mendapatkan detail jadwal cicilan berdasarkan ID", 
        description = "Mengambil satu data spesifik jadwal pembayaran cicilan beserta rincian pokok dan bunganya. [Akses: Semua Role]"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Data jadwal cicilan ditemukan"),
        @ApiResponse(responseCode = "404", description = "ID jadwal cicilan tidak ditemukan")
    })
    public ResponseEntity<RepaymentScheduleResponse> getRepaymentScheduleById(
            @Parameter(description = "ID unik dari jadwal cicilan", example = "1") 
            @PathVariable Long id) {
        
        RepaymentScheduleResponse response = repaymentScheduleService.getRepaymentScheduleById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{repayment_schedule_id}/payment-transactions")
    @RequiresRoles({UserRole.ADMIN, UserRole.STAFF, UserRole.APPROVER, UserRole.MANAGER})
    @Operation(
        summary = "Mendapatkan daftar transaksi pembayaran untuk suatu jadwal cicilan", 
        description = "Mengambil semua riwayat mutasi pembayaran (berhasil/gagal) yang terkait dengan ID jadwal cicilan tertentu."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Daftar transaksi pembayaran berhasil diambil"),
        @ApiResponse(responseCode = "404", description = "ID jadwal cicilan tidak ditemukan")
    })
    public ResponseEntity<List<PaymentTransactionResponse>> getTransactionsByScheduleId(
            @Parameter(description = "ID dari jadwal cicilan", example = "1") 
            @PathVariable("repayment_schedule_id") Long repaymentScheduleId) {
        
        List<PaymentTransactionResponse> responses = paymentTransactionService.getTransactionsByScheduleId(repaymentScheduleId);
        return ResponseEntity.ok(responses);
    }
}
