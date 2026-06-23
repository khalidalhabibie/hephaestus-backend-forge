package com.example.dbbackend;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dbbackend.common.exception.LoanApplicationNotFoundException;
import com.example.dbbackend.common.exception.RepaymentScheduleNotFoundException;
import com.example.dbbackend.loanapplication.entity.LoanApplicationEntity;
import com.example.dbbackend.loanapplication.repository.LoanApplicationRepository;
import com.example.dbbackend.repaymentschedule.dto.RepaymentScheduleResponse;
import com.example.dbbackend.repaymentschedule.entity.RepaymentScheduleEntity;
import com.example.dbbackend.repaymentschedule.entity.RepaymentStatus;
import com.example.dbbackend.repaymentschedule.repository.RepaymentScheduleRepository;
import com.example.dbbackend.repaymentschedule.service.RepaymentScheduleService;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

        @Mock
        private RepaymentScheduleRepository repository;

        @Mock
        private LoanApplicationRepository loanRepository;

        @InjectMocks
        private RepaymentScheduleService service;

        // =========================================================================
        // SKENARIO 1: GET BY LOAN ID (SUKSES)
        // =========================================================================
        @Test
        void shouldGetRepaymentScheduleByLoanIdSuccessfully() {
                // 1. SIAPKAN BAHAN (Arrange)
                Long loanId = 1L;
                LoanApplicationEntity loanPalsu = new LoanApplicationEntity();
                loanPalsu.setId(loanId);

                RepaymentScheduleEntity schedulePalsu = new RepaymentScheduleEntity();
                schedulePalsu.setId(100L);
                schedulePalsu.setInstallmentNumber(1);
                schedulePalsu.setDueDate(LocalDate.now());
                schedulePalsu.setPrincipalAmount(BigDecimal.valueOf(1000000));
                schedulePalsu.setInterestAmount(BigDecimal.valueOf(100000));
                schedulePalsu.setTotalAmount(BigDecimal.valueOf(1100000));
                schedulePalsu.setStatus("UNPAID"); // Harus sesuai dengan Nama Enum di RepaymentStatus

                when(loanRepository.findById(loanId))
                                .thenReturn(Optional.of(loanPalsu));

                when(repository.findByLoanApplicationId(loanId))
                                .thenReturn(List.of(schedulePalsu));

                // 2. JALANKAN FUNGSI (Act)
                List<RepaymentScheduleResponse> hasilAkhir = service.getByLoanId(loanId);

                // 3. CEK HASILNYA (Assert)
                assertNotNull(hasilAkhir);
                assertEquals(1, hasilAkhir.size());
                assertEquals(100L, hasilAkhir.get(0).getId());
                assertEquals(RepaymentStatus.UNPAID, hasilAkhir.get(0).getStatus());

                verify(loanRepository).findById(loanId);
                verify(repository).findByLoanApplicationId(loanId);
        }

        // SKENARIO 2: GET BY LOAN ID (GAGAL KARENA LOAN TIDAK ADA)

        @Test
        void shouldFailGetByLoanIdWhenLoanNotFound() {

                Long loanId = 99L;
                when(loanRepository.findById(loanId))
                                .thenReturn(Optional.empty());

                assertThrows(LoanApplicationNotFoundException.class,
                                () -> service.getByLoanId(loanId));
        }

        // SKENARIO 3: GET BY ID SCHEDULE (SUKSES)

        @Test
        void shouldGetRepaymentScheduleByIdSuccessfully() {

                Long scheduleId = 100L;
                RepaymentScheduleEntity schedulePalsu = new RepaymentScheduleEntity();
                schedulePalsu.setId(scheduleId);
                schedulePalsu.setInstallmentNumber(2);
                schedulePalsu.setDueDate(LocalDate.now());
                schedulePalsu.setPrincipalAmount(BigDecimal.valueOf(2000000));
                schedulePalsu.setInterestAmount(BigDecimal.valueOf(200000));
                schedulePalsu.setTotalAmount(BigDecimal.valueOf(2200000));
                schedulePalsu.setStatus("PAID");

                when(repository.findById(scheduleId))
                                .thenReturn(Optional.of(schedulePalsu));

                RepaymentScheduleResponse hasilAkhir = service.getById(scheduleId);

                assertNotNull(hasilAkhir);
                assertEquals(scheduleId, hasilAkhir.getId());
                assertEquals(2, hasilAkhir.getInstallmentNumber());
                assertEquals(RepaymentStatus.PAID, hasilAkhir.getStatus());

                verify(repository).findById(scheduleId);
        }

        // SKENARIO 4: GET BY ID SCHEDULE (GAGAL KARENA JADWAL TIDAK ADA)

        @Test
        void shouldFailGetByIdWhenScheduleNotFound() {

                Long scheduleId = 999L;
                when(repository.findById(scheduleId))
                                .thenReturn(Optional.empty());

                assertThrows(RepaymentScheduleNotFoundException.class,
                                () -> service.getById(scheduleId));
        }
}
