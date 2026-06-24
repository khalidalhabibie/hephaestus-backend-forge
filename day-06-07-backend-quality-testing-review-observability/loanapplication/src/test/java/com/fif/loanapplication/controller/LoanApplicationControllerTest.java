package com.fif.loanapplication.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fif.loanapplication.dto.customer.CustomerSummaryResponse;
import com.fif.loanapplication.dto.loanapplication.CreateLoanApplicationRequest;
import com.fif.loanapplication.dto.loanapplication.LoanApplicationResponse;
import com.fif.loanapplication.dto.loanapplication.UpdateLoanStatusRequest;
import com.fif.loanapplication.dto.payment.RepaymentScheduleResponse;
import com.fif.loanapplication.entity.enums.LoanStatus;
import com.fif.loanapplication.entity.enums.RepaymentStatus;
import com.fif.loanapplication.exception.BadRequestException;
import com.fif.loanapplication.exception.GlobalExceptionHandler;
import com.fif.loanapplication.exception.LoanApplicationNotFoundException;
import com.fif.loanapplication.service.LoanApplicationService;
import com.fif.loanapplication.service.RepaymentScheduleService;
import com.fif.loanapplication.utils.LoanApplicationUtils;

@ExtendWith(MockitoExtension.class)
class LoanApplicationControllerTest {

    private static final String BASE_URL = "/api/v1/loan-applications";

    @Mock
    private LoanApplicationService loanApplicationService;

    @Mock
    private LoanApplicationUtils loanApplicationUtils;

    @Mock
    private RepaymentScheduleService repaymentScheduleService;

