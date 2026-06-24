package com.example.dbbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.example.dbbackend.customer.controller.CustomerController;
import com.example.dbbackend.customer.dto.CreateCustomerRequest;
import com.example.dbbackend.customer.dto.CustomerResponse;
import com.example.dbbackend.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CustomerService customerService;

    // SKENARIO 1: POST /api/v1/customers (SUKSES)

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFullName("Budi Utomo");
        request.setNik("3171012345670001");
        request.setEmail("budi@example.com");
        request.setPhoneNumber("081234567890");

        CustomerResponse responsePalsu = new CustomerResponse();
        responsePalsu.setId(1L);
        responsePalsu.setFullName("Budi Utomo");
        responsePalsu.setNik("3171012345670001");
        responsePalsu.setEmail("budi@example.com");
        responsePalsu.setPhoneNumber("081234567890");

        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(responsePalsu);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Customer created successfully"))
                .andExpect(jsonPath("$.data.id").value(1L));

        verify(customerService).createCustomer(any(CreateCustomerRequest.class));
    }

    // SKENARIO 2: GET /api/v1/customers/{id} (SUKSES)

    @Test
    void shouldGetCustomerByIdSuccessfully() throws Exception {
        Long customerId = 1L;
        CustomerResponse responsePalsu = new CustomerResponse();
        responsePalsu.setId(customerId);

        when(customerService.getCustomerById(customerId))
                .thenReturn(responsePalsu);

        mockMvc.perform(get("/api/v1/customers/{id}", customerId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Customer retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(customerId));

        verify(customerService).getCustomerById(customerId);
    }

    // SKENARIO 3: NEGATIVE TEST (VALIDASI INPUT @Valid)

    @Test
    void shouldFailCreateCustomerWhenInputInvalid() throws Exception {

        CreateCustomerRequest requestKosong = new CreateCustomerRequest();

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestKosong)))
                .andExpect(status().isBadRequest());
    }
}
