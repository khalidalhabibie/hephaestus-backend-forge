package com.fif.exercise2.service;

import com.fif.exercise2.dto.RepaymentScheduleResponse;
import com.fif.exercise2.entity.LoanApplicationEntity;
import com.fif.exercise2.entity.RepaymentScheduleEntity;
import com.fif.exercise2.exception.RepaymentScheduleNotFoundException;
import com.fif.exercise2.repository.RepaymentScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

        @Mock
        private RepaymentScheduleRepository repaymentScheduleRepository;

        @InjectMocks
        private RepaymentScheduleService repaymentScheduleService;

        // ==========================================================================
        // getByLoanApplicationId — 2 branch: dengan filter status dan tanpa filter
        // ==========================================================================

        @Test
        void should_return_schedules_by_loan_application_id_without_status_filter() {
                // given — status null → ambil semua schedule milik loan tersebut
                List<RepaymentScheduleEntity> entities = List.of(
                        buildSchedule(1L, 1, "UNPAID"),
                        buildSchedule(2L, 2, "UNPAID")
                );
                when(repaymentScheduleRepository.findByLoanApplicationId(10L)).thenReturn(entities);

                // when
                List<RepaymentScheduleResponse> responses =
                        repaymentScheduleService.getByLoanApplicationId(10L, null);

                // then
                assertEquals(2, responses.size());
                assertEquals(1, responses.get(0).getInstallmentNumber());
                assertEquals(2, responses.get(1).getInstallmentNumber());

                // pastikan method yang dipanggil adalah yang tanpa filter status
                verify(repaymentScheduleRepository).findByLoanApplicationId(10L);
                verify(repaymentScheduleRepository, never())
                        .findByLoanApplicationIdAndStatus(anyLong(), anyString());
        }

        @Test
        void should_return_schedules_by_loan_application_id_with_status_filter() {
                // given — status "PAID" → ambil hanya yang sudah lunas
                List<RepaymentScheduleEntity> entities = List.of(
                        buildSchedule(1L, 1, "PAID")
                );
                when(repaymentScheduleRepository.findByLoanApplicationIdAndStatus(10L, "PAID"))
                        .thenReturn(entities);

                // when
                List<RepaymentScheduleResponse> responses =
                        repaymentScheduleService.getByLoanApplicationId(10L, "PAID");

                // then
                assertEquals(1, responses.size());
                assertEquals("PAID", responses.get(0).getStatus());

                // pastikan method yang dipanggil adalah yang dengan filter status
                verify(repaymentScheduleRepository).findByLoanApplicationIdAndStatus(10L, "PAID");
                verify(repaymentScheduleRepository, never()).findByLoanApplicationId(anyLong());
        }

        @Test
        void should_return_empty_list_when_no_schedules_exist() {
                // given
                when(repaymentScheduleRepository.findByLoanApplicationId(99L)).thenReturn(List.of());

                // when
                List<RepaymentScheduleResponse> responses =
                        repaymentScheduleService.getByLoanApplicationId(99L, null);

                // then — tidak error, kembalikan list kosong
                assertNotNull(responses);
                assertTrue(responses.isEmpty());
        }

        // ==========================================================================
        // getById
        // ==========================================================================

        @Test
        void should_get_repayment_schedule_by_id_successfully() {
                // given
                RepaymentScheduleEntity entity = buildSchedule(1L, 3, "PARTIAL");
                when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(entity));

                // when
                RepaymentScheduleResponse response = repaymentScheduleService.getById(1L);

                // then
                assertNotNull(response);
                assertEquals(1L, response.getId());
                assertEquals(3, response.getInstallmentNumber());
                assertEquals("PARTIAL", response.getStatus());
        }

        @Test
        void should_throw_not_found_when_repayment_schedule_does_not_exist() {
                // given
                when(repaymentScheduleRepository.findById(99L)).thenReturn(Optional.empty());

                // when & then
                assertThrows(RepaymentScheduleNotFoundException.class,
                        () -> repaymentScheduleService.getById(99L));
        }

        // ==========================================================================
        // Helpers
        // ==========================================================================

        private RepaymentScheduleEntity buildSchedule(Long id, int installmentNumber, String status) {
                LoanApplicationEntity loan = new LoanApplicationEntity();
                loan.setId(10L);

                RepaymentScheduleEntity entity = new RepaymentScheduleEntity();
                entity.setId(id);
                entity.setLoanApplication(loan);
                entity.setInstallmentNumber(installmentNumber);
                entity.setDueDate(LocalDate.now().plusMonths(installmentNumber));
                entity.setPrincipalAmount(new BigDecimal("900000"));
                entity.setInterestAmount(new BigDecimal("100000"));
                entity.setTotalAmount(new BigDecimal("1000000"));
                entity.setStatus(status);
                entity.setCreatedAt(ZonedDateTime.now());
                entity.setUpdatedAt(ZonedDateTime.now());
                return entity;
        }
        }
