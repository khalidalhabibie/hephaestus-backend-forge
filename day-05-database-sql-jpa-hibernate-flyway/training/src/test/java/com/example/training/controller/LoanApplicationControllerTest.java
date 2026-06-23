package com.example.training.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.CustomerSummaryResponse;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.dto.UpdateLoanStatusRequest;
import com.example.training.exception.NotFoundException;
import com.example.training.service.LoanApplicationService;

@WebMvcTest(LoanApplicationController.class)
class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanApplicationService loanApplicationService;

    private UUID loanId;
    private UUID customerId;
    private LoanApplicationResponse loanResponse;

    @BeforeEach
    void setUp() {
        loanId = UUID.randomUUID();
        customerId = UUID.randomUUID();

        CustomerSummaryResponse customer = CustomerSummaryResponse.builder()
                .id(customerId)
                .fullName("Test User")
                .nik("1234567890123456")
                .email("test@mail.com")
                .phoneNumber("08123456789")
                .build();

        loanResponse = LoanApplicationResponse.builder()
                .id(loanId)
                .customer(customer)
                .loanAmount(new BigDecimal("5000000"))
                .tenorMonth(12)
                .purpose("Home renovation")
                .status("SUBMITTED")
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }

    // --- create ---

    @Test
    void create_ShouldReturnSuccess() throws Exception {
        when(loanApplicationService.create(any(CreateLoanApplicationRequest.class)))
                .thenReturn(loanResponse);

        String body = String.format("""
                {
                    "customer_id": "%s",
                    "loan_amount": 5000000,
                    "tenor_month": 12,
                    "purpose": "Home renovation"
                }""", customerId);

        mockMvc.perform(post("/api/v1/loan-applications")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("SUBMITTED"));
    }

    @Test
    void create_ShouldReturnBadRequestWhenInvalid() throws Exception {
        String body = String.format("""
                {
                    "customer_id": "%s",
                    "loan_amount": -1,
                    "tenor_month": 0
                }""", customerId);

        mockMvc.perform(post("/api/v1/loan-applications")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void create_ShouldReturnNotFoundWhenCustomerMissing() throws Exception {
        when(loanApplicationService.create(any(CreateLoanApplicationRequest.class)))
                .thenThrow(new NotFoundException("CUSTOMER_NOT_FOUND", "Customer not found"));

        String body = String.format("""
                {
                    "customer_id": "%s",
                    "loan_amount": 5000000,
                    "tenor_month": 12,
                    "purpose": "Test"
                }""", customerId);

        mockMvc.perform(post("/api/v1/loan-applications")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CUSTOMER_NOT_FOUND"));
    }

    // --- getById ---

    @Test
    void getById_ShouldReturnSuccess() throws Exception {
        when(loanApplicationService.findById(loanId)).thenReturn(loanResponse);

        mockMvc.perform(get("/api/v1/loan-applications/{id}", loanId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(loanId.toString()));
    }

    @Test
    void getById_ShouldReturnNotFound() throws Exception {
        when(loanApplicationService.findById(loanId))
                .thenThrow(new NotFoundException("LOAN_APPLICATION_NOT_FOUND", "Not found"));

        mockMvc.perform(get("/api/v1/loan-applications/{id}", loanId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("LOAN_APPLICATION_NOT_FOUND"));
    }

    // --- getAll ---

    @Test
    void getAll_ShouldReturnAll() throws Exception {
        when(loanApplicationService.findAll()).thenReturn(List.of(loanResponse));

        mockMvc.perform(get("/api/v1/loan-applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    void getAll_ShouldReturnEmpty() throws Exception {
        when(loanApplicationService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/loan-applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    void getAll_WithBlankStatus_ShouldReturnAll() throws Exception {
        when(loanApplicationService.findAll()).thenReturn(List.of(loanResponse));

        mockMvc.perform(get("/api/v1/loan-applications").param("status", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));

        verify(loanApplicationService, never()).findByStatus(any());
    }

    @Test
    void getAll_WithStatusFilter_ShouldReturnFiltered() throws Exception {
        when(loanApplicationService.findByStatus("submitted"))
                .thenReturn(List.of(loanResponse));

        mockMvc.perform(get("/api/v1/loan-applications").param("status", "submitted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    void getAll_WithInvalidStatus_ShouldReturnBadRequest() throws Exception {
        when(loanApplicationService.findByStatus("invalid"))
                .thenThrow(new IllegalArgumentException("Invalid status"));

        mockMvc.perform(get("/api/v1/loan-applications").param("status", "invalid"))
                .andExpect(status().isInternalServerError());
    }

    // --- getByCustomerId ---

    @Test
    void getByCustomerId_ShouldReturnLoans() throws Exception {
        when(loanApplicationService.findByCustomerId(customerId))
                .thenReturn(List.of(loanResponse));

        mockMvc.perform(get("/api/v1/customers/{customerId}/loan-applications", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    void getByCustomerId_ShouldReturnNotFoundWhenCustomerMissing() throws Exception {
        when(loanApplicationService.findByCustomerId(customerId))
                .thenThrow(new NotFoundException("CUSTOMER_NOT_FOUND", "Customer not found"));

        mockMvc.perform(get("/api/v1/customers/{customerId}/loan-applications", customerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CUSTOMER_NOT_FOUND"));
    }

    // --- updateStatus ---

    @Test
    void updateStatus_ShouldReturnSuccess() throws Exception {
        when(loanApplicationService.updateStatus(eq(loanId), any(UpdateLoanStatusRequest.class)))
                .thenReturn(loanResponse);

        String body = """
                {"status": "APPROVED"}""";

        mockMvc.perform(patch("/api/v1/loan-applications/{id}/status", loanId)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void updateStatus_ShouldReturnNotFound() throws Exception {
        when(loanApplicationService.updateStatus(eq(loanId), any(UpdateLoanStatusRequest.class)))
                .thenThrow(new NotFoundException("LOAN_APPLICATION_NOT_FOUND", "Not found"));

        String body = """
                {"status": "APPROVED"}""";

        mockMvc.perform(patch("/api/v1/loan-applications/{id}/status", loanId)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStatus_ShouldReturnBadRequestWhenInvalid() throws Exception {
        String body = """
                {"status": ""}""";

        mockMvc.perform(patch("/api/v1/loan-applications/{id}/status", loanId)
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
