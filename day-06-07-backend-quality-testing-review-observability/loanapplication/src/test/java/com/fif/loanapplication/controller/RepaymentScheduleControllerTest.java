package com.fif.loanapplication.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fif.loanapplication.dto.payment.PaymentTransactionResponse;
import com.fif.loanapplication.dto.payment.RepaymentScheduleResponse;
import com.fif.loanapplication.entity.enums.PaymentStatus;
import com.fif.loanapplication.entity.enums.RepaymentStatus;
import com.fif.loanapplication.exception.GlobalExceptionHandler;
import com.fif.loanapplication.exception.RepaymentScheduleNotFoundException;
import com.fif.loanapplication.service.PaymentTransactionService;
import com.fif.loanapplication.service.RepaymentScheduleService;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleControllerTest {

    private static final String BASE_URL = "/api/v1/repayment-schedules";

    @Mock
    private RepaymentScheduleService repaymentScheduleService;

    @Mock
    private PaymentTransactionService paymentTransactionService;

    @InjectMocks
    private RepaymentScheduleController repaymentScheduleController;

    private MockMvc mockMvc;

    @BeforeEach
    @SuppressWarnings("removal")
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders.standaloneSetup(repaymentScheduleController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void getRepaymentScheduleByUid_shouldReturnRepaymentSchedule() throws Exception {
        UUID uid = UUID.randomUUID();
        RepaymentScheduleResponse response = repaymentScheduleResponse(uid);

        when(repaymentScheduleService.getRepaymentScheduleByUid(uid))
                .thenReturn(response);

        mockMvc.perform(get(BASE_URL + "/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Repayment Schedule Retrieved!"))
                .andExpect(jsonPath("$.data.uid").value(uid.toString()))
                .andExpect(jsonPath("$.data.installment_number").value(1))
                .andExpect(jsonPath("$.data.due_date").value("2026-07-24"))
                .andExpect(jsonPath("$.data.principal_amount").value(1_000_000))
                .andExpect(jsonPath("$.data.interest_amount").value(120_000))
                .andExpect(jsonPath("$.data.total_amount").value(1_120_000))
                .andExpect(jsonPath("$.data.status").value("UNPAID"));

        verify(repaymentScheduleService).getRepaymentScheduleByUid(uid);
    }

    @Test
    void getRepaymentScheduleByUid_shouldReturnNotFoundWhenRepaymentScheduleMissing() throws Exception {
        UUID uid = UUID.randomUUID();

        when(repaymentScheduleService.getRepaymentScheduleByUid(uid))
                .thenThrow(new RepaymentScheduleNotFoundException(uid));

        mockMvc.perform(get(BASE_URL + "/{uid}", uid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("REPAYMENT_SCHEDULE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Repayment data not found with unique identifier : " + uid))
                .andExpect(jsonPath("$.errors", hasSize(0)));

        verify(repaymentScheduleService).getRepaymentScheduleByUid(uid);
    }

    @Test
    void getPaymentTransactionByRepaymentUid_shouldReturnPaymentTransactions() throws Exception {
        UUID repaymentUid = UUID.randomUUID();
        PaymentTransactionResponse firstPayment = paymentTransactionResponse(
                UUID.randomUUID(),
                repaymentUid,
                "PAY-20260624-001",
                BigDecimal.valueOf(1_120_000));
        PaymentTransactionResponse secondPayment = paymentTransactionResponse(
                UUID.randomUUID(),
                repaymentUid,
                "PAY-20260624-002",
                BigDecimal.valueOf(500_000));

        when(paymentTransactionService.getPaymentByRepaymentUid(repaymentUid))
                .thenReturn(List.of(firstPayment, secondPayment));

        mockMvc.perform(get(BASE_URL + "/{repaymentUid}/payment-transactions", repaymentUid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Payments Retrieved"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].uid").value(firstPayment.getUid().toString()))
                .andExpect(jsonPath("$.data[0].repayment_schedule_uid").value(repaymentUid.toString()))
                .andExpect(jsonPath("$.data[0].payment_reference").value("PAY-20260624-001"))
                .andExpect(jsonPath("$.data[0].paid_amount").value(1_120_000))
                .andExpect(jsonPath("$.data[0].status").value("SUCCESS"))
                .andExpect(jsonPath("$.data[1].uid").value(secondPayment.getUid().toString()))
                .andExpect(jsonPath("$.data[1].payment_reference").value("PAY-20260624-002"))
                .andExpect(jsonPath("$.data[1].paid_amount").value(500_000));

        verify(paymentTransactionService).getPaymentByRepaymentUid(repaymentUid);
    }

    private RepaymentScheduleResponse repaymentScheduleResponse(UUID uid) {
        return RepaymentScheduleResponse.builder()
                .uid(uid)
                .installmentNumber(1)
                .dueDate(LocalDate.of(2026, 7, 24))
                .principalAmount(BigDecimal.valueOf(1_000_000))
                .interestAmount(BigDecimal.valueOf(120_000))
                .totalAmount(BigDecimal.valueOf(1_120_000))
                .status(RepaymentStatus.UNPAID)
                .build();
    }

    private PaymentTransactionResponse paymentTransactionResponse(
            UUID uid,
            UUID repaymentScheduleUid,
            String paymentReference,
            BigDecimal paidAmount) {
        return PaymentTransactionResponse.builder()
                .uid(uid)
                .repaymentScheduleUid(repaymentScheduleUid)
                .paymentReference(paymentReference)
                .paidAmount(paidAmount)
                .paidAt(ZonedDateTime.parse("2026-06-24T10:15:30+07:00"))
                .status(PaymentStatus.SUCCESS)
                .build();
    }
}
