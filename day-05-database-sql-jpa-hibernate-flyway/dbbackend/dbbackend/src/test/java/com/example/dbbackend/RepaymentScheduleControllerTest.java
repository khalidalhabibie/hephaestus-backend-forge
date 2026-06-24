package com.example.dbbackend;

import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.dbbackend.repaymentschedule.controller.RepaymentScheduleController;
import com.example.dbbackend.repaymentschedule.dto.RepaymentScheduleResponse;
import com.example.dbbackend.repaymentschedule.service.RepaymentScheduleService;

@WebMvcTest(RepaymentScheduleController.class)
class RepaymentScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepaymentScheduleService repaymentScheduleService;

    @Test
    @DisplayName("GET /api/v1/loan-applications/{loanId}/repayment-schedules - Harus Sukses Mengambil List Schedule Berdasarkan Loan ID")
    void getByLoan_ShouldReturnList() throws Exception {
        // 1. GIVEN (Persiapan Data)
        Long loanId = 100L;
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();

        List<RepaymentScheduleResponse> responseList = Collections.singletonList(response);

        when(repaymentScheduleService.getByLoanId(loanId)).thenReturn(responseList);

        // 2. WHEN & THEN (Eksekusi dan Validasi)
        mockMvc.perform(get("/api/v1/loan-applications/{loanId}/repayment-schedules", loanId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Repayment schedules retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/repayment-schedules/{id} - Harus Sukses Mengambil Data Schedule Berdasarkan ID")
    void getById_ShouldReturnSingleObject() throws Exception {
        // 1. GIVEN (Persiapan Data)
        Long scheduleId = 1L;
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();

        when(repaymentScheduleService.getById(scheduleId)).thenReturn(response);

        // 2. WHEN & THEN (Eksekusi dan Validasi)
        mockMvc.perform(get("/api/v1/repayment-schedules/{id}", scheduleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Repayment schedule retrieved successfully"))
                .andExpect(jsonPath("$.data").exists());
    }
}
