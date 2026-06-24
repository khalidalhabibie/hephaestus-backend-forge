package com.fif.exercise2.controller;

import com.fif.exercise2.dto.RepaymentScheduleResponse;
import com.fif.exercise2.exception.GlobalExceptionHandler;
import com.fif.exercise2.exception.RepaymentScheduleNotFoundException;
import com.fif.exercise2.service.RepaymentScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RepaymentScheduleController.class)
@Import(GlobalExceptionHandler.class)
class RepaymentScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepaymentScheduleService repaymentScheduleService;

    // ==========================================================================
    // GET /api/v1/loan-applications/{loanApplicationId}/repayment-schedules
    // ==========================================================================

    @Test
    void should_get_schedules_by_loan_id_without_status_filter() throws Exception {
        // given — tanpa query param status
        List<RepaymentScheduleResponse> responses = List.of(
                buildResponse(1L, 1, "UNPAID"),
                buildResponse(2L, 2, "UNPAID")
        );
        when(repaymentScheduleService.getByLoanApplicationId(1L, null)).thenReturn(responses);

        // when & then
        mockMvc.perform(get("/api/v1/loan-applications/1/repayment-schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void should_get_schedules_by_loan_id_with_status_filter() throws Exception {
        // given — ?status=PAID → hanya yang sudah lunas
        List<RepaymentScheduleResponse> responses = List.of(
                buildResponse(1L, 1, "PAID")
        );
        when(repaymentScheduleService.getByLoanApplicationId(1L, "PAID")).thenReturn(responses);

        // when & then
        mockMvc.perform(get("/api/v1/loan-applications/1/repayment-schedules")
                        .param("status", "PAID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].status").value("PAID"));
    }

    @Test
    void should_return_empty_list_when_no_schedules_exist() throws Exception {
        // given
        when(repaymentScheduleService.getByLoanApplicationId(99L, null)).thenReturn(List.of());

        // when & then
        mockMvc.perform(get("/api/v1/loan-applications/99/repayment-schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    // ==========================================================================
    // GET /api/v1/repayment-schedules/{id}
    // ==========================================================================

    @Test
    void should_get_schedule_by_id_and_return_200() throws Exception {
        // given
        RepaymentScheduleResponse response = buildResponse(1L, 1, "UNPAID");
        when(repaymentScheduleService.getById(1L)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.installment_number").value(1))
                .andExpect(jsonPath("$.data.status").value("UNPAID"));
    }

    @Test
    void should_return_404_when_schedule_not_found() throws Exception {
        // given
        when(repaymentScheduleService.getById(99L))
                .thenThrow(new RepaymentScheduleNotFoundException(99L));

        // when & then
        mockMvc.perform(get("/api/v1/repayment-schedules/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("REPAYMENT_SCHEDULE_NOT_FOUND"));
    }

    // ==========================================================================
    // Helpers
    // ==========================================================================

    private RepaymentScheduleResponse buildResponse(Long id, int installmentNumber, String status) {
        RepaymentScheduleResponse res = new RepaymentScheduleResponse();
        res.setId(id);
        res.setInstallmentNumber(installmentNumber);
        res.setDueDate(LocalDate.now().plusMonths(installmentNumber));
        res.setPrincipalAmount(new BigDecimal("900000"));
        res.setInterestAmount(new BigDecimal("100000"));
        res.setTotalAmount(new BigDecimal("1000000"));
        res.setStatus(status);
        res.setCreatedAt(ZonedDateTime.now());
        res.setUpdatedAt(ZonedDateTime.now());
        return res;
    }
}
