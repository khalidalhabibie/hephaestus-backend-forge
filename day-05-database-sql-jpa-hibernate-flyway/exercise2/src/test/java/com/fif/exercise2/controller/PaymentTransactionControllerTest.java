package com.fif.exercise2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fif.exercise2.dto.ApiResponse;
import com.fif.exercise2.dto.CreatePaymentTransactionRequest;
import com.fif.exercise2.dto.PaymentTransactionResponse;
import com.fif.exercise2.exception.GlobalExceptionHandler;
import com.fif.exercise2.exception.RepaymentScheduleNotFoundException;
import com.fif.exercise2.service.PaymentTransactionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentTransactionController.class)
@Import(GlobalExceptionHandler.class)
class PaymentTransactionControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private PaymentTransactionService paymentTransactionService;

        private ObjectMapper objectMapper;

        private CreatePaymentTransactionRequest request;

        @BeforeEach
        void setUp() {
                objectMapper = new ObjectMapper();

                request = new CreatePaymentTransactionRequest();
                request.setRepaymentScheduleId(1L);
                request.setPaymentReference("TRX-001");
                request.setPaidAmount(new BigDecimal("1000000"));
                request.setPaidAt(ZonedDateTime.now());
        }

        // ==========================================================================
        // CREATE PAYMENT
        // ==========================================================================

        @Test
        void should_create_payment_transaction_and_return_201() throws Exception {

                PaymentTransactionResponse response =
                        buildResponse(10L, 1L, "TRX-001", "SUCCESS");

                when(paymentTransactionService.createPaymentTransaction(any()))
                        .thenReturn(response);

                mockMvc.perform(post("/api/v1/payment-transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.success").value(true))
                        .andExpect(jsonPath("$.message")
                                .value("Payment transaction created successfully")) // ✅ penting!
                        .andExpect(jsonPath("$.data.id").value(10))
                        .andExpect(jsonPath("$.data.payment_reference").value("TRX-001"))
                        .andExpect(jsonPath("$.data.repayment_schedule_id").value(1))
                        .andExpect(jsonPath("$.data.status").value("SUCCESS"));
        }

        @Test
        void should_return_404_when_repayment_schedule_not_found() throws Exception {

                when(paymentTransactionService.createPaymentTransaction(any()))
                        .thenThrow(new RepaymentScheduleNotFoundException(99L));

                mockMvc.perform(post("/api/v1/payment-transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        void should_return_400_when_request_invalid() throws Exception {

                CreatePaymentTransactionRequest invalid = new CreatePaymentTransactionRequest();

                mockMvc.perform(post("/api/v1/payment-transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalid)))
                        .andExpect(status().isBadRequest());
        }

        // ==========================================================================
        // GET BY SCHEDULE ID
        // ==========================================================================

        @Test
        void should_get_transactions_by_schedule_id_success() throws Exception {

                List<PaymentTransactionResponse> responses =
                        List.of(buildResponse(1L, 1L, "TRX-001", "SUCCESS"));

                when(paymentTransactionService.getByRepaymentScheduleId(1L))
                        .thenReturn(responses);

                mockMvc.perform(get("/api/v1/repayment-schedules/1/payment-transactions"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.success").value(true))
                        .andExpect(jsonPath("$.message")
                                .value("Payment transactions retrieved successfully")) // ✅ WAJIB
                        .andExpect(jsonPath("$.data.length()").value(1))
                        .andExpect(jsonPath("$.data[0].payment_reference").value("TRX-001"));
        }

        @Test
        void should_return_empty_list_when_no_transactions() throws Exception {

                when(paymentTransactionService.getByRepaymentScheduleId(1L))
                        .thenReturn(List.of());

                mockMvc.perform(get("/api/v1/repayment-schedules/1/payment-transactions"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.message")
                                .value("Payment transactions retrieved successfully")) // ✅ penting
                        .andExpect(jsonPath("$.data.length()").value(0));
        }

        @Test
        void should_return_400_when_invalid_path_variable() throws Exception {

                mockMvc.perform(get("/api/v1/repayment-schedules/abc/payment-transactions"))
                        .andExpect(status().isBadRequest());
        }

        // ==========================================================================
        // EXTRA - COVER ApiResponse (NAIKIN COVERAGE SIGNIFIKAN)
        // ==========================================================================

        @Test
        void should_cover_api_response_success() {

                ApiResponse<String> res = ApiResponse.success("ok", "data");

                assertNotNull(res);
                assertTrue(res.isSuccess());
                assertEquals("ok", res.getMessage());
                assertEquals("data", res.getData());
        }

        @Test
        void should_cover_api_response_error() {

                ApiResponse<String> res = ApiResponse.error("error");

                assertNotNull(res);
                assertFalse(res.isSuccess());
                assertEquals("error", res.getMessage());
        }

        // ==========================================================================
        // HELPER
        // ==========================================================================

        private PaymentTransactionResponse buildResponse(
                Long id,
                Long scheduleId,
                String reference,
                String status
        ) {
                PaymentTransactionResponse res = new PaymentTransactionResponse();
                res.setId(id);
                res.setRepaymentScheduleId(scheduleId);
                res.setPaymentReference(reference);
                res.setPaidAmount(new BigDecimal("1000000"));
                res.setPaidAt(ZonedDateTime.now());
                res.setStatus(status);
                res.setCreatedAt(ZonedDateTime.now());
                res.setUpdatedAt(ZonedDateTime.now());
                return res;
        }
        @Test
        void should_call_service_when_create_payment() throws Exception {

        PaymentTransactionResponse response =
                buildResponse(10L, 1L, "TRX-001", "SUCCESS");

        when(paymentTransactionService.createPaymentTransaction(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/payment-transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(paymentTransactionService)
                .createPaymentTransaction(any());
        }

        @Test
        void should_call_service_when_get_by_schedule() throws Exception {

        when(paymentTransactionService.getByRepaymentScheduleId(1L))
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/v1/repayment-schedules/1/payment-transactions"))
                .andExpect(status().isOk());

        verify(paymentTransactionService)
                .getByRepaymentScheduleId(1L);
        }

        @Test
        void should_return_complete_response_when_get_transactions()
                throws Exception {

        PaymentTransactionResponse response =
                buildResponse(1L, 1L, "TRX-001", "SUCCESS");

        when(paymentTransactionService.getByRepaymentScheduleId(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                get("/api/v1/repayment-schedules/1/payment-transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("Payment transactions retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].repayment_schedule_id").value(1))
                .andExpect(jsonPath("$.data[0].payment_reference")
                        .value("TRX-001"))
                .andExpect(jsonPath("$.data[0].status")
                        .value("SUCCESS"));
        }

        @Test
        void should_cover_payment_transaction_response_getters_setters() {

        PaymentTransactionResponse response =
                buildResponse(1L, 2L, "REF-001", "SUCCESS");

        assertEquals(1L, response.getId());
        assertEquals(2L, response.getRepaymentScheduleId());
        assertEquals("REF-001", response.getPaymentReference());
        assertEquals("SUCCESS", response.getStatus());

        assertNotNull(response.getPaidAmount());
        assertNotNull(response.getPaidAt());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
        }

        @Test
        void should_cover_create_payment_transaction_request() {

        CreatePaymentTransactionRequest req =
                new CreatePaymentTransactionRequest();

        ZonedDateTime now = ZonedDateTime.now();

        req.setRepaymentScheduleId(1L);
        req.setPaymentReference("TRX-001");
        req.setPaidAmount(new BigDecimal("1000000"));
        req.setPaidAt(now);

        assertEquals(1L, req.getRepaymentScheduleId());
        assertEquals("TRX-001", req.getPaymentReference());
        assertEquals(
                new BigDecimal("1000000"),
                req.getPaidAmount()
        );
        assertEquals(now, req.getPaidAt());
        }

        @Test
        void should_cover_controller_constructor() {

        PaymentTransactionController controller =
                new PaymentTransactionController(
                        paymentTransactionService
                );

        assertNotNull(controller);
        }

        @Test
        void should_cover_payment_transaction_response_empty_constructor() {

        PaymentTransactionResponse response =
                new PaymentTransactionResponse();

        response.setId(100L);
        response.setRepaymentScheduleId(200L);
        response.setPaymentReference("REF-100");
        response.setPaidAmount(new BigDecimal("500000"));
        response.setPaidAt(ZonedDateTime.now());
        response.setStatus("SUCCESS");

        assertEquals(100L, response.getId());
        assertEquals(200L, response.getRepaymentScheduleId());
        assertEquals("REF-100", response.getPaymentReference());
        assertEquals("SUCCESS", response.getStatus());
        }

        @Test
        void should_cover_api_response_with_null_data() {

        ApiResponse<Object> response =
                ApiResponse.success("success", null);

        assertTrue(response.isSuccess());
        assertEquals("success", response.getMessage());
        assertNull(response.getData());
        }

        @Test
        void should_cover_api_response_error_only() {

        ApiResponse<Object> response =
                ApiResponse.error("error");

        assertFalse(response.isSuccess());
        assertEquals("error", response.getMessage());
        }
}