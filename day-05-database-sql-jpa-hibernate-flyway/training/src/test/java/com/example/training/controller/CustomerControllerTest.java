package com.example.training.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.exception.DuplicateCustomerException;
import com.example.training.exception.NotFoundException;
import com.example.training.service.CustomerService;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private UUID customerId;
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customerResponse = CustomerResponse.builder()
                .id(customerId)
                .fullName("John Doe")
                .nik("1234567890123456")
                .email("john@mail.com")
                .phoneNumber("08123456789")
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }

    @Test
    void create_ShouldReturnSuccess() throws Exception {
        when(customerService.create(any(CreateCustomerRequest.class), any()))
                .thenReturn(customerResponse);

        String body = """
                {
                    "full_name": "John Doe",
                    "nik": "1234567890123456",
                    "email": "john@mail.com",
                    "phone_number": "08123456789"
                }""";

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.full_name").value("John Doe"))
                .andExpect(jsonPath("$.data.email").value("john@mail.com"));
    }

    @Test
    void create_ShouldReturnBadRequestWhenInvalid() throws Exception {
        String body = """
                {
                    "full_name": "",
                    "nik": "123",
                    "email": ""
                }""";

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void create_ShouldReturnConflictWhenDuplicate() throws Exception {
        when(customerService.create(any(CreateCustomerRequest.class), any()))
                .thenThrow(new DuplicateCustomerException("DUPLICATE_NIK", "NIK already exists"));

        String body = """
                {
                    "full_name": "John Doe",
                    "nik": "1234567890123456",
                    "email": "john@mail.com",
                    "phone_number": "08123456789"
                }""";

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("DUPLICATE_NIK"));
    }

    @Test
    void getAll_ShouldReturnList() throws Exception {
        when(customerService.findAll()).thenReturn(List.of(customerResponse));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].id").value(customerId.toString()));
    }

    @Test
    void getAll_ShouldReturnEmptyList() throws Exception {
        when(customerService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    void getById_ShouldReturnCustomer() throws Exception {
        when(customerService.findById(customerId)).thenReturn(customerResponse);

        mockMvc.perform(get("/api/v1/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(customerId.toString()));
    }

    @Test
    void getById_ShouldReturnNotFound() throws Exception {
        when(customerService.findById(customerId))
                .thenThrow(new NotFoundException("CUSTOMER_NOT_FOUND", "Customer not found"));

        mockMvc.perform(get("/api/v1/customers/{id}", customerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("CUSTOMER_NOT_FOUND"));
    }

    @Test
    void search_ShouldReturnMatching() throws Exception {
        when(customerService.searchByName("John"))
                .thenReturn(List.of(customerResponse));

        mockMvc.perform(get("/api/v1/customers/search").param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    void search_ShouldReturnEmptyWhenNoMatch() throws Exception {
        when(customerService.searchByName("Zzz")).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/customers/search").param("name", "Zzz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(0));
    }
}
