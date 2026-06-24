package com.fif.loanapplication.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fif.loanapplication.dto.customer.CreateCustomerRequest;
import com.fif.loanapplication.dto.customer.CustomerResponse;
import com.fif.loanapplication.dto.customer.CustomerSummaryResponse;
import com.fif.loanapplication.dto.loanapplication.LoanApplicationResponse;
import com.fif.loanapplication.entity.enums.LoanStatus;
import com.fif.loanapplication.exception.CustomerNotFoundException;
import com.fif.loanapplication.exception.DuplicateCustomerException;
import com.fif.loanapplication.exception.GlobalExceptionHandler;
import com.fif.loanapplication.service.CustomerService;
import com.fif.loanapplication.service.LoanApplicationService;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private static final String BASE_URL = "/api/v1/customers";

    @Mock
    private CustomerService customerService;

    @Mock
    private LoanApplicationService loanApplicationService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createCustomer_shouldReturnCreatedResponse() throws Exception {
        CreateCustomerRequest request = validCreateCustomerRequest();
        CustomerResponse response = customerResponse(UUID.randomUUID());

        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customer Created!"))
                .andExpect(jsonPath("$.data.uid").value(response.getUid().toString()))
                .andExpect(jsonPath("$.data.nik").value(response.getNik()))
                .andExpect(jsonPath("$.data.full_name").value(response.getFullName()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()))
                .andExpect(jsonPath("$.data.phone_number").value(response.getPhoneNumber()));

        verify(customerService).createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void createCustomer_shouldReturnBadRequestWhenRequestInvalid() throws Exception {
        String invalidRequest = """
                {
                    "nik": "123",
                    "full_name": "",
                    "email": "not-an-email",
                    "phone_number": ""
                }
                """;

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("INVALID REQUEST!"))
                .andExpect(jsonPath("$.errors", hasSize(5)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder(
                        "nik",
                        "fullName",
                        "fullName",
                        "email",
                        "phoneNumber")));
    }

    @Test
    void createCustomer_shouldReturnConflictWhenDuplicateCustomer() throws Exception {
        CreateCustomerRequest request = validCreateCustomerRequest();

        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenThrow(new DuplicateCustomerException("Email sudah terdaftar!"));

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATED_CUSTOMER_DATA"))
                .andExpect(jsonPath("$.message").value("Email sudah terdaftar!"))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void getCustomerById_shouldReturnCustomer() throws Exception {
        UUID uid = UUID.randomUUID();
        CustomerResponse response = customerResponse(uid);

        when(customerService.getCustomerById(uid)).thenReturn(response);

        mockMvc.perform(get(BASE_URL + "/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customer retrieved"))
                .andExpect(jsonPath("$.data.uid").value(uid.toString()))
                .andExpect(jsonPath("$.data.full_name").value(response.getFullName()));

        verify(customerService).getCustomerById(uid);
    }

    @Test
    void getCustomerById_shouldReturnNotFoundWhenCustomerMissing() throws Exception {
        UUID uid = UUID.randomUUID();

        when(customerService.getCustomerById(uid))
                .thenThrow(new CustomerNotFoundException(uid));

        mockMvc.perform(get(BASE_URL + "/{uid}", uid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CUSTOMER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Customer not found with unique identifier : " + uid))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void getAllCustomer_shouldReturnCustomersUsingOptionalFilters() throws Exception {
        CustomerResponse first = customerResponse(UUID.randomUUID());
        CustomerResponse second = CustomerResponse.builder()
                .uid(UUID.randomUUID())
                .nik("3201010101010002")
                .fullName("sari lestari")
                .email("sari@example.com")
                .phoneNumber("081234567891")
                .build();

        when(customerService.getAllCustomers("andi", "3201", "example.com"))
                .thenReturn(List.of(first, second));

        mockMvc.perform(get(BASE_URL)
                .param("fullName", "andi")
                .param("nik", "3201")
                .param("email", "example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("List of customers"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].uid").value(first.getUid().toString()))
                .andExpect(jsonPath("$.data[1].full_name").value(second.getFullName()));

        verify(customerService).getAllCustomers("andi", "3201", "example.com");
    }

    @Test
    void getLoanApplicationByCustomerUid_shouldReturnLoanApplications() throws Exception {
        UUID customerUid = UUID.randomUUID();
        UUID loanUid = UUID.randomUUID();

        LoanApplicationResponse response = LoanApplicationResponse.builder()
                .uid(loanUid)
                .loanAmount(BigDecimal.valueOf(10_000_000))
                .tenorMonth(12)
                .purpose("modal usaha")
                .status(LoanStatus.APPROVED)
                .customer(CustomerSummaryResponse.builder()
                        .uid(customerUid)
                        .fullName("andi saputra")
                        .build())
                .build();

        when(loanApplicationService.getLoanApplicationByCustomerUid(customerUid))
                .thenReturn(List.of(response));

        mockMvc.perform(get(BASE_URL + "/{customerUid}/loan-applications", customerUid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("List loan application by customer retrived succesfully!"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].uid").value(loanUid.toString()))
                .andExpect(jsonPath("$.data[0].loan_amount").value(10_000_000))
                .andExpect(jsonPath("$.data[0].tenor_month").value(12))
                .andExpect(jsonPath("$.data[0].status").value("APPROVED"))
                .andExpect(jsonPath("$.data[0].customer.uid").value(customerUid.toString()));

        verify(loanApplicationService).getLoanApplicationByCustomerUid(customerUid);
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer() throws Exception {
        UUID uid = UUID.randomUUID();
        CreateCustomerRequest request = validCreateCustomerRequest();
        CustomerResponse response = customerResponse(uid);

        when(customerService.editCustomer(eq(uid), any(CreateCustomerRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put(BASE_URL + "/{uid}", uid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Customer data updated successfully!"))
                .andExpect(jsonPath("$.data.uid").value(uid.toString()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()));

        verify(customerService).editCustomer(eq(uid), any(CreateCustomerRequest.class));
    }

    @Test
    void updateCustomer_shouldReturnConflictWhenDuplicateCustomer() throws Exception {
        UUID uid = UUID.randomUUID();
        CreateCustomerRequest request = validCreateCustomerRequest();

        when(customerService.editCustomer(eq(uid), any(CreateCustomerRequest.class)))
                .thenThrow(new DuplicateCustomerException("NIK sudah digunakan!"));

        mockMvc.perform(put(BASE_URL + "/{uid}", uid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATED_CUSTOMER_DATA"))
                .andExpect(jsonPath("$.message").value("NIK sudah digunakan!"))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void updateCustomer_shouldReturnNotFoundWhenCustomerMissing() throws Exception {
        UUID uid = UUID.randomUUID();
        CreateCustomerRequest request = validCreateCustomerRequest();

        when(customerService.editCustomer(eq(uid), any(CreateCustomerRequest.class)))
                .thenThrow(new CustomerNotFoundException(uid));

        mockMvc.perform(put(BASE_URL + "/{uid}", uid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CUSTOMER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Customer not found with unique identifier : " + uid))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void deleteCustomer_shouldReturnNoContent() throws Exception {
        UUID uid = UUID.randomUUID();

        doNothing().when(customerService).deleteCustomer(uid);

        mockMvc.perform(delete(BASE_URL + "/{uid}", uid))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Customer Deleted!"));

        verify(customerService).deleteCustomer(uid);
    }

    @Test
    void deleteCustomer_shouldReturnNotFoundWhenCustomerMissing() throws Exception {
        UUID uid = UUID.randomUUID();

        doThrow(new CustomerNotFoundException(uid))
                .when(customerService)
                .deleteCustomer(uid);

        mockMvc.perform(delete(BASE_URL + "/{uid}", uid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CUSTOMER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Customer not found with unique identifier : " + uid))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    private CreateCustomerRequest validCreateCustomerRequest() {
        return CreateCustomerRequest.builder()
                .nik("3201010101010001")
                .fullName("Andi Saputra")
                .email("andi@example.com")
                .phoneNumber("081234567890")
                .build();
    }

    private CustomerResponse customerResponse(UUID uid) {
        return CustomerResponse.builder()
                .uid(uid)
                .nik("3201010101010001")
                .fullName("andi saputra")
                .email("andi@example.com")
                .phoneNumber("081234567890")
                .build();
    }
}
