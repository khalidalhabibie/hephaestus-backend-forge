package com.fif.exercise2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fif.exercise2.dto.CreateLoanApplicationRequest;
import com.fif.exercise2.dto.LoanApplicationResponse;
import com.fif.exercise2.dto.LoanSummaryResponse;
import com.fif.exercise2.dto.UpdateLoanStatusRequest;
import com.fif.exercise2.exception.CustomerNotFoundException;
import com.fif.exercise2.exception.GlobalExceptionHandler;
import com.fif.exercise2.exception.InvalidLoanStatusException;
import com.fif.exercise2.exception.LoanApplicationNotFoundException;
import com.fif.exercise2.service.LoanApplicationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanApplicationController.class)
@Import(GlobalExceptionHandler.class)
class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanApplicationService loanApplicationService;

    private ObjectMapper objectMapper;

    private CreateLoanApplicationRequest request;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        request = new CreateLoanApplicationRequest();
        request.setCustomerId(1L);
        request.setLoanAmount(new BigDecimal("10000000"));
        request.setTenorMonth(12);
        request.setPurpose("Modal usaha");
    }

    @Test
    void shouldCreateLoanApplicationSuccessfully() throws Exception {

        LoanApplicationResponse response = buildLoanResponse(10L, "SUBMITTED");

        when(loanApplicationService.createLoanApplication(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/loan-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnCustomerNotFoundWhenCreateLoan() throws Exception {

        when(loanApplicationService.createLoanApplication(any()))
                .thenThrow(new CustomerNotFoundException(99L));

        mockMvc.perform(post("/api/v1/loan-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidCreateLoanRequest() throws Exception {

        CreateLoanApplicationRequest invalidRequest = new CreateLoanApplicationRequest();

        mockMvc.perform(post("/api/v1/loan-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetLoanByIdSuccessfully() throws Exception {

        LoanApplicationResponse response = buildLoanResponse(1L, "SUBMITTED");

        when(loanApplicationService.getLoanApplicationById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/loan-applications/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnLoanNotFound() throws Exception {

        when(loanApplicationService.getLoanApplicationById(99L))
                .thenThrow(new LoanApplicationNotFoundException(99L));

        mockMvc.perform(get("/api/v1/loan-applications/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllLoansSuccessfully() throws Exception {

        when(loanApplicationService.getAllLoanApplications())
                .thenReturn(List.of(buildLoanResponse(1L, "SUBMITTED")));

        mockMvc.perform(get("/api/v1/loan-applications"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetLoansByStatusSuccessfully() throws Exception {

        when(loanApplicationService.getLoansByStatus("APPROVED"))
                .thenReturn(List.of(buildLoanResponse(1L, "APPROVED")));

        mockMvc.perform(get("/api/v1/loan-applications")
                        .param("status", "APPROVED"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetLoansByDateSuccessfully() throws Exception {

        when(loanApplicationService.getLoansByDate(any()))
                .thenReturn(List.of(buildLoanResponse(1L, "SUBMITTED")));

        mockMvc.perform(get("/api/v1/loan-applications")
                        .param("date", "2024-01-01"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetLoansByDateRangeSuccessfully() throws Exception {

        when(loanApplicationService.getLoansByDateRange(any(), any()))
                .thenReturn(List.of(buildLoanResponse(1L, "SUBMITTED")));

        mockMvc.perform(get("/api/v1/loan-applications")
                        .param("start", "2024-01-01T00:00:00Z")
                        .param("end", "2024-12-31T23:59:59Z"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetLoansByCustomerIdSuccessfully() throws Exception {

        when(loanApplicationService.getLoansByCustomerId(1L))
                .thenReturn(List.of(buildLoanResponse(1L, "SUBMITTED")));

        mockMvc.perform(get("/api/v1/customers/1/loan-applications"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateLoanStatusSuccessfully() throws Exception {

        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
        request.setStatus("APPROVED");

        LoanApplicationResponse response = buildLoanResponse(1L, "APPROVED");

        when(loanApplicationService.updateLoanStatus(eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(patch("/api/v1/loan-applications/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnInvalidStatusError() throws Exception {

        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
        request.setStatus("DISBURSED");

        when(loanApplicationService.updateLoanStatus(eq(1L), any()))
                .thenThrow(new InvalidLoanStatusException("Invalid status"));

        mockMvc.perform(patch("/api/v1/loan-applications/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetPagedLoansSuccessfully() throws Exception {

        Page<LoanApplicationResponse> page =
                new PageImpl<>(List.of(buildLoanResponse(1L, "SUBMITTED")));

        when(loanApplicationService.getAllLoanApplicationsPaged(0, 10, null))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/loan-applications/paged"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetLoanSummarySuccessfully() throws Exception {

        List<LoanSummaryResponse> summary =
                List.of(new LoanSummaryResponse("SUBMITTED", 3L, new BigDecimal("15000000")));

        when(loanApplicationService.getLoanSummaryByStatus())
                .thenReturn(summary);

        mockMvc.perform(get("/api/v1/loan-applications/summary"))
                .andExpect(status().isOk());
    }

    // ===== HELPER =====
    private LoanApplicationResponse buildLoanResponse(Long id, String status) {

        LoanApplicationResponse response = new LoanApplicationResponse();

        response.setId(id);
        response.setStatus(status);
        response.setLoanAmount(new BigDecimal("10000000"));
        response.setTenorMonth(12);
        response.setPurpose("Modal usaha");
        response.setCreatedAt(ZonedDateTime.now());
        response.setUpdatedAt(ZonedDateTime.now());

        return response;
    }
}
