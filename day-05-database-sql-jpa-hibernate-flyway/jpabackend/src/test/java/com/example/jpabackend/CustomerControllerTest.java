package com.example.jpabackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.example.jpabackend.controller.CustomerController;
import com.example.jpabackend.dto.CreateCustomerRequest;
import com.example.jpabackend.dto.CustomerResponse;
import com.example.jpabackend.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_create_customer_successfully() throws Exception {
        // given
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFullName("Budi");
        request.setNik("123456789101121133");
        request.setEmail("budi@mail.com");
        request.setPhoneNumber("08123090909090");

        CustomerResponse response = new CustomerResponse(
                1L, "Budi", "123456789101121133", "budi@mail.com", "08123090909090", null, null);

        when(service.createCustomer(any())).thenReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/customers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Customer created successfully"))
                .andExpect(jsonPath("$.data.full_name").value("Budi"));
    }

    @Test
    void should_get_customer_by_id() throws Exception {
        // given
        CustomerResponse response = new CustomerResponse(
                1L, "Budi", "123", "budi@mail.com", "08123", null, null);

        when(service.getById(1L)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.full_name").value("Budi"));
    }

    @Test
    void should_get_all_customers() throws Exception {
        // given
        CustomerResponse c = new CustomerResponse(
                1L, "Budi", "123", "budi@mail.com", "08123", null, null);

        when(service.getAll()).thenReturn(List.of(c));

        // when & then
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }

    @Test
    void should_search_customers_by_name() throws Exception {
        // given
        CustomerResponse c = new CustomerResponse(
                1L, "Budi", "123", "budi@mail.com", "08123", null, null);

        when(service.searchByName("bud")).thenReturn(List.of(c));

        // when & then
        mockMvc.perform(get("/api/v1/customers/search")
                .param("name", "bud"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].full_name").value("Budi"));
    }

    @Test
    void should_delete_customer_successfully() throws Exception {
        // given
        doNothing().when(service).deleteCustomer(1L);

        // when & then
        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Customer deleted successfully"));
    }

    @Test
    void should_return_bad_request_when_request_invalid() throws Exception {
        // given
        CreateCustomerRequest request = new CreateCustomerRequest();

        // when & then
        mockMvc.perform(post("/api/v1/customers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
