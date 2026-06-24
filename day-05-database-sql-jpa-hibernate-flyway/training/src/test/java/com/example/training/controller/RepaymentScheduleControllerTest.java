package com.example.training.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.training.dto.RepaymentScheduleResponse;
import com.example.training.exception.NotFoundException;
import com.example.training.service.RepaymentScheduleService;

@WebMvcTest(RepaymentScheduleController.class)
class RepaymentScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepaymentScheduleService repaymentScheduleService;

    private UUID loanId;
    private UUID scheduleId;

    @BeforeEach
    void setUp() {
        loanId = UUID.randomUUID();
        scheduleId = UUID.randomUUID();
    }

    @Test
    void getByLoanApplicationId_ShouldReturnList() throws Exception {
        RepaymentScheduleResponse resp = RepaymentScheduleResponse.builder()
                .id(scheduleId)
                .loanApplicationId(loanId)
                .installmentNumber(1)
                .dueDate(LocalDate.of(2026, 7, 19))
                .principalAmount(new BigDecimal("1000000"))
                .interestAmount(new BigDecimal("120000"))
                .totalAmount(new BigDecimal("1120000"))
                .status("UNPAID")
                .build();

        when(repaymentScheduleService.findByLoanApplicationId(loanId))
                .thenReturn(List.of(resp));

        mockMvc.perform(get("/api/v1/loan-applications/{loanId}/repayment-schedules", loanId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].installment_number").value(1));
    }

    @Test
    void getByLoanApplicationId_ShouldReturnEmptyList() throws Exception {
        when(repaymentScheduleService.findByLoanApplicationId(loanId))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/loan-applications/{loanId}/repayment-schedules", loanId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    void getById_ShouldReturnSchedule() throws Exception {
        RepaymentScheduleResponse resp = RepaymentScheduleResponse.builder()
                .id(scheduleId)
                .loanApplicationId(loanId)
                .installmentNumber(1)
                .dueDate(LocalDate.of(2026, 7, 19))
                .principalAmount(new BigDecimal("1000000"))
                .interestAmount(new BigDecimal("120000"))
                .totalAmount(new BigDecimal("1120000"))
                .status("UNPAID")
                .build();

        when(repaymentScheduleService.findById(scheduleId)).thenReturn(resp);

        mockMvc.perform(get("/api/v1/repayment-schedules/{id}", scheduleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(scheduleId.toString()));
    }

    @Test
    void getById_ShouldReturnNotFound() throws Exception {
        when(repaymentScheduleService.findById(scheduleId))
                .thenThrow(new NotFoundException("REPAYMENT_SCHEDULE_NOT_FOUND", "Not found"));

        mockMvc.perform(get("/api/v1/repayment-schedules/{id}", scheduleId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("REPAYMENT_SCHEDULE_NOT_FOUND"));
    }
}
