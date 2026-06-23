package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.RepaymentScheduleResponse;
import com.example.demoSpringbootDatabase.entity.LoanApplicationEntity;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.RepaymentScheduleNotFoundException;
import com.example.demoSpringbootDatabase.repository.RepaymentScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

    @Mock
    private RepaymentScheduleRepository scheduleRepository;

    @InjectMocks
    private RepaymentScheduleService scheduleService;

    private RepaymentScheduleEntity scheduleEntity;
    private LoanApplicationEntity loanApplication;

    @BeforeEach
    void setUp() {
        loanApplication = LoanApplicationEntity.builder().id(10L).build();
        
        scheduleEntity = RepaymentScheduleEntity.builder()
                .id(100L)
                .loanApplication(loanApplication)
                .installmentNumber(1)
                .dueDate(LocalDate.now().plusMonths(1))
                .principalAmount(3333333L)
                .interestAmount(200000L)
                .totalAmount(3533333L)
                .status("UNPAID")
                .build();
    }

    @Test
    @DisplayName("Happy Path: Ambil Jadwal Cicilan Berdasarkan Loan ID")
    void getByLoanId_HappyPath_Success() {
        // Given
        when(scheduleRepository.findByLoanApplicationId(10L))
                .thenReturn(List.of(scheduleEntity));

        // When
        List<RepaymentScheduleResponse> responses = scheduleService.getByLoanId(10L);

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("UNPAID", responses.get(0).getStatus());
        assertEquals(3533333L, responses.get(0).getTotalAmount());
        verify(scheduleRepository, times(1)).findByLoanApplicationId(10L);
    }

    @Test
    @DisplayName("Happy Path: Get Schedule Berdasarkan ID Tunggal Sukses")
    void getById_HappyPath_Success() {
        // Given
        when(scheduleRepository.findById(100L)).thenReturn(Optional.of(scheduleEntity));

        // When
        RepaymentScheduleResponse response = scheduleService.getById(100L);

        // Then
        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(1, response.getInstallmentNumber());
    }

    @Test
    @DisplayName("Negative Path: Get Schedule Gagal karena ID Tidak Ditemukan")
    void getById_NegativePath_NotFound() {
        // Given
        when(scheduleRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RepaymentScheduleNotFoundException.class, () -> scheduleService.getById(999L));
        verify(scheduleRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Happy Path: Hitung Outstanding Saldo Customer")
    void getCustomerOutstanding_HappyPath_Success() {
        // Given
        BigDecimal expectedOutstanding = BigDecimal.valueOf(17666665);
        when(scheduleRepository.getOutstandingAmountByCustomerId(1L)).thenReturn(expectedOutstanding);

        // When
        BigDecimal actualOutstanding = scheduleService.getCustomerOutstanding(1L);

        // Then
        assertNotNull(actualOutstanding);
        assertEquals(expectedOutstanding, actualOutstanding);
        verify(scheduleRepository, times(1)).getOutstandingAmountByCustomerId(1L);
    }

    @Test
    @DisplayName("Happy Path: Filter Cicilan Berdasarkan Loan ID dan Status")
    void getByLoanIdAndStatus_HappyPath_WithStatus() {
        // Given
        when(scheduleRepository.findByLoanApplicationIdAndStatus(10L, "UNPAID"))
                .thenReturn(List.of(scheduleEntity));

        // When
        List<RepaymentScheduleResponse> responses = scheduleService.getByLoanIdAndStatus(10L, "unpaid");

        // Then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        verify(scheduleRepository, times(1)).findByLoanApplicationIdAndStatus(10L, "UNPAID");
    }

    @Test
    @DisplayName("Happy Path: Filter Cicilan Berdasarkan Loan ID Tanpa Status (Status Kosong)")
    void getByLoanIdAndStatus_HappyPath_WithoutStatus() {
        // Given
        when(scheduleRepository.findByLoanApplicationId(10L)).thenReturn(List.of(scheduleEntity));

        // When
        List<RepaymentScheduleResponse> responses = scheduleService.getByLoanIdAndStatus(10L, null);

        // Then
        assertNotNull(responses);
        verify(scheduleRepository, times(1)).findByLoanApplicationId(10L);
        verify(scheduleRepository, never()).findByLoanApplicationIdAndStatus(anyLong(), anyString());
    }
}