package com.example.jpabackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.example.jpabackend.controller.*;
import com.example.jpabackend.dto.*;
import com.example.jpabackend.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RepaymentScheduleController.class)
class RepaymentScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepaymentScheduleService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_get_repayment_by_loan_id() throws Exception {
        // given
        when(service.getByLoanId(1L)).thenReturn(List.of());

        // when & then
        mockMvc.perform(get("/api/v1/loan-applications/1/repayment-schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Repayment schedules retrieved successfully"));
    }

    @Test
    void should_get_repayment_by_id() throws Exception {
        // given
        RepaymentScheduleResponse res = mock(RepaymentScheduleResponse.class);

        when(service.getById(1L)).thenReturn(res);

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Repayment schedule retrieved successfully"));
    }

    @Test
    void should_get_repayment_by_status() throws Exception {
        // given
        when(service.getByStatus("PAID")).thenReturn(List.of());

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules")
                .param("status", "PAID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Filtered schedules"));
    }

    @Test
    void should_get_all_repayment_schedules_when_status_null() throws Exception {
        // given
        when(service.getAll()).thenReturn(List.of());

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All schedules"));
    }

    @Test
    void should_get_outstanding_summary() throws Exception {
        // given
        when(service.getOutstandingPerCustomer()).thenReturn(List.of());

        // when & then
        mockMvc.perform(get("/api/v1/customers/outstanding"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Outstanding retrieved"));
    }

    @Test
    void should_return_empty_list_when_no_schedule_found() throws Exception {
        // given
        when(service.getByLoanId(1L)).thenReturn(List.of());

        // when & then
        mockMvc.perform(get("/api/v1/loan-applications/1/repayment-schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void should_return_list_structure() throws Exception {
        // given
        RepaymentScheduleResponse res = mock(RepaymentScheduleResponse.class);

        when(service.getByLoanId(1L)).thenReturn(List.of(res));

        // when & then
        mockMvc.perform(get("/api/v1/loan-applications/1/repayment-schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    void should_return_error_when_service_throw_exception() throws Exception {
        // given
        when(service.getById(1L)).thenThrow(new RuntimeException("ERROR"));

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules/1"))
                .andExpect(status().isInternalServerError());
    }

}
