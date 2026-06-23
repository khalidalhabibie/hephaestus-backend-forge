package com.example.spring_boot_database.controller;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.CreateCustomerRequest;
import com.example.spring_boot_database.dto.CustomerResponse;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.dto.OutstandingAmountResponse;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.exception.CustomerNotFoundException;
import com.example.spring_boot_database.exception.DuplicateCustomerException;
import com.example.spring_boot_database.service.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateCustomerRequest request;
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        request = CreateCustomerRequest.builder()
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .phoneNumber("08123456789")
                .build();

        customerResponse = CustomerResponse.builder()
                .id(1L)
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .phoneNumber("08123456789")
                .build();
    }


    @Test
    void create_success() throws Exception {
        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(customerResponse);

        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<CustomerResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Customer created successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());

                    assertEquals(1L, response.getData().getId());
                    assertEquals("Budi", response.getData().getFullName());
                    assertEquals("3173010101900001", response.getData().getNik());
                    assertEquals("budi@mail.com", response.getData().getEmail());
                    assertEquals("08123456789", response.getData().getPhoneNumber());
                });

        verify(customerService, times(1)).createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void create_failed_duplicate_nik() throws Exception {
        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenThrow(new DuplicateCustomerException("nik", request.getNik()));

        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("DUPLICATE_DATA", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                });

        verify(customerService, times(1)).createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void create_failed_duplicate_email() throws Exception {
        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenThrow(new DuplicateCustomerException("email", request.getEmail()));

        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("DUPLICATE_DATA", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                });

        verify(customerService, times(1)).createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void create_failed_full_name_blank() throws Exception {
        request.setFullName("");

        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
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
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(customerService, never()).createCustomer(any());
    }

    @Test
    void create_failed_nik_blank() throws Exception {
        request.setNik("");

        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
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
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(customerService, never()).createCustomer(any());
    }

    @Test
    void create_failed_email_blank() throws Exception {
        request.setEmail("");

        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
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
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(customerService, never()).createCustomer(any());
    }

    @Test
    void create_failed_email_invalid() throws Exception {
        request.setEmail("email-salah");

        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
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
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(customerService, never()).createCustomer(any());
    }

    @Test
    void create_failed_phone_number_blank() throws Exception {
        request.setPhoneNumber("");

        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
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
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(customerService, never()).createCustomer(any());
    }

    @Test
    void create_failed_empty_json_body() throws Exception {
        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
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
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(customerService, never()).createCustomer(any());
    }

    @Test
    void create_failed_malformed_json() throws Exception {
        mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid json }")
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
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(customerService, never()).createCustomer(any());
    }

    @Test
    void create_failed_unsupported_media_type() throws Exception {
        mockMvc.perform(
                post("/api/v1/customers")
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
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(customerService, never()).createCustomer(any());
    }

    @Test
    void get_by_id_success() throws Exception {
        when(customerService.findById(1L))
                .thenReturn(customerResponse);

        mockMvc.perform(
                get("/api/v1/customers/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<CustomerResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Customer retrieved successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());

                    assertEquals(1L, response.getData().getId());
                    assertEquals("Budi", response.getData().getFullName());
                    assertEquals("3173010101900001", response.getData().getNik());
                    assertEquals("budi@mail.com", response.getData().getEmail());
                    assertEquals("08123456789", response.getData().getPhoneNumber());
                });

        verify(customerService, times(1)).findById(1L);
    }

    @Test
    void get_by_id_failed_not_found() throws Exception {
        when(customerService.findById(999L))
                .thenThrow(new CustomerNotFoundException(999L));

        mockMvc.perform(
                get("/api/v1/customers/{id}", 999L)
                        .accept(MediaType.APPLICATION_JSON)
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

        verify(customerService, times(1)).findById(999L);
    }

    @Test
    void get_by_id_failed_id_not_number() throws Exception {
        mockMvc.perform(
                get("/api/v1/customers/{id}", "abc")
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
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(customerService, never()).findById(any());
    }



    @Test
    void get_all_success_with_data() throws Exception {
        CustomerResponse customer2 = CustomerResponse.builder()
                .id(2L)
                .fullName("Andi")
                .nik("3173010101900002")
                .email("andi@mail.com")
                .phoneNumber("08123456780")
                .build();

        when(customerService.findCustomer(null))
                .thenReturn(List.of(customerResponse, customer2));

        mockMvc.perform(
                get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<CustomerResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Customers retrieved successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(2, response.getData().size());

                    assertEquals("Budi", response.getData().get(0).getFullName());
                    assertEquals("Andi", response.getData().get(1).getFullName());
                });

        verify(customerService, times(1)).findCustomer(null);
    }

    @Test
    void get_all_success_empty() throws Exception {
        when(customerService.findCustomer(null))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<CustomerResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Customers retrieved successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(0, response.getData().size());
                });

        verify(customerService, times(1)).findCustomer(null);
    }



    @Test
    void search_success_found() throws Exception {
        when(customerService.findCustomer("Budi"))
                .thenReturn(List.of(customerResponse));

        mockMvc.perform(
                get("/api/v1/customers/search")
                        .param("name", "Budi")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<CustomerResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Customers found", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(1, response.getData().size());

                    assertEquals("Budi", response.getData().get(0).getFullName());
                });

        verify(customerService, times(1)).findCustomer("Budi");
    }

    @Test
    void search_success_empty_result() throws Exception {
        when(customerService.findCustomer("TidakAda"))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/customers/search")
                        .param("name", "TidakAda")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<CustomerResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Customers found", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(0, response.getData().size());
                });

        verify(customerService, times(1)).findCustomer("TidakAda");
    }

    @Test
    void search_success_blank_name() throws Exception {
        when(customerService.findCustomer("   "))
                .thenReturn(List.of(customerResponse));

        mockMvc.perform(
                get("/api/v1/customers/search")
                        .param("name", "   ")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<List<CustomerResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Customers found", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(1, response.getData().size());
                });

        verify(customerService, times(1)).findCustomer("   ");
    }

    @Test
    void search_failed_name_param_missing() throws Exception {
        mockMvc.perform(
                get("/api/v1/customers/search")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("MISSING_PARAMETER", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(customerService, never()).findCustomer(any());
    }



    @Test
    void get_loans_success_with_data() throws Exception {
        LoanApplicationResponse loanResponse = LoanApplicationResponse.builder()
                .loanAmount(new BigDecimal("10000000"))
                .tenorMonth(12)
                .purpose("Modal usaha")
                .status(Status.APPROVED)
                .customer(customerResponse)
                .build();

        when(customerService.findLoanByCustomer(1L))
                .thenReturn(List.of(loanResponse));

        mockMvc.perform(
                get("/api/v1/customers/{id}/loan-applications", 1L)
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

                    LoanApplicationResponse loan = response.getData().get(0);

                    assertEquals(new BigDecimal("10000000"), loan.getLoanAmount());
                    assertEquals(12, loan.getTenorMonth());
                    assertEquals("Modal usaha", loan.getPurpose());
                    assertEquals(Status.APPROVED, loan.getStatus());
                    assertNotNull(loan.getCustomer());
                    assertEquals("Budi", loan.getCustomer().getFullName());
                });

        verify(customerService, times(1)).findLoanByCustomer(1L);
    }

    @Test
    void get_loans_success_empty() throws Exception {
        when(customerService.findLoanByCustomer(1L))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/customers/{id}/loan-applications", 1L)
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
                    assertEquals(0, response.getData().size());
                });

        verify(customerService, times(1)).findLoanByCustomer(1L);
    }

    @Test
    void get_loans_failed_customer_not_found() throws Exception {
        when(customerService.findLoanByCustomer(999L))
                .thenThrow(new CustomerNotFoundException(999L));

        mockMvc.perform(
                get("/api/v1/customers/{id}/loan-applications", 999L)
                        .accept(MediaType.APPLICATION_JSON)
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

        verify(customerService, times(1)).findLoanByCustomer(999L);
    }

    @Test
    void get_loans_failed_id_not_number() throws Exception {
        mockMvc.perform(
                get("/api/v1/customers/{id}/loan-applications", "abc")
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
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(customerService, never()).findLoanByCustomer(any());
    }


@Test
void get_outstanding_amount_success_positive_amount() throws Exception {
    OutstandingAmountResponse outstandingResponse = OutstandingAmountResponse.builder()
            .customerId(1L)
            .outstandingAmount(new BigDecimal("7500000"))
            .build();

    when(customerService.getOutstandingAmountByCustomer(1L))
            .thenReturn(outstandingResponse);

    mockMvc.perform(
            get("/api/v1/customers/{id}/outstanding-amount", 1L)
                    .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk())
            .andDo(result -> {
                JsonNode response = objectMapper.readTree(
                        result.getResponse().getContentAsString()
                );

                assertTrue(response.get("success").asBoolean());
                assertEquals(
                        "Outstanding amount retrieved successfully",
                        response.get("message").asText()
                );

                assertTrue(response.get("error").isNull());
                assertTrue(response.hasNonNull("data"));

                JsonNode data = response.get("data");

                assertEquals(1L, data.get("customerId").asLong());

                BigDecimal actualOutstandingAmount =
                        data.get("outstandingAmount").decimalValue();

                assertEquals(
                        0,
                        new BigDecimal("7500000").compareTo(actualOutstandingAmount)
                );
            });

    verify(customerService, times(1)).getOutstandingAmountByCustomer(1L);
}

    @Test
    void get_outstanding_amount_success_zero_amount() throws Exception {
        OutstandingAmountResponse outstandingResponse = OutstandingAmountResponse.builder()
                .customerId(1L)
                .outstandingAmount(BigDecimal.ZERO)
                .build();

        when(customerService.getOutstandingAmountByCustomer(1L))
                .thenReturn(outstandingResponse);

        mockMvc.perform(
                get("/api/v1/customers/{id}/outstanding-amount", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode response = objectMapper.readTree(
                            result.getResponse().getContentAsString()
                    );

                    assertTrue(response.get("success").asBoolean());
                    assertEquals(
                            "Outstanding amount retrieved successfully",
                            response.get("message").asText()
                    );

                    assertTrue(response.get("error").isNull());
                    assertTrue(response.hasNonNull("data"));

                    JsonNode data = response.get("data");

                    assertEquals(1L, data.get("customerId").asLong());

                    BigDecimal actualOutstandingAmount =
                            data.get("outstandingAmount").decimalValue();

                    assertEquals(
                            0,
                            BigDecimal.ZERO.compareTo(actualOutstandingAmount)
                    );
                });

        verify(customerService, times(1)).getOutstandingAmountByCustomer(1L);
    }

    @Test
    void get_outstanding_amount_failed_customer_not_found() throws Exception {
        when(customerService.getOutstandingAmountByCustomer(999L))
                .thenThrow(new CustomerNotFoundException(999L));

        mockMvc.perform(
                get("/api/v1/customers/{id}/outstanding-amount", 999L)
                        .accept(MediaType.APPLICATION_JSON)
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

        verify(customerService, times(1)).getOutstandingAmountByCustomer(999L);
    }

    @Test
    void get_outstanding_amount_failed_id_not_number() throws Exception {
        mockMvc.perform(
                get("/api/v1/customers/{id}/outstanding-amount", "abc")
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
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(customerService, never()).getOutstandingAmountByCustomer(any());
    }



    @Test
    void delete_success() throws Exception {
        when(customerService.softDeleteCustomer(1L))
                .thenReturn(customerResponse);

        mockMvc.perform(
                delete("/api/v1/customers/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    ApiResponse<CustomerResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertTrue(response.isSuccess());
                    assertEquals("Customer deleted successfully", response.getMessage());
                    assertNull(response.getError());
                    assertNotNull(response.getData());

                    assertEquals(1L, response.getData().getId());
                    assertEquals("Budi", response.getData().getFullName());
                    assertEquals("3173010101900001", response.getData().getNik());
                    assertEquals("budi@mail.com", response.getData().getEmail());
                    assertEquals("08123456789", response.getData().getPhoneNumber());
                });

        verify(customerService, times(1)).softDeleteCustomer(1L);
    }

    @Test
    void delete_failed_customer_not_found() throws Exception {
        when(customerService.softDeleteCustomer(999L))
                .thenThrow(new CustomerNotFoundException(999L));

        mockMvc.perform(
                delete("/api/v1/customers/{id}", 999L)
                        .accept(MediaType.APPLICATION_JSON)
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

        verify(customerService, times(1)).softDeleteCustomer(999L);
    }

    @Test
    void delete_failed_id_not_number() throws Exception {
        mockMvc.perform(
                delete("/api/v1/customers/{id}", "abc")
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
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verify(customerService, never()).softDeleteCustomer(any());
    }



    @Test
    void unsupported_method_post_to_id_should_return_method_not_allowed() throws Exception {
        mockMvc.perform(
                post("/api/v1/customers/{id}", 1L)
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
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verifyNoInteractions(customerService);
    }

    @Test
    void unsupported_method_put_should_return_method_not_allowed() throws Exception {
        mockMvc.perform(
                put("/api/v1/customers/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isMethodNotAllowed())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("METHOD_NOT_ALLOWED", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                });

        verifyNoInteractions(customerService);
    }
}