package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.CreatePaymentTransactionRequest;
import com.example.demoSpringbootDatabase.dto.PaymentTransactionResponse;
import com.example.demoSpringbootDatabase.entity.LoanApplicationEntity;
import com.example.demoSpringbootDatabase.entity.PaymentTransactionEntity;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.RepaymentScheduleNotFoundException;
import com.example.demoSpringbootDatabase.repository.PaymentTransactionRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @Mock private PaymentTransactionRepository transactionRepository;
    @Mock private RepaymentScheduleRepository scheduleRepository;

    @InjectMocks private PaymentTransactionService transactionService;

    private RepaymentScheduleEntity schedule;
    private CreatePaymentTransactionRequest paymentRequest;

    @BeforeEach
    void setUp() {
        LoanApplicationEntity loan = LoanApplicationEntity.builder().id(10L).build();
        schedule = RepaymentScheduleEntity.builder()
                .id(100L)
                .loanApplication(loan)
                .installmentNumber(1)
                .dueDate(LocalDate.now().plusMonths(1))
                .principalAmount(3333333L)
                .interestAmount(200000L)
                .totalAmount(3533333L)
                .status("UNPAID")
                .build();

        paymentRequest = new CreatePaymentTransactionRequest();
        paymentRequest.setRepaymentScheduleId(100L);
        paymentRequest.setPaidAmount(3533333L);
        paymentRequest.setPaymentReference("BANK-REF-12345");
        paymentRequest.setPaidAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Happy Path: Transaksi Pembayaran Sukses Mengubah Status Cicilan Menjadi PAID")
    void createTransaction_HappyPath_Success() {
        // Given
        when(scheduleRepository.findById(100L)).thenReturn(Optional.of(schedule));
        when(transactionRepository.sumPaidAmountByScheduleId(100L)).thenReturn(BigDecimal.valueOf(3533333));

        PaymentTransactionEntity mockTrx = PaymentTransactionEntity.builder()
                .id(500L)
                .repaymentSchedule(schedule)
                .paymentReference("BANK-REF-12345")
                .paidAmount(3533333L)
                .status("SUCCESS")
                .build();
        when(transactionRepository.save(any(PaymentTransactionEntity.class))).thenReturn(mockTrx);

        // When
        PaymentTransactionResponse response = transactionService.createTransaction(paymentRequest);

        // Then
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("PAID", schedule.getStatus());
        verify(scheduleRepository, times(1)).save(schedule);
    }

    @Test
    @DisplayName("Happy Path: Pengecekan Kondisi saat totalPaidSoFar bernilai null")
    void createTransaction_HappyPath_TotalPaidSoFarIsNull() {
        // Given
        when(scheduleRepository.findById(100L)).thenReturn(Optional.of(schedule));
        // Mengembalikan null untuk memaksa masuk ke blok if (totalPaidSoFar == null)
        when(transactionRepository.sumPaidAmountByScheduleId(100L)).thenReturn(null);

        PaymentTransactionEntity mockTrx = PaymentTransactionEntity.builder()
                .id(500L)
                .repaymentSchedule(schedule)
                .paidAmount(3533333L)
                .status("SUCCESS")
                .build();
        when(transactionRepository.save(any(PaymentTransactionEntity.class))).thenReturn(mockTrx);

        // When
        PaymentTransactionResponse response = transactionService.createTransaction(paymentRequest);

        // Then
        assertNotNull(response);
        assertEquals("PAID", schedule.getStatus());
    }

    @Test
    @DisplayName("Negative Path: Transaksi Gagal karena ID Jadwal Cicilan Tidak Ditemukan")
    void createTransaction_NegativePath_ScheduleNotFound() {
        // Given
        when(scheduleRepository.findById(100L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RepaymentScheduleNotFoundException.class, 
                () -> transactionService.createTransaction(paymentRequest));
        verify(transactionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Happy Path: Mengambil Riwayat Pembayaran Berdasarkan ID Cicilan")
    void getByScheduleId_HappyPath_Success() {
        // Given
        PaymentTransactionEntity mockTrx = PaymentTransactionEntity.builder()
                .id(500L)
                .repaymentSchedule(schedule)
                .paidAmount(3533333L)
                .status("SUCCESS")
                .build();
        when(transactionRepository.findByRepaymentScheduleId(100L)).thenReturn(List.of(mockTrx));

        // When
        List<PaymentTransactionResponse> responses = transactionService.getByScheduleId(100L);

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(500L, responses.get(0).getId());
    }
}