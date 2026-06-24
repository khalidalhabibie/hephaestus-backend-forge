package com.example.dbbackend;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dbbackend.common.exception.RepaymentScheduleNotFoundException;
import com.example.dbbackend.paymenttransaction.dto.CreatePaymentTransactionRequest;
import com.example.dbbackend.paymenttransaction.dto.PaymentTransactionResponse;
import com.example.dbbackend.paymenttransaction.entity.PaymentTransactionEntity;
import com.example.dbbackend.paymenttransaction.entity.PaymentTransactionStatus;
import com.example.dbbackend.paymenttransaction.repository.PaymentTransactionRepository;
import com.example.dbbackend.paymenttransaction.service.PaymentTransactionService;
import com.example.dbbackend.repaymentschedule.entity.RepaymentScheduleEntity;
import com.example.dbbackend.repaymentschedule.repository.RepaymentScheduleRepository;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

        @Mock
        private PaymentTransactionRepository repository;

        @Mock
        private RepaymentScheduleRepository scheduleRepository;

        @InjectMocks
        private PaymentTransactionService service;

        // SKENARIO 1: CREATE PAYMENT (SUKSES & JADWAL CICILAN BERUBAH JADI 'PAID')

        @Test
        void shouldCreatePaymentSuccessfullyAndMarkScheduleAsPaid() {
                // GIVEN
                CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
                request.setRepaymentScheduleId(1L);
                request.setPaymentReference("REF-12345");
                request.setPaidAmount(BigDecimal.valueOf(100000));
                request.setPaidAt(LocalDateTime.now());

                RepaymentScheduleEntity schedulePalsu = new RepaymentScheduleEntity();
                schedulePalsu.setId(1L);
                schedulePalsu.setTotalAmount(BigDecimal.valueOf(100000));
                schedulePalsu.setStatus("UNPAID");

                when(scheduleRepository.findById(1L))
                                .thenReturn(Optional.of(schedulePalsu));

                when(repository.save(any(PaymentTransactionEntity.class))).thenAnswer(i -> {
                        PaymentTransactionEntity entity = i.getArgument(0);
                        entity.setId(100L);
                        return entity;
                });

                when(repository.sumPaidAmountByScheduleId(1L))
                                .thenReturn(BigDecimal.valueOf(100000));

                when(scheduleRepository.save(any(RepaymentScheduleEntity.class)))
                                .thenReturn(schedulePalsu);

                // GIVEN
                PaymentTransactionResponse response = service.createPayment(request);

                // THEN
                assertNotNull(response);
                assertEquals(100L, response.getId());
                assertEquals("REF-12345", response.getPaymentReference());
                assertEquals(PaymentTransactionStatus.SUCCESS, response.getStatus());

                assertEquals("PAID", schedulePalsu.getStatus());
                verify(scheduleRepository).save(schedulePalsu);
        }

        // SKENARIO 2: CREATE PAYMENT (SUKSES TAPI BELUM LUNAS TOTAL / TETAP 'UNPAID')

        @Test
        void shouldCreatePaymentSuccessfullyButKeepScheduleUnpaid() {
                // GIVEN
                CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
                request.setRepaymentScheduleId(1L);
                request.setPaidAmount(BigDecimal.valueOf(50000));

                RepaymentScheduleEntity schedulePalsu = new RepaymentScheduleEntity();
                schedulePalsu.setId(1L);
                schedulePalsu.setTotalAmount(BigDecimal.valueOf(100000));
                schedulePalsu.setStatus("UNPAID");

                when(scheduleRepository.findById(1L))
                                .thenReturn(Optional.of(schedulePalsu));

                when(repository.save(any(PaymentTransactionEntity.class))).thenAnswer(i -> {
                        PaymentTransactionEntity entity = i.getArgument(0);
                        entity.setId(101L);
                        return entity;
                });

                when(repository.sumPaidAmountByScheduleId(1L))
                                .thenReturn(BigDecimal.valueOf(50000));

                // WHEN
                PaymentTransactionResponse response = service.createPayment(request);

                // THEN
                assertNotNull(response);
                assertEquals("UNPAID", schedulePalsu.getStatus());

                verify(scheduleRepository, times(0)).save(any(RepaymentScheduleEntity.class));
        }

        // SKENARIO 3: CREATE PAYMENT (GAGAL KARENA JADWAL TIDAK DITEMUKAN)

        @Test
        void shouldFailCreatePaymentWhenScheduleNotFound() {
                // GIVEN
                CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
                request.setRepaymentScheduleId(99L);

                when(scheduleRepository.findById(99L))
                                .thenReturn(Optional.empty());

                // WHEN & THEN
                assertThrows(RepaymentScheduleNotFoundException.class,
                                () -> service.createPayment(request));
        }

        // SKENARIO 4: GET BY SCHEDULE (SUKSES MENGEMBALIKAN DAFTAR TRANSAKSI)

        @Test
        void shouldGetTransactionsByScheduleId() {
                // GIVEN
                PaymentTransactionEntity transaksiPalsu = new PaymentTransactionEntity();
                transaksiPalsu.setId(200L);
                transaksiPalsu.setPaymentReference("REF-OK");
                transaksiPalsu.setPaidAmount(BigDecimal.valueOf(100000));
                transaksiPalsu.setStatus("SUCCESS");

                List<PaymentTransactionEntity> daftarTransaksiPalsu = List.of(transaksiPalsu);

                when(repository.findByRepaymentScheduleId(1L))
                                .thenReturn(daftarTransaksiPalsu);
                // WHEN
                List<PaymentTransactionResponse> hasilAkhir = service.getBySchedule(1L);

                // THEN
                assertNotNull(hasilAkhir);
                assertEquals(1, hasilAkhir.size());
                assertEquals(200L, hasilAkhir.get(0).getId());
                assertEquals(PaymentTransactionStatus.SUCCESS, hasilAkhir.get(0).getStatus());
        }
}
