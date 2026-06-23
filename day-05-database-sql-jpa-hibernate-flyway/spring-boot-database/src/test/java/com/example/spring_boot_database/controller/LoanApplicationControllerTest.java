package com.example.spring_boot_database.controller;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.CreateLoanApplicationRequest;
import com.example.spring_boot_database.dto.CustomerResponse;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.dto.LoanSummaryByStatusResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.dto.UpdateLoanStatusRequest;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.exception.CustomerNotFoundException;
import com.example.spring_boot_database.exception.LoanNotFoundException;
import com.example.spring_boot_database.service.LoanApplicationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanApplicationController.class)
class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanApplicationService loanService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CreateLoanApplicationRequest createRequest;
    private UpdateLoanStatusRequest updateStatusRequest;

    private CustomerResponse customerResponse;
    private LoanApplicationResponse loanResponse;
    private RepaymentScheduleResponse scheduleResponse;
    private LoanSummaryByStatusResponse summaryResponse;

    @BeforeEach
    void setUp() {
        createRequest = CreateLoanApplicationRequest.builder()
                .customerId(1L)
                .loanAmount(new BigDecimal("10000000"))
                .tenorMonth(12)
                .purpose("Modal usaha")
                .build();

        updateStatusRequest = UpdateLoanStatusRequest.builder()
                .status(Status.APPROVED)
                .build();

        customerResponse = CustomerResponse.builder()
                .id(1L)
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .phoneNumber("08123456789")
                .build();

        loanResponse = LoanApplicationResponse.builder()
                .loanAmount(new BigDecimal("10000000"))
                .tenorMonth(12)
                .purpose("Modal usaha")
                .status(Status.SUBMITTED)
                .customer(customerResponse)
                .build();

        scheduleResponse = RepaymentScheduleResponse.builder()
                .build();

        summaryResponse = LoanSummaryByStatusResponse.builder()
                .status(Status.SUBMITTED)
                .totalLoan(2L)
                .totalLoanAmount(new BigDecimal("20000000"))
                .build();
    }

    @Test
    void create_success() throws Exception {
        when(loanService.createLoanApplication(any(CreateLoanApplicationRequest.class)))
                .thenReturn(loanResponse);

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<LoanApplicationResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Loan application created successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());

                    assertEquals(new BigDecimal("10000000"), response.getData().getLoanAmount());
                    assertEquals(12, response.getData().getTenorMonth());
                    assertEquals("Modal usaha", response.getData().getPurpose());
                    assertEquals(Status.SUBMITTED, response.getData().getStatus());

                    assertNotNull(response.getData().getCustomer());
                    assertEquals(1L, response.getData().getCustomer().getId());
                    assertEquals("Budi", response.getData().getCustomer().getFullName());
                });

        ArgumentCaptor<CreateLoanApplicationRequest> captor =
                ArgumentCaptor.forClass(CreateLoanApplicationRequest.class);

        verify(loanService, times(1)).createLoanApplication(captor.capture());

        assertEquals(1L, captor.getValue().getCustomerId());
        assertEquals(new BigDecimal("10000000"), captor.getValue().getLoanAmount());
        assertEquals(12, captor.getValue().getTenorMonth());
        assertEquals("Modal usaha", captor.getValue().getPurpose());
    }

    @Test
    void create_failed_customer_not_found() throws Exception {
        when(loanService.createLoanApplication(any(CreateLoanApplicationRequest.class)))
                .thenThrow(new CustomerNotFoundException(1L));

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isNotFound())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("CUSTOMER_NOT_FOUND", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(loanService, times(1)).createLoanApplication(any(CreateLoanApplicationRequest.class));
    }

    @Test
    void create_failed_validation_customerId_null() throws Exception {
        createRequest.setCustomerId(null);

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("VALIDATION_ERROR", response.getError());
                    assertEquals("Invalid request", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("customerId")
                                    && detail.contains("customer_id is required")));
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_validation_loanAmount_null() throws Exception {
        createRequest.setLoanAmount(null);

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("VALIDATION_ERROR", response.getError());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("loanAmount")
                                    && detail.contains("loan_amount is required")));
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_validation_loanAmount_zero() throws Exception {
        createRequest.setLoanAmount(BigDecimal.ZERO);

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("VALIDATION_ERROR", response.getError());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("loanAmount")
                                    && detail.contains("loan_amount must be greater than 0")));
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_validation_tenorMonth_null() throws Exception {
        createRequest.setTenorMonth(null);

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("VALIDATION_ERROR", response.getError());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("tenorMonth")
                                    && detail.contains("tenor_month is required")));
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_validation_tenorMonth_negative() throws Exception {
        createRequest.setTenorMonth(-1);

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("VALIDATION_ERROR", response.getError());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("tenorMonth")
                                    && detail.contains("tenor_month must be greater than 0")));
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_validation_purpose_blank() throws Exception {
        createRequest.setPurpose(" ");

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("VALIDATION_ERROR", response.getError());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("purpose")
                                    && detail.contains("purpose is required")));
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_validation_multiple_fields() throws Exception {
        createRequest.setCustomerId(null);
        createRequest.setLoanAmount(BigDecimal.ZERO);
        createRequest.setTenorMonth(0);
        createRequest.setPurpose("");

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("VALIDATION_ERROR", response.getError());
                    assertEquals("Invalid request", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().size() >= 4);
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_malformed_json() throws Exception {
        String malformedJson = """
                {
                    "customer_id": 1,
                    "loan_amount": 10000000,
                    "tenor_month": 12,
                    "purpose": "Modal usaha"
                """;

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("MALFORMED_JSON", response.getError());
                    assertEquals("Request body is invalid or malformed", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_unsupported_media_type() throws Exception {
        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("plain text")
        ).andExpect(status().isUnsupportedMediaType())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("UNSUPPORTED_MEDIA_TYPE", response.getError());
                    assertEquals("Content type is not supported", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                });

        verify(loanService, never()).createLoanApplication(any());
    }

    @Test
    void create_failed_unexpected_exception() throws Exception {
        when(loanService.createLoanApplication(any(CreateLoanApplicationRequest.class)))
                .thenThrow(new RuntimeException("Unexpected failure"));

        mockMvc.perform(
                post("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
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
                    assertTrue(response.getDetails().contains("RuntimeException"));
                });

        verify(loanService, times(1)).createLoanApplication(any(CreateLoanApplicationRequest.class));
    }

    @Test
    void getById_success() throws Exception {
        when(loanService.findById(1L)).thenReturn(loanResponse);

        mockMvc.perform(
                get("/api/v1/loan-applications/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<LoanApplicationResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Loan application retrieved successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(Status.SUBMITTED, response.getData().getStatus());
                    assertEquals("Modal usaha", response.getData().getPurpose());
                });

        verify(loanService, times(1)).findById(1L);
    }

    @Test
    void getById_failed_loan_not_found() throws Exception {
        when(loanService.findById(99L))
                .thenThrow(new LoanNotFoundException(99L));

        mockMvc.perform(
                get("/api/v1/loan-applications/{id}", 99L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("LOAN_NOT_FOUND", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(loanService, times(1)).findById(99L);
    }

    @Test
    void getById_failed_invalid_id_type() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications/{id}", "abc")
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
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("id")
                                    && detail.contains("invalid value")));
                });

        verify(loanService, never()).findById(anyLong());
    }

    @Test
    void getAll_success_without_filter() throws Exception {
        when(loanService.findLoan(null, null, null))
                .thenReturn(List.of(loanResponse));

        mockMvc.perform(
                get("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<LoanApplicationResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Loan applications retrieved successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(1, response.getData().size());
                });

        verify(loanService, times(1)).findLoan(null, null, null);
    }

    @Test
    void getAll_success_with_all_filters() throws Exception {
        when(loanService.findLoan(
                eq(Status.SUBMITTED),
                eq(LocalDate.of(2025, 1, 1)),
                eq(LocalDate.of(2025, 1, 31))
        )).thenReturn(List.of(loanResponse));

        mockMvc.perform(
                get("/api/v1/loan-applications")
                        .param("status", "SUBMITTED")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-31")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<LoanApplicationResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Loan applications retrieved successfully", response.getMessage());
                    assertNotNull(response.getData());
                    assertEquals(1, response.getData().size());
                });

        verify(loanService, times(1))
                .findLoan(Status.SUBMITTED, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 31));
    }

    @Test
    void getAll_success_empty_result() throws Exception {
        when(loanService.findLoan(null, null, null))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/loan-applications")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<LoanApplicationResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertNotNull(response.getData());
                    assertTrue(response.getData().isEmpty());
                });

        verify(loanService, times(1)).findLoan(null, null, null);
    }

    @Test
    void getAll_failed_invalid_status_enum() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications")
                        .param("status", "INVALID_STATUS")
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
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("status")
                                    && detail.contains("invalid value")));
                });

        verify(loanService, never()).findLoan(any(), any(), any());
    }

    @Test
    void getAll_failed_invalid_startDate_format() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications")
                        .param("startDate", "01-01-2025")
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
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("startDate")
                                    && detail.contains("invalid value")));
                });

        verify(loanService, never()).findLoan(any(), any(), any());
    }

    @Test
    void getAll_failed_invalid_date_range_from_service() throws Exception {
        when(loanService.findLoan(
                eq(null),
                eq(LocalDate.of(2025, 2, 1)),
                eq(LocalDate.of(2025, 1, 1))
        )).thenThrow(new BadRequestException("startDate cannot be after endDate"));

        mockMvc.perform(
                get("/api/v1/loan-applications")
                        .param("startDate", "2025-02-01")
                        .param("endDate", "2025-01-01")
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
                    assertEquals("startDate cannot be after endDate", response.getMessage());
                    assertNull(response.getData());
                });

        verify(loanService, times(1))
                .findLoan(null, LocalDate.of(2025, 2, 1), LocalDate.of(2025, 1, 1));
    }



    @Test
    void updateStatus_success() throws Exception {
        LoanApplicationResponse approvedResponse = LoanApplicationResponse.builder()
                .loanAmount(new BigDecimal("10000000"))
                .tenorMonth(12)
                .purpose("Modal usaha")
                .status(Status.APPROVED)
                .customer(customerResponse)
                .build();

        when(loanService.updateStatus(eq(1L), any(UpdateLoanStatusRequest.class)))
                .thenReturn(approvedResponse);

        mockMvc.perform(
                patch("/api/v1/loan-applications/{id}/status", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStatusRequest))
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<LoanApplicationResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Loan status updated successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(Status.APPROVED, response.getData().getStatus());
                });

        ArgumentCaptor<UpdateLoanStatusRequest> captor =
                ArgumentCaptor.forClass(UpdateLoanStatusRequest.class);

        verify(loanService, times(1)).updateStatus(eq(1L), captor.capture());
        assertEquals(Status.APPROVED, captor.getValue().getStatus());
    }

    @Test
    void updateStatus_failed_loan_not_found() throws Exception {
        when(loanService.updateStatus(eq(99L), any(UpdateLoanStatusRequest.class)))
                .thenThrow(new LoanNotFoundException(99L));

        mockMvc.perform(
                patch("/api/v1/loan-applications/{id}/status", 99L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStatusRequest))
        ).andExpect(status().isNotFound())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("LOAN_NOT_FOUND", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(loanService, times(1)).updateStatus(eq(99L), any(UpdateLoanStatusRequest.class));
    }

    @Test
    void updateStatus_failed_bad_request_invalid_transition() throws Exception {
        when(loanService.updateStatus(eq(1L), any(UpdateLoanStatusRequest.class)))
                .thenThrow(new BadRequestException("Invalid transition"));

        mockMvc.perform(
                patch("/api/v1/loan-applications/{id}/status", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStatusRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("BAD_REQUEST", response.getError());
                    assertEquals("Invalid transition", response.getMessage());
                    assertNull(response.getData());
                });

        verify(loanService, times(1)).updateStatus(eq(1L), any(UpdateLoanStatusRequest.class));
    }

    @Test
    void updateStatus_failed_malformed_json() throws Exception {
        String malformedJson = """
                {
                    "status": "APPROVED"
                """;

        mockMvc.perform(
                patch("/api/v1/loan-applications/{id}/status", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("MALFORMED_JSON", response.getError());
                    assertEquals("Request body is invalid or malformed", response.getMessage());
                    assertNull(response.getData());
                });

        verify(loanService, never()).updateStatus(anyLong(), any());
    }

    @Test
    void updateStatus_failed_invalid_status_enum_in_body() throws Exception {
        String invalidJson = """
                {
                    "status": "INVALID_STATUS"
                }
                """;

        mockMvc.perform(
                patch("/api/v1/loan-applications/{id}/status", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

       
                    assertFalse(response.isSuccess());
                    assertEquals("MALFORMED_JSON", response.getError());
                    assertEquals("Request body is invalid or malformed", response.getMessage());
                    assertNull(response.getData());
                });

        verify(loanService, never()).updateStatus(anyLong(), any());
    }

    @Test
    void updateStatus_failed_invalid_id_type() throws Exception {
        mockMvc.perform(
                patch("/api/v1/loan-applications/{id}/status", "abc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStatusRequest))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("INVALID_PARAMETER", response.getError());
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("id")
                                    && detail.contains("invalid value")));
                });

        verify(loanService, never()).updateStatus(anyLong(), any());
    }

 

    @Test
    void getSchedules_success() throws Exception {
        when(loanService.getSchedules(1L))
                .thenReturn(List.of(scheduleResponse));

        mockMvc.perform(
                get("/api/v1/loan-applications/{loanId}/repayment-schedules", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<RepaymentScheduleResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Repayment schedules retrieved successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(1, response.getData().size());
                });

        verify(loanService, times(1)).getSchedules(1L);
    }

    @Test
    void getSchedules_success_empty() throws Exception {
        when(loanService.getSchedules(1L))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/loan-applications/{loanId}/repayment-schedules", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<RepaymentScheduleResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertNotNull(response.getData());
                    assertTrue(response.getData().isEmpty());
                });

        verify(loanService, times(1)).getSchedules(1L);
    }

    @Test
    void getSchedules_failed_invalid_loanId_type() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications/{loanId}/repayment-schedules", "abc")
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
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("loanId")
                                    && detail.contains("invalid value")));
                });

        verify(loanService, never()).getSchedules(anyLong());
    }



    @Test
    void getSummaryByStatus_success() throws Exception {
        when(loanService.getSummaryByStatus())
                .thenReturn(List.of(summaryResponse));

        mockMvc.perform(
                get("/api/v1/loan-applications/summary/by-status")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(
                            result.getResponse().getContentAsString()
                    );

                    assertTrue(root.get("success").asBoolean());
                    assertEquals(
                            "Loan summary by status retrieved successfully",
                            root.get("message").asText()
                    );

                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));
                    assertTrue(root.get("data").isArray());
                    assertEquals(1, root.get("data").size());

                    JsonNode firstData = root.get("data").get(0);

                    assertEquals("SUBMITTED", firstData.get("status").asText());
                    assertEquals(2L, firstData.get("totalLoan").asLong());
                    assertEquals(
                            new BigDecimal("20000000"),
                            firstData.get("totalLoanAmount").decimalValue()
                    );
                });

        verify(loanService, times(1)).getSummaryByStatus();
    }

    @Test
    void getSummaryByStatus_success_empty() throws Exception {
        when(loanService.getSummaryByStatus())
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/loan-applications/summary/by-status")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<LoanSummaryByStatusResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertNotNull(response.getData());
                    assertTrue(response.getData().isEmpty());
                });

        verify(loanService, times(1)).getSummaryByStatus();
    }



    @Test
    void getPaged_success_without_filter_default_page_size() throws Exception {
        Page<LoanApplicationResponse> page = new PageImpl<>(List.of(loanResponse));

        when(loanService.findLoanPaged(
                isNull(),
                isNull(),
                isNull(),
                any(Pageable.class)
        )).thenReturn(page);

        mockMvc.perform(
                get("/api/v1/loan-applications/paged")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Loan applications paginated", root.get("message").asText());
                    assertTrue(root.get("error").isNull());
                    assertNotNull(root.get("data"));

                    JsonNode data = root.get("data");
                    assertTrue(data.has("content"));
                    assertEquals(1, data.get("content").size());
                    assertEquals(1, data.get("totalElements").asInt());
                });

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(loanService, times(1))
                .findLoanPaged(isNull(), isNull(), isNull(), pageableCaptor.capture());

        assertEquals(0, pageableCaptor.getValue().getPageNumber());
        assertEquals(10, pageableCaptor.getValue().getPageSize());
    }

    @Test
    void getPaged_success_with_all_filters_and_custom_page_size() throws Exception {
        Page<LoanApplicationResponse> page = new PageImpl<>(List.of(loanResponse));

        when(loanService.findLoanPaged(
                eq(Status.SUBMITTED),
                eq(LocalDate.of(2025, 1, 1)),
                eq(LocalDate.of(2025, 1, 31)),
                any(Pageable.class)
        )).thenReturn(page);

        mockMvc.perform(
                get("/api/v1/loan-applications/paged")
                        .param("status", "SUBMITTED")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-31")
                        .param("page", "2")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

                    assertTrue(root.get("success").asBoolean());
                    assertEquals("Loan applications paginated", root.get("message").asText());

                    JsonNode data = root.get("data");
                    assertTrue(data.has("content"));
                    assertEquals(1, data.get("content").size());
                });

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(loanService, times(1)).findLoanPaged(
                eq(Status.SUBMITTED),
                eq(LocalDate.of(2025, 1, 1)),
                eq(LocalDate.of(2025, 1, 31)),
                pageableCaptor.capture()
        );

        assertEquals(2, pageableCaptor.getValue().getPageNumber());
        assertEquals(5, pageableCaptor.getValue().getPageSize());
    }

    @Test
    void getPaged_failed_invalid_status_enum() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications/paged")
                        .param("status", "INVALID_STATUS")
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
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("status")
                                    && detail.contains("invalid value")));
                });

        verify(loanService, never()).findLoanPaged(any(), any(), any(), any());
    }

    @Test
    void getPaged_failed_invalid_page_type() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications/paged")
                        .param("page", "abc")
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
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("page")
                                    && detail.contains("invalid value")));
                });

        verify(loanService, never()).findLoanPaged(any(), any(), any(), any());
    }

    @Test
    void getPaged_failed_invalid_size_type() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications/paged")
                        .param("size", "abc")
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
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("size")
                                    && detail.contains("invalid value")));
                });

        verify(loanService, never()).findLoanPaged(any(), any(), any(), any());
    }

    @Test
    void getPaged_failed_negative_page_from_controller_pageRequest() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications/paged")
                        .param("page", "-1")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isInternalServerError())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    /*
                     * PageRequest.of(-1, 10) throw IllegalArgumentException.
                     * Karena GlobalExceptionHandler belum punya handler khusus IllegalArgumentException,
                     * maka masuk handler Exception => 500.
                     */
                    assertFalse(response.isSuccess());
                    assertEquals("INTERNAL_SERVER_ERROR", response.getError());
                    assertEquals("Unexpected error occurred", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().contains("IllegalArgumentException"));
                });

        verify(loanService, never()).findLoanPaged(any(), any(), any(), any());
    }

    @Test
    void getPaged_failed_zero_size_from_controller_pageRequest() throws Exception {
        mockMvc.perform(
                get("/api/v1/loan-applications/paged")
                        .param("page", "0")
                        .param("size", "0")
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
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().contains("IllegalArgumentException"));
                });

        verify(loanService, never()).findLoanPaged(any(), any(), any(), any());
    }

    @Test
    void getPaged_failed_invalid_date_range_from_service() throws Exception {
        when(loanService.findLoanPaged(
                eq(null),
                eq(LocalDate.of(2025, 2, 1)),
                eq(LocalDate.of(2025, 1, 1)),
                any(Pageable.class)
        )).thenThrow(new BadRequestException("startDate cannot be after endDate"));

        mockMvc.perform(
                get("/api/v1/loan-applications/paged")
                        .param("startDate", "2025-02-01")
                        .param("endDate", "2025-01-01")
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
                    assertEquals("startDate cannot be after endDate", response.getMessage());
                    assertNull(response.getData());
                });

        verify(loanService, times(1)).findLoanPaged(
                eq(null),
                eq(LocalDate.of(2025, 2, 1)),
                eq(LocalDate.of(2025, 1, 1)),
                any(Pageable.class)
        );
    }


    @Test
    void methodNotAllowed_deleteLoanApplications() throws Exception {
        mockMvc.perform(
                delete("/api/v1/loan-applications")
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
                    assertTrue(response.getDetails().stream()
                            .anyMatch(detail -> detail.contains("DELETE")
                                    && detail.contains("method is not allowed")));
                });
    }
}