package com.fif.loanapplication.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fif.loanapplication.dto.payment.CreatePaymentTransactionRequest;
import com.fif.loanapplication.dto.payment.PaymentTransactionResponse;
import com.fif.loanapplication.entity.enums.PaymentStatus;
import com.fif.loanapplication.exception.GlobalExceptionHandler;
import com.fif.loanapplication.exception.RepaymentScheduleNotFoundException;
import com.fif.loanapplication.service.PaymentTransactionService;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionControllerTest {

        private static final String BASE_URL = "/api/v1/payment-transactions";

        @Mock
        private PaymentTransactionService paymentTransactionService;

        @InjectMocks
        private PaymentTransactionController paymentTransactionController;

        private MockMvc mockMvc;
        private ObjectMapper objectMapper;

        @BeforeEach
        @SuppressWarnings("removal")
        void setUp() {
                LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
                validator.afterPropertiesSet();

                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                mockMvc = MockMvcBuilders.standaloneSetup(paymentTransactionController)
                                .setControllerAdvice(new GlobalExceptionHandler())
                                .setValidator(validator)
                                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                                .build();
        }

        @Test
        void createPaymentTransaction_shouldReturnCreatedResponse() throws Exception {
                CreatePaymentTransactionRequest request = validCreatePaymentTransactionRequest();
                PaymentTransactionResponse response = paymentTransactionResponse(
                                UUID.randomUUID(),
                                request.getRepaymentScheduleUid(),
                                request.getPaidAt());

                when(paymentTransactionService.createPayments(any(CreatePaymentTransactionRequest.class)))
                                .thenReturn(response);

                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Payments Success!"))
                                .andExpect(jsonPath("$.data.uid").value(response.getUid().toString()))
                                .andExpect(jsonPath("$.data.repayment_schedule_uid")
                                                .value(response.getRepaymentScheduleUid().toString()))
                                .andExpect(jsonPath("$.data.payment_reference").value("PAY-20260624-001"))
                                .andExpect(jsonPath("$.data.paid_amount").value(1_120_000))
                                .andExpect(jsonPath("$.data.status").value("SUCCESS"));

                verify(paymentTransactionService).createPayments(any(CreatePaymentTransactionRequest.class));
        }

        @Test
        void createPaymentTransaction_shouldReturnBadRequestWhenRequestInvalid() throws Exception {
                String invalidRequest = """
                                {
                                    "repayment_schedule_uid": null,
                                    "payment_reference": "",
                                    "paid_amount": 0,
                                    "paid_at": null
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
                                                "repaymentScheduleUid",
                                                "paymentReference",
                                                "paidAmount",
                                                "paidAt")));

                verifyNoInteractions(paymentTransactionService);
        }

        @Test
        void createPaymentTransaction_shouldReturnNotFoundWhenRepaymentScheduleMissing() throws Exception {
                UUID repaymentScheduleUid = UUID.randomUUID();
                CreatePaymentTransactionRequest request = CreatePaymentTransactionRequest.builder()
                                .repaymentScheduleUid(repaymentScheduleUid)
                                .paymentReference("PAY-20260624-001")
                                .paidAmount(BigDecimal.valueOf(1_120_000))
                                .paidAt(ZonedDateTime.parse("2026-06-24T10:15:30+07:00"))
                                .build();

                when(paymentTransactionService.createPayments(any(CreatePaymentTransactionRequest.class)))
                                .thenThrow(new RepaymentScheduleNotFoundException(repaymentScheduleUid));

                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.code").value("REPAYMENT_SCHEDULE_NOT_FOUND"))
                                .andExpect(jsonPath("$.message").value(
                                                "Repayment data not found with unique identifier : "
                                                                + repaymentScheduleUid))
                                .andExpect(jsonPath("$.errors", hasSize(0)));
        }

        private CreatePaymentTransactionRequest validCreatePaymentTransactionRequest() {
                return CreatePaymentTransactionRequest.builder()
                                .repaymentScheduleUid(UUID.randomUUID())
                                .paymentReference("PAY-20260624-001")
                                .paidAmount(BigDecimal.valueOf(1_120_000))
                                .paidAt(ZonedDateTime.parse("2026-06-24T10:15:30+07:00"))
                                .build();
        }

        private PaymentTransactionResponse paymentTransactionResponse(
                        UUID uid,
                        UUID repaymentScheduleUid,
                        ZonedDateTime paidAt) {
                return PaymentTransactionResponse.builder()
                                .uid(uid)
                                .repaymentScheduleUid(repaymentScheduleUid)
                                .paymentReference("PAY-20260624-001")
                                .paidAmount(BigDecimal.valueOf(1_120_000))
                                .paidAt(paidAt)
                                .status(PaymentStatus.SUCCESS)
                                .build();
        }
}
