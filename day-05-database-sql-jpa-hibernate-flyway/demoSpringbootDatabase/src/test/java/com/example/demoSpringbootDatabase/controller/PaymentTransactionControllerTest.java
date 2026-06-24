package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.CreatePaymentTransactionRequest;
import com.example.demoSpringbootDatabase.dto.PaymentTransactionResponse;
import com.example.demoSpringbootDatabase.exception.RepaymentScheduleNotFoundException;
import com.example.demoSpringbootDatabase.service.PaymentTransactionService;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentTransactionController.class)
class PaymentTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PaymentTransactionService transactionService;

    private CreatePaymentTransactionRequest createRequest;
    private PaymentTransactionResponse transactionResponse;

    @BeforeEach
    void setUp() {
        createRequest = new CreatePaymentTransactionRequest();
        createRequest.setRepaymentScheduleId(100L);
        createRequest.setPaidAmount(3533333L);
        createRequest.setPaymentReference("PAY-REF-99999");
        createRequest.setPaidAt(LocalDateTime.now());

        transactionResponse = PaymentTransactionResponse.builder()
                .id(500L)
                .repaymentScheduleId(100L)
                .paymentReference("PAY-REF-99999")
                .paidAmount(3533333L)
                .paidAt(createRequest.getPaidAt())
                .status("SUCCESS")
                .build();
    }

    // --- SECTION 1: HAPPY PATHS ---

    @Test
    @DisplayName("Happy Path: Catat Pembayaran Baru Sukses HTTP 201 Created")
    void create_HappyPath_Created() throws Exception {
        // Given
        when(transactionService.createTransaction(any(CreatePaymentTransactionRequest.class)))
                .thenReturn(transactionResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/payment-transactions")
                        .header("X-Correlation-Id", "TRX-TRACK-001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("X-Correlation-Id", "TRX-TRACK-001"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Payment recorded successfully"))
                .andExpect(jsonPath("$.data.id").value(500L))
                .andExpect(jsonPath("$.data.status").value("SUCCESS"));
    }

    @Test
    @DisplayName("Happy Path: Ambil Riwayat Transaksi Berdasarkan ID Jadwal Cicilan HTTP 200 Ok")
    void getBySchedule_HappyPath_Ok() throws Exception {
        // Given
        when(transactionService.getByScheduleId(100L)).thenReturn(List.of(transactionResponse));

        // When & Then
        mockMvc.perform(get("/api/v1/repayment-schedules/100/payment-transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Payment transactions retrieved successfully"))
                .andExpect(jsonPath("$.data", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data[0].payment_reference").value("PAY-REF-99999"));
    }

    // --- SECTION 2: NEGATIVE PATHS ---

    @Test
    @DisplayName("Negative Path: Bayar Gagal karena Jadwal Cicilan Tidak Ditemukan HTTP 404 Not Found")
    void create_NegativePath_ScheduleNotFound() throws Exception {
        // Given
        when(transactionService.createTransaction(any(CreatePaymentTransactionRequest.class)))
                .thenThrow(new RepaymentScheduleNotFoundException(100L));

        // When & Then
        mockMvc.perform(post("/api/v1/payment-transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.correlation_id").exists());
    }
}