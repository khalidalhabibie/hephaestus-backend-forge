package com.example.jpabackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import com.example.jpabackend.controller.*;
import com.example.jpabackend.dto.*;
import com.example.jpabackend.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LoanApplicationController.class)
class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanApplicationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_create_loan_successfully() throws Exception {
        // given
        CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
        req.setCustomerId(1L);
        req.setLoanAmount(BigDecimal.valueOf(1000));
        req.setTenorMonth(12);
        req.setPurpose("Test");

        LoanApplicationResponse res = mock(LoanApplicationResponse.class);

        when(service.createLoan(any())).thenReturn(res);

        // when & then
        mockMvc.perform(post("/api/v1/loan-applications")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Loan created successfully"));
    }

    @Test
    void should_get_loan_by_id() throws Exception {
        // given
        LoanApplicationResponse res = mock(LoanApplicationResponse.class);

        when(service.getById(1L)).thenReturn(res);

        // when & then
        mockMvc.perform(get("/api/v1/loan-applications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Loan retrieved successfully"));
    }

    @Test
    void should_get_all_loans() throws Exception {
        when(service.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/loan-applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All loans retrieved"));
    }

    @Test
    void should_get_loans_by_status() throws Exception {
        when(service.getByStatus("APPROVED")).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/loan-applications/filter")
                .param("status", "APPROVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Filtered loans"));
    }

    @Test
    void should_get_loans_page() throws Exception {
        Page<LoanApplicationResponse> page = new PageImpl<>(List.of());

        when(service.getAll(anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(get("/api/v1/loan-applications/page")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Loans retrieved"));
    }

    @Test
    void should_filter_by_date() throws Exception {
        when(service.filterByDate(any(), any()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/loan-applications/filter-date")
                .param("start", "2024-01-01T00:00:00Z")
                .param("end", "2024-01-31T00:00:00Z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Filtered loans"));
    }

    @Test
    void should_get_loans_by_customer_id() throws Exception {
        when(service.getByCustomerId(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/customers/1/loan-applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Customer loans retrieved successfully"));
    }

    @Test
    void should_update_status() throws Exception {
        // given
        LoanApplicationResponse res = mock(LoanApplicationResponse.class);

        when(service.updateStatus(eq(1L), eq("APPROVED")))
                .thenReturn(res);

        // when & then
        mockMvc.perform(patch("/api/v1/loan-applications/1/status")
                .contentType("application/json")
                .content("{\"status\":\"APPROVED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Status updated"));
    }

    @Test
    void should_get_summary() throws Exception {
        when(service.getLoanSummaryDTO()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/loan-applications/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Loan summary retrieved"));
    }

    @Test
    void should_allow_null_status_and_return_success_when_status_missing() throws Exception {
        mockMvc.perform(patch("/api/v1/loan-applications/1/status")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isOk());
    }

}
