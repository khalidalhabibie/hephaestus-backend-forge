package com.example.spring_boot_database.controller;

import com.example.spring_boot_database.dto.*;
import com.example.spring_boot_database.exception.CustomerNotFoundException;
import com.example.spring_boot_database.exception.DuplicateCustomerException;
import com.example.spring_boot_database.service.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateCustomerRequest request;
    private CustomerResponse response;

    @BeforeEach
    void setUp() {
        request = CreateCustomerRequest.builder()
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .phoneNumber("08123456789")
                .build();

        response = CustomerResponse.builder()
                .id(1L)
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .phoneNumber("08123456789")
                .build();
    }

@Test
void create_success() throws Exception {

    when(customerService.createCustomer(any())).thenReturn(response);

    mockMvc.perform(
            post("/api/v1/customers")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
    )
    .andExpect(status().isOk()).andDo(result -> {
               ApiResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                     new TypeReference<>() {
                     });
               assertNull(response.getError());
               assertNotNull(response.getData().getFullName());
               assertNotNull(response.getData().getEmail());

            });

}




    // @Test
    // void create_success() throws Exception {

    //   mockMvc.perform(
    //         post("/api/v1/customers")
    //               .accept(MediaType.APPLICATION_JSON)
    //               .contentType(MediaType.APPLICATION_JSON)
    //               .content(objectMapper.writeValueAsString(request))

    //   ).andExpectAll(
    //         status().isOk()).andDo(result -> {
    //            ApiResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
    //                  new TypeReference<>() {
    //                  });
    //            assertNull(response.getError());
    //            assertNotNull(response.getData().getFullName());
    //            assertNotNull(response.getData().getEmail());

    //         });

    // }

}