    @InjectMocks
    private LoanApplicationController loanApplicationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(loanApplicationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createLoanApplication_shouldReturnCreatedResponse() throws Exception {
        CreateLoanApplicationRequest request = validCreateLoanApplicationRequest();
        LoanApplicationResponse response = loanApplicationResponse(UUID.randomUUID(), LoanStatus.SUBMITTED);

        when(loanApplicationService.createLoanApplication(any(CreateLoanApplicationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan Application Created!"))
                .andExpect(jsonPath("$.data.uid").value(response.getUid().toString()))
                .andExpect(jsonPath("$.data.loan_amount").value(10_000_000))
                .andExpect(jsonPath("$.data.tenor_month").value(12))
                .andExpect(jsonPath("$.data.purpose").value("modal usaha"))
                .andExpect(jsonPath("$.data.status").value("SUBMITTED"))
                .andExpect(jsonPath("$.data.customer.uid").value(response.getCustomer().getUid().toString()))
                .andExpect(jsonPath("$.data.customer.nik").value(response.getCustomer().getNik()))
                .andExpect(jsonPath("$.data.customer.full_name").value(response.getCustomer().getFullName()))
                .andExpect(jsonPath("$.data.customer.email").value(response.getCustomer().getEmail()));

        verify(loanApplicationService).createLoanApplication(any(CreateLoanApplicationRequest.class));
    }

    @Test
    void createLoanApplication_shouldReturnBadRequestWhenRequestInvalid() throws Exception {
        String invalidRequest = """
                {
                    "customer_uid": null,
                    "loan_amount": 0,
                    "tenor_month": 0,
                    "purpose": ""
                }
                """;

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("INVALID REQUEST!"))
                .andExpect(jsonPath("$.errors", hasSize(4)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder(
                        "customerUid",
                        "loanAmount",
                        "tenorMonth",
                        "purpose")));
    }

    @Test
    void createLoanApplication_shouldReturnNotFoundWhenServiceThrowsLoanNotFound() throws Exception {
        UUID uid = UUID.randomUUID();
        CreateLoanApplicationRequest request = validCreateLoanApplicationRequest();

        when(loanApplicationService.createLoanApplication(any(CreateLoanApplicationRequest.class)))
                .thenThrow(new LoanApplicationNotFoundException(uid));

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("LOAN_APPLICATION_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Loan data not found with unique identifier : " + uid))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void getLoanApplicationByUid_shouldReturnLoanApplication() throws Exception {
        UUID uid = UUID.randomUUID();
        LoanApplicationResponse response = loanApplicationResponse(uid, LoanStatus.APPROVED);

        when(loanApplicationService.getLoanByUid(uid)).thenReturn(response);

        mockMvc.perform(get(BASE_URL + "/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan Application Retrieved!"))
                .andExpect(jsonPath("$.data.uid").value(uid.toString()))
                .andExpect(jsonPath("$.data.status").value("APPROVED"));

        verify(loanApplicationService).getLoanByUid(uid);
    }

    @Test
    void getLoanApplicationByUid_shouldReturnNotFoundWhenLoanMissing() throws Exception {
        UUID uid = UUID.randomUUID();

        when(loanApplicationService.getLoanByUid(uid))
                .thenThrow(new LoanApplicationNotFoundException(uid));

        mockMvc.perform(get(BASE_URL + "/{uid}", uid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("LOAN_APPLICATION_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Loan data not found with unique identifier : " + uid))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void getLoanApplications_shouldReturnAllLoansWhenStatusMissing() throws Exception {
        LoanApplicationResponse first = loanApplicationResponse(UUID.randomUUID(), LoanStatus.SUBMITTED);
        LoanApplicationResponse second = loanApplicationResponse(UUID.randomUUID(), LoanStatus.APPROVED);

        when(loanApplicationUtils.parseLoanStatus(null)).thenReturn(null);
        when(loanApplicationService.getLoans(null)).thenReturn(List.of(first, second));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan Application Retrieved!"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].uid").value(first.getUid().toString()))
                .andExpect(jsonPath("$.data[1].uid").value(second.getUid().toString()));

        verify(loanApplicationUtils).parseLoanStatus(null);
        verify(loanApplicationService).getLoans(null);
    }

    @Test
    void getLoanApplications_shouldReturnFilteredLoansWhenStatusProvided() throws Exception {
        LoanApplicationResponse response = loanApplicationResponse(UUID.randomUUID(), LoanStatus.APPROVED);

        when(loanApplicationUtils.parseLoanStatus("approved")).thenReturn(LoanStatus.APPROVED);
        when(loanApplicationService.getLoans(LoanStatus.APPROVED)).thenReturn(List.of(response));

        mockMvc.perform(get(BASE_URL).param("status", "approved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan Application Retrieved!"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].status").value("APPROVED"));

        verify(loanApplicationUtils).parseLoanStatus("approved");
        verify(loanApplicationService).getLoans(LoanStatus.APPROVED);
    }

    @Test
    void getLoanApplications_shouldReturnBadRequestWhenStatusInvalid() throws Exception {
        when(loanApplicationUtils.parseLoanStatus("unknown"))
                .thenThrow(new BadRequestException("Status loan tidak valid: unknown"));

        mockMvc.perform(get(BASE_URL).param("status", "unknown"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Status loan tidak valid: unknown"))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void getRepaymentSchedulesByLoanApplicationUid_shouldReturnSchedules() throws Exception {
        UUID loanApplicationUid = UUID.randomUUID();
        RepaymentScheduleResponse first = repaymentScheduleResponse(1, RepaymentStatus.UNPAID);
        RepaymentScheduleResponse second = repaymentScheduleResponse(2, RepaymentStatus.PAID);

        when(repaymentScheduleService.getRepaymentScheduleByLoanApplicationUid(loanApplicationUid))
                .thenReturn(List.of(first, second));

        mockMvc.perform(get(BASE_URL + "/{loan_application_uid}/repayment-schedules", loanApplicationUid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Repayment schedules retrieved!"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].uid").value(first.getUid().toString()))
                .andExpect(jsonPath("$.data[0].installment_number").value(1))
                .andExpect(jsonPath("$.data[0].due_date").value(first.getDueDate().toString()))
                .andExpect(jsonPath("$.data[0].principal_amount").value(1_000_000))
                .andExpect(jsonPath("$.data[0].interest_amount").value(120_000))
                .andExpect(jsonPath("$.data[0].total_amount").value(1_120_000))
                .andExpect(jsonPath("$.data[0].status").value("UNPAID"))
                .andExpect(jsonPath("$.data[1].installment_number").value(2))
                .andExpect(jsonPath("$.data[1].status").value("PAID"));

        verify(repaymentScheduleService).getRepaymentScheduleByLoanApplicationUid(loanApplicationUid);
    }

    @Test
    void approveLoanApplication_shouldReturnApprovedLoanApplication() throws Exception {
        UUID uid = UUID.randomUUID();
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatus.APPROVED)
                .build();
        LoanApplicationResponse response = loanApplicationResponse(uid, LoanStatus.APPROVED);

        when(loanApplicationService.approveLoanApplicationByUid(eq(uid), any(UpdateLoanStatusRequest.class)))
                .thenReturn(response);

        mockMvc.perform(patch(BASE_URL + "/{uid}", uid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan Application Approved!"))
                .andExpect(jsonPath("$.data.uid").value(uid.toString()))
                .andExpect(jsonPath("$.data.status").value("APPROVED"));

        verify(loanApplicationService).approveLoanApplicationByUid(eq(uid), any(UpdateLoanStatusRequest.class));
    }

    @Test
    void approveLoanApplication_shouldReturnNotFoundWhenLoanMissing() throws Exception {
        UUID uid = UUID.randomUUID();
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatus.APPROVED)
                .build();

        when(loanApplicationService.approveLoanApplicationByUid(eq(uid), any(UpdateLoanStatusRequest.class)))
                .thenThrow(new LoanApplicationNotFoundException(uid));

        mockMvc.perform(patch(BASE_URL + "/{uid}", uid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("LOAN_APPLICATION_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Loan data not found with unique identifier : " + uid))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void approveLoanApplication_shouldReturnBadRequestWhenTransitionInvalid() throws Exception {
        UUID uid = UUID.randomUUID();
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatus.CLOSED)
                .build();

        when(loanApplicationService.approveLoanApplicationByUid(eq(uid), any(UpdateLoanStatusRequest.class)))
                .thenThrow(new BadRequestException("Tidak boleh mengubah status dari SUBMITTED ke CLOSED"));

        mockMvc.perform(patch(BASE_URL + "/{uid}", uid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Tidak boleh mengubah status dari SUBMITTED ke CLOSED"))
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    private CreateLoanApplicationRequest validCreateLoanApplicationRequest() {
        return CreateLoanApplicationRequest.builder()
                .customerUid(UUID.randomUUID())
                .loanAmount(BigDecimal.valueOf(10_000_000))
                .tenorMonth(12)
                .purpose("modal usaha")
                .build();
    }

    private LoanApplicationResponse loanApplicationResponse(UUID uid, LoanStatus status) {
        return LoanApplicationResponse.builder()
                .uid(uid)
                .loanAmount(BigDecimal.valueOf(10_000_000))
                .tenorMonth(12)
                .purpose("modal usaha")
                .status(status)
                .customer(CustomerSummaryResponse.builder()
                        .uid(UUID.randomUUID())
                        .nik("3201010101010001")
                        .fullName("andi saputra")
                        .email("andi@example.com")
                        .build())
                .build();
    }

    private RepaymentScheduleResponse repaymentScheduleResponse(int installmentNumber, RepaymentStatus status) {
        return RepaymentScheduleResponse.builder()
                .uid(UUID.randomUUID())
                .installmentNumber(installmentNumber)
                .dueDate(LocalDate.of(2026, 7, installmentNumber))
                .principalAmount(BigDecimal.valueOf(1_000_000))
                .interestAmount(BigDecimal.valueOf(120_000))
                .totalAmount(BigDecimal.valueOf(1_120_000))
                .status(status)
                .build();
    }
}
