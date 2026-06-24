package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.RepaymentScheduleResponse;
import com.example.demoSpringbootDatabase.exception.RepaymentScheduleNotFoundException;
import com.example.demoSpringbootDatabase.service.RepaymentScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RepaymentScheduleController.class)
class RepaymentScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepaymentScheduleService scheduleService;

    private RepaymentScheduleResponse scheduleResponse;

    @BeforeEach
    void setUp() {
        scheduleResponse = RepaymentScheduleResponse.builder()
                .id(100L)
                .installmentNumber(1)
                .dueDate(LocalDate.now().plusMonths(1))
                .principalAmount(3333333L)
                .interestAmount(200000L)
                .totalAmount(3533333L)
                .status("UNPAID")
                .build();
    }

    // --- SECTION 1: HAPPY PATHS ---

    @Test
    @DisplayName("Happy Path: Ambil Jadwal Cicilan Dengan Filter Status HTTP 200 Ok")
    void getByLoan_HappyPath_WithStatus() throws Exception {
        // Given
        when(scheduleService.getByLoanIdAndStatus(eq(10L), eq("UNPAID")))
                .thenReturn(List.of(scheduleResponse));

        // When & Then
        mockMvc.perform(get("/api/v1/loan-applications/10/repayment-schedules")
                        .header("X-Correlation-Id", "TRACK-SCH-01")
                        .param("status", "UNPAID"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Correlation-Id", "TRACK-SCH-01"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Repayment schedules retrieved successfully"))
                .andExpect(jsonPath("$.data", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data[0].status").value("UNPAID"));
    }

    @Test
    @DisplayName("Happy Path: Ambil Jadwal Cicilan Tanpa Filter Status (Status Kosong) HTTP 200 Ok")
    void getByLoan_HappyPath_WithoutStatus() throws Exception {
        // Given
        when(scheduleService.getByLoanIdAndStatus(eq(10L), isNull()))
                .thenReturn(List.of(scheduleResponse));

        // When & Then
        mockMvc.perform(get("/api/v1/loan-applications/10/repayment-schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(100L));
    }

    @Test
    @DisplayName("Happy Path: Get Schedule Berdasarkan ID Tunggal HTTP 200 Ok")
    void getById_HappyPath_Ok() throws Exception {
        // Given
        when(scheduleService.getById(100L)).thenReturn(scheduleResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/repayment-schedules/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total_amount").value(3533333L));
    }

    // --- SECTION 2: NEGATIVE PATHS ---

    @Test
    @DisplayName("Negative Path: Get Schedule Gagal karena ID Tidak Eksis HTTP 404 Not Found")
    void getById_NegativePath_NotFound() throws Exception {
        // Given
        when(scheduleService.getById(999L)).thenThrow(new RepaymentScheduleNotFoundException(999L));

        // When & Then
        mockMvc.perform(get("/api/v1/repayment-schedules/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.correlation_id").exists());
    }
}