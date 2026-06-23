package com.example.training_2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.training_2.dto.CreateCustomerRequest;
import com.example.training_2.dto.CustomerResponse;
import com.example.training_2.service.CustomerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void shouldCreateCustomer() throws Exception {

        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFullName("Tony Stark");
        request.setNik("3174082612020144");
        request.setEmail("tony@stark.com");
        request.setPhoneNumber("08123456789");

        CustomerResponse response = CustomerResponse.builder()
                .id(1L)
                .fullName("Tony Stark")
                .nik("3174082612020144")
                .email("tony@stark.com")
                .phoneNumber("08123456789")
                .build();

        when(customerService.create(any(CreateCustomerRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("Successfully Add Data Customer"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.fullName")
                        .value("Tony Stark"));
    }

    @Test
    void shouldGetAllCustomers() throws Exception {

        List<CustomerResponse> responses = List.of(
                CustomerResponse.builder()
                        .id(1L)
                        .fullName("Tony Stark")
                        .build(),
                CustomerResponse.builder()
                        .id(2L)
                        .fullName("Steve Rogers")
                        .build());

        when(customerService.getAll(null))
                .thenReturn(responses);

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2));
    }
}
