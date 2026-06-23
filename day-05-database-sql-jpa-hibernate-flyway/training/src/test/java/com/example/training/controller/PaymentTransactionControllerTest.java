package com.example.training.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.training.dto.CreatePaymentTransactionRequest;
import com.example.training.dto.PaymentTransactionResponse;
import com.example.training.exception.NotFoundException;
import com.example.training.service.PaymentTransactionService;

@WebMvcTest(PaymentTransactionController.class)
class PaymentTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentTransactionService paymentTransactionService;

    private UUID scheduleId;
    private UUID paymentId;

    @BeforeEach
    void setUp() {
        scheduleId = UUID.randomUUID();
        paymentId = UUID.randomUUID();
    }

    @Test
    void create_ShouldReturnSuccess() throws Exception {
        PaymentTransactionResponse response = PaymentTransactionResponse.builder()
                .id(paymentId)
                .repaymentScheduleId(scheduleId)
                .paymentReference("PAY-001")
                .paidAmount(new BigDecimal("950000"))
                .paidAt(OffsetDateTime.parse("2026-06-19T10:00:00+07:00"))
                .status("SUCCESS")
                .build();

        when(paymentTransactionService.create(any(CreatePaymentTransactionRequest.class)))
                .thenReturn(response);

        String body = """
                {
                    "repayment_schedule_id": "%s",
                    "payment_reference": "PAY-001",
                    "paid_amount": 950000,
                    "paid_at": "2026-06-19T10:00:00+07:00"
                }""".formatted(scheduleId);

        mockMvc.perform(post("/api/v1/payment-transactions")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(paymentId.toString()));
    }

    @Test
    void create_ShouldReturnBadRequestWhenInvalid() throws Exception {
        String body = """
                {
                    "repayment_schedule_id": "%s",
                    "payment_reference": "",
                    "paid_amount": -1,
                    "paid_at": "2026-06-19T10:00:00+07:00"
                }""".formatted(scheduleId);

        mockMvc.perform(post("/api/v1/payment-transactions")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void create_ShouldReturnNotFound() throws Exception {
        when(paymentTransactionService.create(any(CreatePaymentTransactionRequest.class)))
                .thenThrow(new NotFoundException("REPAYMENT_SCHEDULE_NOT_FOUND", "Not found"));

        String body = """
                {
                    "repayment_schedule_id": "%s",
                    "payment_reference": "PAY-001",
                    "paid_amount": 950000,
                    "paid_at": "2026-06-19T10:00:00+07:00"
                }""".formatted(scheduleId);

        mockMvc.perform(post("/api/v1/payment-transactions")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("REPAYMENT_SCHEDULE_NOT_FOUND"));
    }

    @Test
    void getByRepaymentScheduleId_ShouldReturnList() throws Exception {
        PaymentTransactionResponse response = PaymentTransactionResponse.builder()
                .id(paymentId)
                .repaymentScheduleId(scheduleId)
                .paymentReference("PAY-001")
                .paidAmount(new BigDecimal("950000"))
                .status("SUCCESS")
                .build();

        when(paymentTransactionService.findByRepaymentScheduleId(scheduleId))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/repayment-schedules/{id}/payment-transactions", scheduleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    void getByRepaymentScheduleId_ShouldReturnEmptyList() throws Exception {
        when(paymentTransactionService.findByRepaymentScheduleId(scheduleId))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/repayment-schedules/{id}/payment-transactions", scheduleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(0));
    }
}
