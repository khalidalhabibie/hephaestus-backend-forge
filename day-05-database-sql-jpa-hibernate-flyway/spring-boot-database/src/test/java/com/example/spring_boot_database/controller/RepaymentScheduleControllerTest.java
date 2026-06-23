package com.example.spring_boot_database.controller;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.entity.StatusRepayment;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.exception.RepaymentScheduleNotFoundException;
import com.example.spring_boot_database.service.RepaymentScheduleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepaymentScheduleController.class)
class RepaymentScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepaymentScheduleService scheduleService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private RepaymentScheduleResponse repaymentScheduleResponse;
    private RepaymentScheduleResponse repaymentSchedulePaidResponse;
    private PaymentTransactionResponse paymentTransactionResponse;
    private PaymentTransactionResponse secondPaymentTransactionResponse;

    @BeforeEach
    void setUp() {
        repaymentScheduleResponse = RepaymentScheduleResponse.builder()
                .id(1L)
                .installmentNumber(1)
                .dueDate(LocalDate.of(2026, 7, 19))
                .principalAmount(new BigDecimal("833333.00"))
                .interestAmount(new BigDecimal("100000.00"))
                .totalAmount(new BigDecimal("933333.00"))
                .status(StatusRepayment.UNPAID)
                .build();

        repaymentSchedulePaidResponse = RepaymentScheduleResponse.builder()
                .id(2L)
                .installmentNumber(2)
                .dueDate(LocalDate.of(2026, 8, 19))
                .principalAmount(new BigDecimal("833333.00"))
                .interestAmount(new BigDecimal("100000.00"))
                .totalAmount(new BigDecimal("933333.00"))
                .status(StatusRepayment.PAID)
                .build();

        paymentTransactionResponse = PaymentTransactionResponse.builder()
                .repaymentScheduleId(1L)
                .paidAmount(new BigDecimal("500000.00"))
                .paymentReference("PAY-REF-001")
                .paidAt(LocalDateTime.of(2026, 7, 20, 10, 30, 0))
                .build();

        secondPaymentTransactionResponse = PaymentTransactionResponse.builder()
                .repaymentScheduleId(1L)
                .paidAmount(new BigDecimal("433333.00"))
                .paymentReference("PAY-REF-002")
                .paidAt(LocalDateTime.of(2026, 7, 21, 15, 45, 0))
                .build();
    }

    @Test
    void get_by_id_success() throws Exception {
        when(scheduleService.findById(1L))
                .thenReturn(repaymentScheduleResponse);

        mockMvc.perform(
                get("/api/v1/repayment-schedules/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Repayment schedule retrieved successfully", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));

                    JsonNode data = root.get("data");

                    assertEquals(1L, data.get("id").asLong());
                    assertEquals(1, data.get("installment_number").asInt());
                    assertEquals("2026-07-19", data.get("due_date").asText());

                    assertEquals(0, new BigDecimal("833333.00")
                            .compareTo(data.get("principal_amount").decimalValue()));
                    assertEquals(0, new BigDecimal("100000.00")
                            .compareTo(data.get("interest_amount").decimalValue()));
                    assertEquals(0, new BigDecimal("933333.00")
                            .compareTo(data.get("total_amount").decimalValue()));

                    assertEquals("UNPAID", data.get("status").asText());
                });

        verify(scheduleService, times(1)).findById(1L);
    }

    @Test
    void get_by_id_failed_not_found() throws Exception {
        when(scheduleService.findById(99L))
                .thenThrow(new RepaymentScheduleNotFoundException(99L));

        mockMvc.perform(
                get("/api/v1/repayment-schedules/{id}", 99L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("REPAYMENT_NOT_FOUND", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(scheduleService, times(1)).findById(99L);
    }

    @Test
    void get_by_id_failed_invalid_id_type() throws Exception {
        mockMvc.perform(
                get("/api/v1/repayment-schedules/{id}", "abc")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("INVALID_PARAMETER", response.getError());
                    assertEquals("Invalid parameter value", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(scheduleService, never()).findById(anyLong());
    }

    @Test
    void get_by_id_failed_unexpected_exception() throws Exception {
        when(scheduleService.findById(1L))
                .thenThrow(new RuntimeException("database error"));

        mockMvc.perform(
                get("/api/v1/repayment-schedules/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isInternalServerError())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("INTERNAL_SERVER_ERROR", response.getError());
                    assertEquals("Unexpected error occurred", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                    assertEquals("RuntimeException", response.getDetails().get(0));
                });

        verify(scheduleService, times(1)).findById(1L);
    }

    @Test
    void get_payments_success() throws Exception {
        when(scheduleService.findPaymentTransactionByRepaymentId(1L))
                .thenReturn(List.of(paymentTransactionResponse, secondPaymentTransactionResponse));

        mockMvc.perform(
                get("/api/v1/repayment-schedules/{id}/payment-transactions", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Payment transactions retrieved successfully", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(2, root.get("data").size());

                    JsonNode first = root.get("data").get(0);

                    assertEquals(1L, first.get("repayment_schedule_id").asLong());
                    assertEquals(0, new BigDecimal("500000.00")
                            .compareTo(first.get("paid_amount").decimalValue()));
                    assertEquals("PAY-REF-001", first.get("payment_reference").asText());
                    assertTrue(first.has("paid_at"));

                    JsonNode second = root.get("data").get(1);

                    assertEquals(1L, second.get("repayment_schedule_id").asLong());
                    assertEquals(0, new BigDecimal("433333.00")
                            .compareTo(second.get("paid_amount").decimalValue()));
                    assertEquals("PAY-REF-002", second.get("payment_reference").asText());
                    assertTrue(second.has("paid_at"));
                });

        verify(scheduleService, times(1)).findPaymentTransactionByRepaymentId(1L);
    }

    @Test
    void get_payments_success_empty_list() throws Exception {
        when(scheduleService.findPaymentTransactionByRepaymentId(1L))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/repayment-schedules/{id}/payment-transactions", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Payment transactions retrieved successfully", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(0, root.get("data").size());
                });

        verify(scheduleService, times(1)).findPaymentTransactionByRepaymentId(1L);
    }

    @Test
    void get_payments_failed_invalid_id_type() throws Exception {
        mockMvc.perform(
                get("/api/v1/repayment-schedules/{id}/payment-transactions", "abc")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("INVALID_PARAMETER", response.getError());
                    assertEquals("Invalid parameter value", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(scheduleService, never()).findPaymentTransactionByRepaymentId(anyLong());
    }

    @Test
    void get_payments_failed_unexpected_exception() throws Exception {
        when(scheduleService.findPaymentTransactionByRepaymentId(1L))
                .thenThrow(new RuntimeException("unexpected error"));

        mockMvc.perform(
                get("/api/v1/repayment-schedules/{id}/payment-transactions", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isInternalServerError())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("INTERNAL_SERVER_ERROR", response.getError());
                    assertEquals("Unexpected error occurred", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                    assertEquals("RuntimeException", response.getDetails().get(0));
                });

        verify(scheduleService, times(1)).findPaymentTransactionByRepaymentId(1L);
    }

    @Test
    void get_all_success_without_status() throws Exception {
        when(scheduleService.findAll(null))
                .thenReturn(List.of(repaymentScheduleResponse, repaymentSchedulePaidResponse));

        mockMvc.perform(
                get("/api/v1/repayment-schedules")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Repayment schedules retrieved", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(2, root.get("data").size());

                    JsonNode first = root.get("data").get(0);

                    assertEquals(1L, first.get("id").asLong());
                    assertEquals(1, first.get("installment_number").asInt());
                    assertEquals("2026-07-19", first.get("due_date").asText());
                    assertEquals("UNPAID", first.get("status").asText());

                    JsonNode second = root.get("data").get(1);

                    assertEquals(2L, second.get("id").asLong());
                    assertEquals(2, second.get("installment_number").asInt());
                    assertEquals("2026-08-19", second.get("due_date").asText());
                    assertEquals("PAID", second.get("status").asText());
                });

        verify(scheduleService, times(1)).findAll(null);
    }

    @Test
    void get_all_success_with_status_unpaid() throws Exception {
        when(scheduleService.findAll("UNPAID"))
                .thenReturn(List.of(repaymentScheduleResponse));

        mockMvc.perform(
                get("/api/v1/repayment-schedules")
                        .param("status", "UNPAID")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Repayment schedules retrieved", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(1, root.get("data").size());

                    JsonNode data = root.get("data").get(0);

                    assertEquals(1L, data.get("id").asLong());
                    assertEquals(1, data.get("installment_number").asInt());
                    assertEquals("2026-07-19", data.get("due_date").asText());
                    assertEquals("UNPAID", data.get("status").asText());
                });

        verify(scheduleService, times(1)).findAll("UNPAID");
    }

    @Test
    void get_all_success_with_status_paid() throws Exception {
        when(scheduleService.findAll("PAID"))
                .thenReturn(List.of(repaymentSchedulePaidResponse));

        mockMvc.perform(
                get("/api/v1/repayment-schedules")
                        .param("status", "PAID")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Repayment schedules retrieved", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(1, root.get("data").size());

                    JsonNode data = root.get("data").get(0);

                    assertEquals(2L, data.get("id").asLong());
                    assertEquals(2, data.get("installment_number").asInt());
                    assertEquals("2026-08-19", data.get("due_date").asText());
                    assertEquals("PAID", data.get("status").asText());
                });

        verify(scheduleService, times(1)).findAll("PAID");
    }

    @Test
    void get_all_success_with_status_lowercase() throws Exception {
        when(scheduleService.findAll("paid"))
                .thenReturn(List.of(repaymentSchedulePaidResponse));

        mockMvc.perform(
                get("/api/v1/repayment-schedules")
                        .param("status", "paid")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Repayment schedules retrieved", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(1, root.get("data").size());

                    JsonNode data = root.get("data").get(0);

                    assertEquals(2L, data.get("id").asLong());
                    assertEquals("PAID", data.get("status").asText());
                });

        verify(scheduleService, times(1)).findAll("paid");
    }

    @Test
    void get_all_success_with_blank_status() throws Exception {
        when(scheduleService.findAll(""))
                .thenReturn(List.of(repaymentScheduleResponse, repaymentSchedulePaidResponse));

        mockMvc.perform(
                get("/api/v1/repayment-schedules")
                        .param("status", "")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Repayment schedules retrieved", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(2, root.get("data").size());
                });

        verify(scheduleService, times(1)).findAll("");
    }

    @Test
    void get_all_success_empty_list() throws Exception {
        when(scheduleService.findAll(null))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/repayment-schedules")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Repayment schedules retrieved", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(0, root.get("data").size());
                });

        verify(scheduleService, times(1)).findAll(null);
    }

    @Test
    void get_all_failed_invalid_status() throws Exception {
        when(scheduleService.findAll("INVALID"))
                .thenThrow(new BadRequestException("Invalid repayment status. Allowed values: PAID, UNPAID"));

        mockMvc.perform(
                get("/api/v1/repayment-schedules")
                        .param("status", "INVALID")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("BAD_REQUEST", response.getError());
                    assertEquals("Invalid repayment status. Allowed values: PAID, UNPAID", response.getMessage());
                    assertNull(response.getData());
                });

        verify(scheduleService, times(1)).findAll("INVALID");
    }

    @Test
    void get_all_failed_unexpected_exception() throws Exception {
        when(scheduleService.findAll(null))
                .thenThrow(new RuntimeException("database error"));

        mockMvc.perform(
                get("/api/v1/repayment-schedules")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isInternalServerError())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("INTERNAL_SERVER_ERROR", response.getError());
                    assertEquals("Unexpected error occurred", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                    assertEquals("RuntimeException", response.getDetails().get(0));
                });

        verify(scheduleService, times(1)).findAll(null);
    }

    @Test
    void post_repayment_schedules_failed_method_not_allowed() throws Exception {
        mockMvc.perform(
                post("/api/v1/repayment-schedules")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(status().isMethodNotAllowed())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("METHOD_NOT_ALLOWED", response.getError());
                    assertEquals("HTTP method is not supported", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                });

        verifyNoInteractions(scheduleService);
    }

    @Test
    void put_repayment_schedule_by_id_failed_method_not_allowed() throws Exception {
        mockMvc.perform(
                put("/api/v1/repayment-schedules/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(status().isMethodNotAllowed())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("METHOD_NOT_ALLOWED", response.getError());
                    assertEquals("HTTP method is not supported", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                });

        verifyNoInteractions(scheduleService);
    }

    @Test
    void delete_payment_transactions_failed_method_not_allowed() throws Exception {
        mockMvc.perform(
                delete("/api/v1/repayment-schedules/{id}/payment-transactions", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isMethodNotAllowed())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("METHOD_NOT_ALLOWED", response.getError());
                    assertEquals("HTTP method is not supported", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                });

        verifyNoInteractions(scheduleService);
    }
}