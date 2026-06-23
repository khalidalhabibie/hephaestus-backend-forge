package com.example.jpabackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import com.example.jpabackend.controller.*;
import com.example.jpabackend.dto.*;
import com.example.jpabackend.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PaymentTransactionController.class)
class PaymentTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentTransactionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_create_payment_successfully() throws Exception {
        // given
        CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
        req.setRepaymentScheduleId(1L);
        req.setPaymentReference("REF123");
        req.setPaidAmount(BigDecimal.valueOf(100));
        req.setPaidAt(ZonedDateTime.now());

        PaymentTransactionResponse res = mock(PaymentTransactionResponse.class);

        when(service.createPayment(any())).thenReturn(res);

        // when & then
        mockMvc.perform(post("/api/v1/payment-transactions")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value("Payment created successfully"));
    }

    @Test
    void should_get_payments_by_schedule_id() throws Exception {
        // given
        when(service.getByScheduleId(1L)).thenReturn(List.of());

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules/1/payment-transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Payment transactions retrieved successfully"));
    }

    @Test
    void should_return_empty_list_when_no_payment_found() throws Exception {
        // given
        when(service.getByScheduleId(1L)).thenReturn(List.of());

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules/1/payment-transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void should_fail_when_create_payment_request_invalid() throws Exception {
        // given
        CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
        // kosong → kena @Valid

        // when & then
        mockMvc.perform(post("/api/v1/payment-transactions")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_error_when_service_throw_exception() throws Exception {
        // given
        CreatePaymentTransactionRequest req = new CreatePaymentTransactionRequest();
        req.setRepaymentScheduleId(1L);
        req.setPaymentReference("REF123");
        req.setPaidAmount(BigDecimal.valueOf(100));
        req.setPaidAt(ZonedDateTime.now());

        when(service.createPayment(any()))
                .thenThrow(new RuntimeException("ERROR"));

        // when & then
        mockMvc.perform(post("/api/v1/payment-transactions")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void should_return_payment_list_structure() throws Exception {
        // given
        PaymentTransactionResponse res = mock(PaymentTransactionResponse.class);

        when(service.getByScheduleId(1L))
                .thenReturn(List.of(res));

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules/1/payment-transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }
}
