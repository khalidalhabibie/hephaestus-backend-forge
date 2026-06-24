package com.example.dbbackend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.dbbackend.paymenttransaction.controller.PaymentTransactionController;
import com.example.dbbackend.paymenttransaction.dto.CreatePaymentTransactionRequest;
import com.example.dbbackend.paymenttransaction.dto.PaymentTransactionResponse;
import com.example.dbbackend.paymenttransaction.service.PaymentTransactionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(PaymentTransactionController.class)
class PaymentTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PaymentTransactionService paymentTransactionService;

    @Test
    @DisplayName("POST /api/v1/payment-transactions - Harus Sukses Membuat Payment")
    void createPayment_ShouldReturnSuccess() throws Exception {
        // 1. GIVEN (Persiapan Data)
        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();

        // !!! ISI FIELD BERIKUT SESUAI DENGAN SETTER YANG ADA DI CLASS REQUEST ANDA !!!
        // Contoh pengisian agar lolos validasi @Valid:
        request.setRepaymentScheduleId(1L);
        request.setPaymentReference("REF-12345");
        request.setPaidAmount(new java.math.BigDecimal("100000"));
        request.setPaidAt(java.time.LocalDateTime.now());

        PaymentTransactionResponse response = new PaymentTransactionResponse();
        response.setId(1L);
        response.setPaymentReference("REF-12345");
        response.setPaidAmount(new java.math.BigDecimal("100000"));
        response.setPaidAt(java.time.LocalDateTime.now());
        response.setStatus(com.example.dbbackend.paymenttransaction.entity.PaymentTransactionStatus.SUCCESS);

        when(paymentTransactionService.createPayment(any(CreatePaymentTransactionRequest.class)))
                .thenReturn(response);

        // 2. WHEN & THEN (Eksekusi dan Validasi)
        mockMvc.perform(post("/api/v1/payment-transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // Sekarang akan mengembalikan status 200 OK
                .andExpect(jsonPath("$.message").value("Payment created successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @DisplayName("GET /api/v1/repayment-schedules/{scheduleId}/payment-transactions - Harus Sukses Mengambil List Data")
    void getBySchedule_ShouldReturnList() throws Exception {
        // 1. GIVEN
        Long scheduleId = 1L;
        PaymentTransactionResponse response = new PaymentTransactionResponse();
        List<PaymentTransactionResponse> responseList = Collections.singletonList(response);

        when(paymentTransactionService.getBySchedule(scheduleId)).thenReturn(responseList);

        // 2. WHEN & THEN
        mockMvc.perform(get("/api/v1/repayment-schedules/{scheduleId}/payment-transactions", scheduleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Payment transactions retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }
}
