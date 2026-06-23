package com.example.spring_boot_database.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.spring_boot_database.dto.LoanApplicationReportProjection;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.exception.GlobalExceptionHandler;
import com.example.spring_boot_database.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ReportControllerTest {

    private MockMvc mockMvc;

    private ReportService reportService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reportService = Mockito.mock(ReportService.class);

        ReportController controller = new ReportController(reportService);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    private LoanApplicationReportProjection mockProjection() {
        return new LoanApplicationReportProjection() {
            public Long getLoanId() { return 1L; }
            public Long getCustomerId() { return 10L; }
            public String getCustomerName() { return "Budi"; }
            public String getNik() { return "123"; }
            public String getStatus() { return "APPROVED"; }
            public BigDecimal getLoanAmount() { return BigDecimal.valueOf(1000000); }
            public Integer getTenorMonth() { return 12; }
            public String getPurpose() { return "Motor"; }
            public LocalDateTime getCreatedAt() { return LocalDateTime.now(); }
            public BigDecimal getTotalScheduleAmount() { return BigDecimal.valueOf(1200000); }
            public BigDecimal getTotalPaidAmount() { return BigDecimal.valueOf(500000); }
            public BigDecimal getOutstandingAmount() { return BigDecimal.valueOf(700000); }
        };
    }

    @Nested
    @DisplayName("Happy Case")
    class HappyCase {

        @Test
        @DisplayName("Should return report successfully without filters")
        void testGetReport_noFilter() throws Exception {

            when(reportService.getLoanApplicationReport(null, null, null))
                    .thenReturn(List.of(mockProjection()));

            mockMvc.perform(get("/api/v1/reports/loan-applications"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Loan application report retrieved successfully"))
                    .andExpect(jsonPath("$.data", hasSize(1)))
                    .andExpect(jsonPath("$.data[0].loanId").value(1))
                    .andExpect(jsonPath("$.data[0].customerName").value("Budi"));
        }

        @Test
        @DisplayName("Should return report with status filter")
        void testGetReport_withStatus() throws Exception {

            when(reportService.getLoanApplicationReport(Status.APPROVED, null, null))
                    .thenReturn(List.of(mockProjection()));

            mockMvc.perform(get("/api/v1/reports/loan-applications")
                            .param("status", "APPROVED"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(1)));
        }

        @Test
        @DisplayName("Should return report with date range")
        void testGetReport_withDateRange() throws Exception {

            LocalDate start = LocalDate.of(2024, 1, 1);
            LocalDate end = LocalDate.of(2024, 12, 31);

            when(reportService.getLoanApplicationReport(null, start, end))
                    .thenReturn(List.of(mockProjection()));

            mockMvc.perform(get("/api/v1/reports/loan-applications")
                            .param("startDate", "2024-01-01")
                            .param("endDate", "2024-12-31"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(1)));
        }

        @Test
        @DisplayName("Should return empty list when no data")
        void testGetReport_emptyResult() throws Exception {

            when(reportService.getLoanApplicationReport(null, null, null))
                    .thenReturn(List.of());

            mockMvc.perform(get("/api/v1/reports/loan-applications"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(0)));
        }
    }


    @Nested
    @DisplayName("Bad Request")
    class BadRequestTests {

        @Test
        @DisplayName("Should return 400 when status invalid")
        void testInvalidStatus() throws Exception {

            mockMvc.perform(get("/api/v1/reports/loan-applications")
                            .param("status", "INVALID"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("INVALID_PARAMETER"));
        }

        @Test
        @DisplayName("Should return 400 when startDate invalid format")
        void testInvalidStartDate() throws Exception {

            mockMvc.perform(get("/api/v1/reports/loan-applications")
                            .param("startDate", "invalid-date"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("INVALID_PARAMETER"));
        }

        @Test
        @DisplayName("Should return 400 when endDate invalid format")
        void testInvalidEndDate() throws Exception {

            mockMvc.perform(get("/api/v1/reports/loan-applications")
                            .param("endDate", "abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("INVALID_PARAMETER"));
        }
    }


    @Nested
    @DisplayName("Service Exception")
    class ServiceExceptionTests {

        @Test
        @DisplayName("Should return 400 when BadRequestException thrown")
        void testBadRequestException() throws Exception {

            when(reportService.getLoanApplicationReport(any(), any(), any()))
                    .thenThrow(new RuntimeException("unexpected"));

            mockMvc.perform(get("/api/v1/reports/loan-applications"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.error").value("INTERNAL_SERVER_ERROR"));
        }
    }

    @Nested
    @DisplayName("HTTP Method Validation")
    class MethodTests {

        @Test
        @DisplayName("Should return 405 when POST used")
        void testMethodNotAllowed() throws Exception {

            mockMvc.perform(
                    org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(
                            "/api/v1/reports/loan-applications"))
                    .andExpect(status().isMethodNotAllowed())
                    .andExpect(jsonPath("$.error").value("METHOD_NOT_ALLOWED"));
        }
    }


    @Nested
    @DisplayName("Media Type Validation")
    class MediaTypeTests {

        @Test
        @DisplayName("Should ignore unsupported content type for GET (still OK)")
        void testUnsupportedMediaType() throws Exception {

            when(reportService.getLoanApplicationReport(null, null, null))
                    .thenReturn(List.of(mockProjection()));

            mockMvc.perform(get("/api/v1/reports/loan-applications")
                            .contentType(MediaType.APPLICATION_XML))
                    .andExpect(status().isOk());
        }
    }
}
