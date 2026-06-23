package com.example.spring_boot_database.controller;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.CreatePaymentTransactionRequest;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.exception.RepaymentScheduleNotFoundException;
import com.example.spring_boot_database.service.PaymentTransactionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentTransactionController.class)
class PaymentTransactionControllerTest {

    private static final String BASE_URL = "/api/v1/payment-transactions";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentTransactionService paymentService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private PaymentTransactionResponse paymentResponse;

    @BeforeEach
    void setUp() {
        paymentResponse = PaymentTransactionResponse.builder()
                .repaymentScheduleId(1L)
                .paymentReference("PAY-20260619-001")
                .paidAmount(new BigDecimal("950000"))
                .paidAt(LocalDateTime.of(2026, 6, 19, 10, 0, 0))
                .build();
    }

    private String validRequestJson() {
        return """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": 950000,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;
    }

    @Test
    void create_success() throws Exception {
        when(paymentService.create(any(CreatePaymentTransactionRequest.class)))
                .thenReturn(paymentResponse);

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson())
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode response = objectMapper.readTree(
                            result.getResponse().getContentAsString()
                    );

                    assertTrue(response.get("success").asBoolean());
                    assertEquals("Payment transaction created successfully", response.get("message").asText());

                    assertTrue(response.get("error").isNull());
                    assertNotNull(response.get("data"));

                    JsonNode data = response.get("data");

                    assertEquals(1L, data.get("repayment_schedule_id").asLong());
                    assertEquals("PAY-20260619-001", data.get("payment_reference").asText());
                    assertEquals(0, new BigDecimal("950000").compareTo(data.get("paid_amount").decimalValue()));
                    assertEquals("2026-06-19T10:00:00", data.get("paid_at").asText());
                });

        verify(paymentService, times(1)).create(any(CreatePaymentTransactionRequest.class));
    }

    @Test
    void create_success_should_send_correct_request_to_service() throws Exception {
        when(paymentService.create(any(CreatePaymentTransactionRequest.class)))
                .thenReturn(paymentResponse);

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson())
        ).andExpect(status().isOk());

        ArgumentCaptor<CreatePaymentTransactionRequest> captor =
                ArgumentCaptor.forClass(CreatePaymentTransactionRequest.class);

        verify(paymentService, times(1)).create(captor.capture());

        CreatePaymentTransactionRequest capturedRequest = captor.getValue();

        assertNotNull(capturedRequest);
        assertEquals(1L, capturedRequest.getRepaymentSchedule_id());
        assertEquals("PAY-20260619-001", capturedRequest.getPaymentReference());
        assertEquals(0, new BigDecimal("950000").compareTo(capturedRequest.getPaidAmount()));
        assertEquals(
                LocalDateTime.of(2026, 6, 19, 10, 0, 0),
                capturedRequest.getPaidAt()
        );
    }

    @Test
    void create_failed_repayment_schedule_not_found() throws Exception {
        when(paymentService.create(any(CreatePaymentTransactionRequest.class)))
                .thenThrow(new RepaymentScheduleNotFoundException(1L));

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson())
        ).andExpect(status().isNotFound())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("REPAYMENT_NOT_FOUND", response.getError());
                    assertNotNull(response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().isEmpty());
                });

        verify(paymentService, times(1)).create(any(CreatePaymentTransactionRequest.class));
    }

    @Test
    void create_failed_schedule_already_paid_bad_request() throws Exception {
        when(paymentService.create(any(CreatePaymentTransactionRequest.class)))
                .thenThrow(new BadRequestException("Schedule already paid"));

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson())
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("BAD_REQUEST", response.getError());
                    assertEquals("Schedule already paid", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertTrue(response.getDetails().isEmpty());
                });

        verify(paymentService, times(1)).create(any(CreatePaymentTransactionRequest.class));
    }

    @Test
    void create_failed_unexpected_exception() throws Exception {
        when(paymentService.create(any(CreatePaymentTransactionRequest.class)))
                .thenThrow(new RuntimeException("Unexpected database error"));

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson())
        ).andExpect(status().isInternalServerError())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("INTERNAL_SERVER_ERROR", response.getError());
                    assertEquals("Unexpected error occurred", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                    assertTrue(response.getDetails().contains("RuntimeException"));
                });

        verify(paymentService, times(1)).create(any(CreatePaymentTransactionRequest.class));
    }

    @Test
    void create_failed_repayment_schedule_id_null() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": null,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": 950000,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertValidationError(response);
                    assertDetailContains(response, "repaymentSchedule_id");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_payment_reference_null() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": null,
                  "paid_amount": 950000,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertValidationError(response);
                    assertDetailContains(response, "paymentReference");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_payment_reference_blank() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "",
                  "paid_amount": 950000,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertValidationError(response);
                    assertDetailContains(response, "paymentReference");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_payment_reference_only_spaces() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "   ",
                  "paid_amount": 950000,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertValidationError(response);
                    assertDetailContains(response, "paymentReference");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_paid_amount_null() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": null,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertValidationError(response);
                    assertDetailContains(response, "paidAmount");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_paid_amount_zero() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": 0,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertValidationError(response);
                    assertDetailContains(response, "paidAmount");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_paid_amount_negative() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": -1000,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertValidationError(response);
                    assertDetailContains(response, "paidAmount");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_multiple_validation_errors() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": null,
                  "payment_reference": "",
                  "paid_amount": -1000,
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertValidationError(response);
                    assertTrue(response.getDetails().size() >= 3);

                    assertDetailContains(response, "repaymentSchedule_id");
                    assertDetailContains(response, "paymentReference");
                    assertDetailContains(response, "paidAmount");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_success_paid_at_null_because_no_not_null_validation() throws Exception {
        PaymentTransactionResponse responseWithNullPaidAt = PaymentTransactionResponse.builder()
                .repaymentScheduleId(1L)
                .paymentReference("PAY-20260619-001")
                .paidAmount(new BigDecimal("950000"))
                .paidAt(null)
                .build();

        when(paymentService.create(any(CreatePaymentTransactionRequest.class)))
                .thenReturn(responseWithNullPaidAt);

        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": 950000,
                  "paid_at": null
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk())
                .andDo(result -> {
                    JsonNode response = objectMapper.readTree(
                            result.getResponse().getContentAsString()
                    );

                    assertTrue(response.get("success").asBoolean());
                    assertEquals("Payment transaction created successfully", response.get("message").asText());
                    assertTrue(response.get("error").isNull());

                    JsonNode data = response.get("data");

                    assertEquals(1L, data.get("repayment_schedule_id").asLong());
                    assertEquals("PAY-20260619-001", data.get("payment_reference").asText());
                    assertEquals(0, new BigDecimal("950000").compareTo(data.get("paid_amount").decimalValue()));
                    assertTrue(data.get("paid_at").isNull());
                });

        verify(paymentService, times(1)).create(any(CreatePaymentTransactionRequest.class));
    }

    @Test
    void create_failed_empty_body_malformed_json() throws Exception {
        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertMalformedJson(response);
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_malformed_json() throws Exception {
        String malformedJson = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": 950000,
                  "paid_at": "2026-06-19T10:00:00",
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertMalformedJson(response);
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_paid_amount_wrong_type_malformed_json() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": "bukan-number",
                  "paid_at": "2026-06-19T10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertMalformedJson(response);
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_paid_at_wrong_format_malformed_json() throws Exception {
        String json = """
                {
                  "repayment_schedule_id": 1,
                  "payment_reference": "PAY-20260619-001",
                  "paid_amount": 950000,
                  "paid_at": "19-06-2026 10:00:00"
                }
                """;

        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertMalformedJson(response);
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_unsupported_media_type() throws Exception {
        mockMvc.perform(
                post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(validRequestJson())
        ).andExpect(status().isUnsupportedMediaType())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertFalse(response.isSuccess());
                    assertEquals("UNSUPPORTED_MEDIA_TYPE", response.getError());
                    assertEquals("Content type is not supported", response.getMessage());
                    assertNull(response.getData());
                    assertNotNull(response.getDetails());
                    assertFalse(response.getDetails().isEmpty());
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_method_get_not_allowed() throws Exception {
        mockMvc.perform(
                get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isMethodNotAllowed())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertMethodNotAllowed(response, "GET");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_method_put_not_allowed() throws Exception {
        mockMvc.perform(
                put(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson())
        ).andExpect(status().isMethodNotAllowed())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertMethodNotAllowed(response, "PUT");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_method_patch_not_allowed() throws Exception {
        mockMvc.perform(
                patch(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson())
        ).andExpect(status().isMethodNotAllowed())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertMethodNotAllowed(response, "PATCH");
                });

        verify(paymentService, never()).create(any());
    }

    @Test
    void create_failed_method_delete_not_allowed() throws Exception {
        mockMvc.perform(
                delete(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isMethodNotAllowed())
                .andDo(result -> {
                    ApiResponse<Void> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );

                    assertMethodNotAllowed(response, "DELETE");
                });

        verify(paymentService, never()).create(any());
    }

    private void assertValidationError(ApiResponse<Void> response) {
        assertFalse(response.isSuccess());
        assertEquals("VALIDATION_ERROR", response.getError());
        assertEquals("Invalid request", response.getMessage());
        assertNull(response.getData());
        assertNotNull(response.getDetails());
        assertFalse(response.getDetails().isEmpty());
    }

    private void assertMalformedJson(ApiResponse<Void> response) {
        assertFalse(response.isSuccess());
        assertEquals("MALFORMED_JSON", response.getError());
        assertEquals("Request body is invalid or malformed", response.getMessage());
        assertNull(response.getData());
        assertNotNull(response.getDetails());
        assertFalse(response.getDetails().isEmpty());
        assertTrue(response.getDetails().contains("JSON request body is not readable"));
    }

    private void assertMethodNotAllowed(ApiResponse<Void> response, String method) {
        assertFalse(response.isSuccess());
        assertEquals("METHOD_NOT_ALLOWED", response.getError());
        assertEquals("HTTP method is not supported", response.getMessage());
        assertNull(response.getData());
        assertNotNull(response.getDetails());
        assertFalse(response.getDetails().isEmpty());

        assertTrue(
                response.getDetails()
                        .stream()
                        .anyMatch(detail -> detail.contains(method)),
                "Expected details to contain method: " + method + ", actual details: " + response.getDetails()
        );
    }

    private void assertDetailContains(ApiResponse<Void> response, String keyword) {
        assertTrue(
                response.getDetails()
                        .stream()
                        .anyMatch(detail -> detail.contains(keyword)),
                "Expected details to contain keyword: " + keyword + ", actual details: " + response.getDetails()
        );
    }
}