package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.*;
import com.example.demoSpringbootDatabase.service.LoanApplicationService;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanApplicationController.class)
class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoanApplicationService loanService;

    private CreateLoanApplicationRequest createRequest;
    private UpdateLoanStatusRequest updateRequest;
    private LoanApplicationResponse loanResponse;

    @BeforeEach
    void setUp() {
        createRequest = new CreateLoanApplicationRequest();
        createRequest.setCustomerId(1L);
        createRequest.setLoanAmount(20000000L);
        createRequest.setTenorMonth(6);
        createRequest.setPurpose("Modal Usaha");

        updateRequest = new UpdateLoanStatusRequest();
        updateRequest.setStatus("APPROVED");

        CustomerSummaryResponse customerSummary = CustomerSummaryResponse.builder()
                .id(1L).fullName("Budi").nik("123").email("budi@mail.com").build();

        loanResponse = LoanApplicationResponse.builder()
                .id(10L)
                .loanAmount(20000000L)
                .tenorMonth(6)
                .purpose("Modal Usaha")
                .status("SUBMITTED")
                .customer(customerSummary)
                .build();
    }

    // --- SECTION 1: HAPPY PATHS ---

    @Test
    @DisplayName("Happy Path: Ajukan Pinjaman Baru HTTP 201 Created")
    void create_HappyPath_Created() throws Exception {
        // Given
        when(loanService.createLoan(any(CreateLoanApplicationRequest.class))).thenReturn(loanResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/loan-applications")
                        .header("X-Correlation-Id", "REQ-FORGE-LOAN-01")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("X-Correlation-Id", "REQ-FORGE-LOAN-01"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan application submitted successfully"))
                .andExpect(jsonPath("$.data.id").value(10L))
                .andExpect(jsonPath("$.data.status").value("SUBMITTED"));
    }

    @Test
    @DisplayName("Happy Path: Ambil Detail Pinjaman Berdasarkan ID HTTP 200 Ok")
    void getById_HappyPath_Ok() throws Exception {
        // Given
        when(loanService.getById(10L)).thenReturn(loanResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/loan-applications/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.loan_amount").value(20000000L));
    }

    @Test
    @DisplayName("Happy Path: Menguji API Pagination dan Filter Rentang Halaman HTTP 200 Ok")
    void getAll_HappyPath_Paginated() throws Exception {
        // Given
        org.springframework.data.domain.Page<LoanApplicationResponse> pageResult = 
                new PageImpl<>(List.of(loanResponse));
        
        when(loanService.getLoansWithPagination(eq("SUBMITTED"), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(pageResult);

        // When & Then
        mockMvc.perform(get("/api/v1/loan-applications")
                        .param("status", "SUBMITTED")
                        .param("start_date", "2026-06-01T00:00:00")
                        .param("end_date", "2026-06-23T23:59:59")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].id").value(10L));
    }

    @Test
    @DisplayName("Happy Path: Ambil Pinjaman Berdasarkan Customer ID HTTP 200 Ok")
    void getByCustomer_HappyPath_Ok() throws Exception {
        // Given
        when(loanService.getByCustomerId(1L)).thenReturn(List.of(loanResponse));

        // When & Then
        mockMvc.perform(get("/api/v1/customers/1/loan-applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", org.hamcrest.Matchers.hasSize(1)));
    }

    @Test
    @DisplayName("Happy Path: Patch Status Pengajuan Pinjaman HTTP 200 Ok")
    void updateStatus_HappyPath_Ok() throws Exception {
        // Given
        loanResponse.setStatus("APPROVED");
        when(loanService.updateStatus(eq(10L), eq("APPROVED"))).thenReturn(loanResponse);

        // When & Then
        mockMvc.perform(patch("/api/v1/loan-applications/10/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("APPROVED"));
    }

    @Test
    @DisplayName("Happy Path: Tarik Ringkasan Total Data Projection (Reporting Matrix) HTTP 200 Ok")
    void getLoanSummary_HappyPath_Ok() throws Exception {
        // Given
        LoanStatusSummaryDto mockSummary = mock(LoanStatusSummaryDto.class);
        when(loanService.getStatusSummary()).thenReturn(List.of(mockSummary));

        // When & Then
        mockMvc.perform(get("/api/v1/loan-applications/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    // --- SECTION 2: NEGATIVE PATHS ---

    @Test
    @DisplayName("Negative Path: Update Status Gagal karena Pelanggaran Aturan Bisnis State Machine HTTP 400")
    void updateStatus_NegativePath_BadRequest() throws Exception {
        // Given
        when(loanService.updateStatus(eq(10L), anyString()))
                .thenThrow(new IllegalArgumentException("SUBMITTED loan can only transition to APPROVED or REJECTED"));

        // When & Then
        mockMvc.perform(patch("/api/v1/loan-applications/10/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.correlation_id").exists());
    }
}