package com.example.demoSpringbootDatabase.controller;

import com.example.demoSpringbootDatabase.dto.ApiResponse;
import com.example.demoSpringbootDatabase.dto.CreateCustomerRequest;
import com.example.demoSpringbootDatabase.dto.CustomerResponse;
import com.example.demoSpringbootDatabase.exception.CustomerNotFoundException;
import com.example.demoSpringbootDatabase.exception.DuplicateCustomerException;
import com.example.demoSpringbootDatabase.service.CustomerService;
import com.example.demoSpringbootDatabase.service.RepaymentScheduleService;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService customerService;

    // ✅ Wajib di-mock karena ter-inject di constructor controller
    @MockitoBean
    private RepaymentScheduleService repaymentScheduleService; 

    private CreateCustomerRequest createRequest;
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        createRequest = new CreateCustomerRequest();
        createRequest.setFullName("Budi Santoso");
        createRequest.setNik("3275010203040005");
        createRequest.setEmail("budi@mail.com");
        createRequest.setPhoneNumber("08123456789");

        customerResponse = CustomerResponse.builder()
                .id(1L)
                .fullName("Budi Santoso")
                .nik("3275010203040005")
                .email("budi@mail.com")
                .phoneNumber("08123456789")
                .build();
                
    }

    // --- SECTION 1: HAPPY PATHS ---

    @Test
    @DisplayName("Happy Path: Create Customer via API HTTP 201 dengan Filter Header Correlation ID")
    void createCustomer_HappyPath_Created() throws Exception {
        // Given
        when(customerService.createCustomer(any(CreateCustomerRequest.class))).thenReturn(customerResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/customers")
                        .header("X-Correlation-Id", "REQ-FORGE-2026-01")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("X-Correlation-Id", "REQ-FORGE-2026-01"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customer created successfully"))
                .andExpect(jsonPath("$.data.full_name").value("Budi Santoso"))
                .andExpect(jsonPath("$.data.nik").value("3275010203040005"));
    }

    @Test
    @DisplayName("Happy Path: Get Customer By ID HTTP 200 Ok")
    void getById_HappyPath_Ok() throws Exception {
        // Given
        when(customerService.getById(1L)).thenReturn(customerResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @DisplayName("Happy Path: Ambil Seluruh Daftar Customer HTTP 200 Ok")
    void getAll_HappyPath_Ok() throws Exception {
        // Given
        when(customerService.getAll()).thenReturn(List.of(customerResponse));

        // When & Then
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", org.hamcrest.Matchers.hasSize(1)));
    }

    @Test
    @DisplayName("Happy Path: Pencarian Nama Customer HTTP 200 Ok")
    void search_HappyPath_Ok() throws Exception {
        // Given
        when(customerService.searchByName("Budi")).thenReturn(List.of(customerResponse));

        // When & Then
        mockMvc.perform(get("/api/v1/customers/search").param("name", "Budi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Happy Path: Ambil Nominal Outstanding Balance Finansial Nasabah HTTP 200 Ok")
    void getOutstanding_HappyPath_Ok() throws Exception {
        // Given
        BigDecimal mockOutstanding = BigDecimal.valueOf(17666665);
        when(repaymentScheduleService.getCustomerOutstanding(1L)).thenReturn(mockOutstanding);

        // When & Then
        mockMvc.perform(get("/api/v1/customers/1/outstanding"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Outstanding balance retrieved"))
                .andExpect(jsonPath("$.data").value(17666665));
    }

    // --- SECTION 2: NEGATIVE PATHS ---

    @Test
    @DisplayName("Negative Path: Create Customer Conflict HTTP 409 saat Identitas Duplikat")
    void createCustomer_NegativePath_Conflict() throws Exception {
        // Given
        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenThrow(new DuplicateCustomerException("NIK already registered"));

        // When & Then
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.correlation_id").exists());
    }

    @Test
    @DisplayName("Negative Path: Get By ID Nasabah Tidak Terdaftar HTTP 404 Not Found")
    void getById_NegativePath_NotFound() throws Exception {
        // Given
        when(customerService.getById(99L)).thenThrow(new CustomerNotFoundException(99L));

        // When & Then
        mockMvc.perform(get("/api/v1/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.correlation_id").exists());
    }
}