package com.fif.exercise2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fif.exercise2.dto.CreateCustomerRequest;
import com.fif.exercise2.dto.CustomerResponse;
import com.fif.exercise2.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private ObjectMapper objectMapper;

    private CreateCustomerRequest request;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        request = new CreateCustomerRequest();
        request.setFullName("Gracia Joanne");
        request.setNik("1234567890123456");
        request.setEmail("gracia@test.com");
        request.setPhoneNumber("081234567890");
    }

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {

        CustomerResponse response = buildResponse(
                1L,
                "Gracia Joanne",
                "gracia@test.com"
        );

        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value("Customer created successfully"))
                .andExpect(jsonPath("$.data.id")
                        .value(1))
                .andExpect(jsonPath("$.data.full_name")
                        .value("Gracia Joanne"));

        verify(customerService)
                .createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void shouldGetCustomerByIdSuccessfully() throws Exception {

        CustomerResponse response = buildResponse(
                1L,
                "Gracia Joanne",
                "gracia@test.com"
        );

        when(customerService.getCustomerById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/customers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Customer retrieved successfully"))
                .andExpect(jsonPath("$.data.id")
                        .value(1))
                .andExpect(jsonPath("$.data.full_name")
                        .value("Gracia Joanne"));

        verify(customerService).getCustomerById(1L);
    }

    @Test
    void shouldGetAllCustomersSuccessfully() throws Exception {

        CustomerResponse customer1 =
                buildResponse(1L, "Gracia Joanne", "gracia@test.com");

        CustomerResponse customer2 =
                buildResponse(2L, "Budi Santoso", "budi@test.com");

        when(customerService.getAllCustomers())
                .thenReturn(List.of(customer1, customer2));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Customers retrieved successfully"))
                .andExpect(jsonPath("$.data.length()")
                        .value(2));
    }

    @Test
    void shouldSearchCustomerByNameSuccessfully() throws Exception {

        CustomerResponse response =
                buildResponse(1L, "Gracia Joanne", "gracia@test.com");

        when(customerService.searchByName("Gracia"))
                .thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/api/v1/customers/search")
                        .param("name", "Gracia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Customers retrieved successfully"))
                .andExpect(jsonPath("$.data[0].full_name")
                        .value("Gracia Joanne"));

        verify(customerService).searchByName("Gracia");
    }

    @Test
    void shouldDeleteCustomerSuccessfully() throws Exception {

        mockMvc.perform(delete("/api/v1/customers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Customer deleted successfully"));

        verify(customerService).softDeleteCustomer(1L);
    }

    @Test
    void shouldReturnBadRequestWhenRequestInvalid() throws Exception {

        CreateCustomerRequest invalidRequest =
                new CreateCustomerRequest();

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    private CustomerResponse buildResponse(
            Long id,
            String fullName,
            String email
    ) {
        CustomerResponse response = new CustomerResponse();

        response.setId(id);
        response.setFullName(fullName);
        response.setNik("1234567890123456");
        response.setEmail(email);
        response.setPhoneNumber("081234567890");
        response.setCreatedAt(ZonedDateTime.now());
        response.setUpdatedAt(ZonedDateTime.now());

        return response;
    }
}