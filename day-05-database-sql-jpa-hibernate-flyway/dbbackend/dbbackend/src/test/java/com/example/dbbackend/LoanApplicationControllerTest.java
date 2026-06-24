package com.example.dbbackend;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.dbbackend.loanapplication.controller.LoanApplicationController;
import com.example.dbbackend.loanapplication.dto.CreateLoanApplicationRequest;
import com.example.dbbackend.loanapplication.dto.LoanApplicationResponse;
import com.example.dbbackend.loanapplication.dto.UpdateLoanStatusRequest;
import com.example.dbbackend.loanapplication.entity.LoanApplicationStatus;
import com.example.dbbackend.loanapplication.service.LoanApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LoanApplicationController.class)
class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private LoanApplicationService service;

    // SKENARIO 1: POST (CREATE LOAN - SUKSES)

    @Test
    void shouldCreateLoanSuccessfully() throws Exception {
        CreateLoanApplicationRequest request = new CreateLoanApplicationRequest();

        request.setCustomerId(1L);
        request.setLoanAmount(BigDecimal.valueOf(10000000));
        request.setTenorMonth(12);

        LoanApplicationResponse responsePalsu = new LoanApplicationResponse();
        responsePalsu.setId(1L);

        when(service.createLoan(any(CreateLoanApplicationRequest.class)))
                .thenReturn(responsePalsu);

        mockMvc.perform(post("/api/v1/loan-applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Loan created successfully"))
                .andExpect(jsonPath("$.data.id").value(1L));

        verify(service).createLoan(any(CreateLoanApplicationRequest.class));
    }

    // SKENARIO 2: GET BY ID (SUKSES)

    @Test
    void shouldGetLoanByIdSuccessfully() throws Exception {

        Long loanId = 1L;
        LoanApplicationResponse responsePalsu = new LoanApplicationResponse();
        responsePalsu.setId(loanId);

        when(service.getLoanById(loanId)).thenReturn(responsePalsu);

        mockMvc.perform(get("/api/v1/loan-applications/{id}", loanId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Loan retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(loanId));

        verify(service).getLoanById(loanId);
    }

    // SKENARIO 3: GET ALL (TANPA FILTER STATUS)

    @Test
    void shouldGetAllLoanApplications() throws Exception {

        LoanApplicationResponse responsePalsu = new LoanApplicationResponse();
        responsePalsu.setId(1L);
        List<LoanApplicationResponse> daftarPalsu = List.of(responsePalsu);

        when(service.getAll()).thenReturn(daftarPalsu);

        mockMvc.perform(get("/api/v1/loan-applications")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All loan retrieved"))
                .andExpect(jsonPath("$.data[0].id").value(1L));

        verify(service).getAll();
    }

    // SKENARIO 4: GET ALL (DENGAN FILTER STATUS)

    @Test
    void shouldGetLoanApplicationsFilteredByStatus() throws Exception {

        String statusParam = "APPROVED";
        LoanApplicationResponse responsePalsu = new LoanApplicationResponse();
        responsePalsu.setId(2L);
        responsePalsu.setStatus(LoanApplicationStatus.APPROVED);
        List<LoanApplicationResponse> daftarPalsu = List.of(responsePalsu);

        when(service.getByStatus(statusParam)).thenReturn(daftarPalsu);

        mockMvc.perform(get("/api/v1/loan-applications")
                .param("status", statusParam)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Loan filtered by status"))
                .andExpect(jsonPath("$.data[0].id").value(2L))
                .andExpect(jsonPath("$.data[0].status").value("APPROVED"));

        verify(service).getByStatus(statusParam);
    }

    // SKENARIO 5: PATCH UPDATE STATUS (SUKSES)

    @Test
    void shouldUpdateLoanStatusSuccessfully() throws Exception {

        Long loanId = 1L;
        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();

        request.setStatus(LoanApplicationStatus.APPROVED);

        LoanApplicationResponse responsePalsu = new LoanApplicationResponse();
        responsePalsu.setId(loanId);
        responsePalsu.setStatus(LoanApplicationStatus.APPROVED);

        when(service.updateStatus(loanId, LoanApplicationStatus.APPROVED))
                .thenReturn(responsePalsu);

        mockMvc.perform(patch("/api/v1/loan-applications/{id}/status", loanId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Status updated successfully"))
                .andExpect(jsonPath("$.data.id").value(loanId))
                .andExpect(jsonPath("$.data.status").value("APPROVED"));

        verify(service).updateStatus(loanId, LoanApplicationStatus.APPROVED);
    }

    // SKENARIO 6: POST VALIDASI GAGAL (BAD REQUEST)

    @Test
    void shouldFailCreateLoanWhenInputInvalid() throws Exception {
        CreateLoanApplicationRequest requestKosong = new CreateLoanApplicationRequest();

        mockMvc.perform(post("/api/v1/loan-applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestKosong)))
                .andExpect(status().isBadRequest());
    }
}
