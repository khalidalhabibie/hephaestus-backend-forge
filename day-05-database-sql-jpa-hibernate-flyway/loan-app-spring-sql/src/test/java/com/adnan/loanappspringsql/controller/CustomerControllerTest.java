package com.adnan.loanappspringsql.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.adnan.loanappspringsql.dto.CreateCustomerRequest;
import com.adnan.loanappspringsql.dto.CustomerResponse;
import com.adnan.loanappspringsql.dto.LoanApplicationResponse;
import com.adnan.loanappspringsql.service.impl.CustomerServiceImpl;
import com.adnan.loanappspringsql.service.impl.LoanApplicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper objectMapper = new ObjectMapper();

  @MockitoBean
  private CustomerServiceImpl customerService;
  @MockitoBean
  private LoanApplicationServiceImpl loanApplicationService;

  @InjectMocks
  private CustomerController customerController;

  @Test
  void create_shouldReturnCreated() throws Exception {
    CreateCustomerRequest request = CreateCustomerRequest.builder()
        .fullName("Budi Santoso")
        .nik("3173010101900001")
        .email("budi@mail.com")
        .phoneNumber("08123456789")
        .build();
    CustomerResponse response = CustomerResponse.builder()
        .id(1L)
        .fullName(request.getFullName())
        .nik(request.getNik())
        .email(request.getEmail())
        .phoneNumber(request.getPhoneNumber())
        .build();

    when(customerService.create(any())).thenReturn(response);

    mockMvc.perform(post("/api/v1/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Customer created successfully"))
        .andExpect(jsonPath("$.data.id").value(1))
        .andExpect(jsonPath("$.data.full_name").value("Budi Santoso"));
  }

  @Test
  void create_shouldReturnBadRequest_whenRequestInvalid() throws Exception {
    CreateCustomerRequest request = CreateCustomerRequest.builder().build();

    mockMvc.perform(post("/api/v1/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findById_shouldReturnCustomer() throws Exception {
    CustomerResponse response = CustomerResponse.builder()
        .id(1L)
        .fullName("Budi")
        .nik("3173010101900001")
        .email("budi@mail.com")
        .build();

    when(customerService.findById(1L)).thenReturn(response);

    mockMvc.perform(get("/api/v1/customers/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id").value(1))
        .andExpect(jsonPath("$.data.full_name").value("Budi"));
  }

  @Test
  void findAll_shouldReturnCustomers() throws Exception {
    CustomerResponse response = CustomerResponse.builder()
        .id(1L)
        .fullName("Budi")
        .build();

    when(customerService.findAll()).thenReturn(List.of(response));

    mockMvc.perform(get("/api/v1/customers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()").value(1));
  }

  @Test
  void search_shouldReturnCustomers() throws Exception {
    CustomerResponse response = CustomerResponse.builder()
        .id(1L)
        .fullName("Budi")
        .build();

    when(customerService.search("Budi")).thenReturn(List.of(response));

    mockMvc.perform(get("/api/v1/customers/search")
        .param("name", "Budi"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()").value(1));
  }

  @Test
  void findByCustomerId_shouldReturnLoans() throws Exception {
    LoanApplicationResponse response = LoanApplicationResponse.builder()
        .id(1L)
        .purpose("Motor")
        .build();

    when(loanApplicationService.findByCustomerId(1L)).thenReturn(List.of(response));

    mockMvc.perform(
        get("/api/v1/customers/1/loan-applications"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()").value(1));
  }
}
