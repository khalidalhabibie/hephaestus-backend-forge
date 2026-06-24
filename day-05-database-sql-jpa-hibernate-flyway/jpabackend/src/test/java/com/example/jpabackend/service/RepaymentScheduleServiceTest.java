package com.example.jpabackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.jpabackend.dto.request.UpdateRepaymentScheduleStatusRequest;
import com.example.jpabackend.dto.response.RepaymentScheduleResponse;
import com.example.jpabackend.entity.LoanApplicationEntity;
import com.example.jpabackend.entity.RepaymentScheduleEntity;
import com.example.jpabackend.enums.RepaymentStatus;
import com.example.jpabackend.exception.LoanApplicationNotFoundException;
import com.example.jpabackend.exception.RepaymentScheduleNotFoundException;
import com.example.jpabackend.repository.LoanApplicationRepository;
import com.example.jpabackend.repository.RepaymentScheduleRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private RepaymentScheduleService repaymentScheduleService;

    @Test
    void should_return_all_schedules() {
    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(900))
        .interestAmount(BigDecimal.valueOf(100))
        .totalAmount(BigDecimal.valueOf(1000))
        .status(RepaymentStatus.UNPAID)
        .build();
        schedule.setId(1L);
    when(repaymentScheduleRepository.findAll())
        .thenReturn(List.of(schedule));
    List<RepaymentScheduleResponse> result = repaymentScheduleService.getAll();
    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    }

    @Test
    void should_get_schedule_by_id_successfully() {
    Long id = 1L;
    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(900))
        .interestAmount(BigDecimal.valueOf(100))
        .totalAmount(BigDecimal.valueOf(1000))
        .status(RepaymentStatus.UNPAID)
        .build();
        schedule.setId(id);
    when(repaymentScheduleRepository.findByIdWithLoanApplication(id))
        .thenReturn(Optional.of(schedule));
    RepaymentScheduleResponse response = repaymentScheduleService.getScheduleById(id);
    assertEquals(id, response.getId());
    assertEquals(RepaymentStatus.UNPAID, response.getStatus());
    }

    @Test
    void should_throw_not_found_when_schedule_by_id_not_found() {
    Long id = 99L;
    when(repaymentScheduleRepository.findByIdWithLoanApplication(id))
        .thenReturn(Optional.empty());
    assertThrows(RepaymentScheduleNotFoundException.class, () -> repaymentScheduleService.getScheduleById(id));
    }

    @Test
    void should_get_schedules_by_loan_application_successfully() {
    Long loanId = 1L;
    LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);

    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(900))
        .interestAmount(BigDecimal.valueOf(100))
        .totalAmount(BigDecimal.valueOf(1000))
        .status(RepaymentStatus.UNPAID)
        .build();
        schedule.setId(1L);
    when(loanApplicationRepository.existsById(loanId))
        .thenReturn(true);
    when(repaymentScheduleRepository.findByLoanApplicationId(loanId))
        .thenReturn(List.of(schedule));
    List<RepaymentScheduleResponse> result = repaymentScheduleService.getSchedulesByLoanApplication(loanId);
    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    }

    @Test
    void should_throw_not_found_when_loan_application_not_found() {
    Long loanId = 99L;
    when(loanApplicationRepository.existsById(loanId))
        .thenReturn(false);
    assertThrows(LoanApplicationNotFoundException.class, () -> repaymentScheduleService.getSchedulesByLoanApplication(loanId));
    }

    @Test
    void should_get_by_status_successfully() {
    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(900))
        .interestAmount(BigDecimal.valueOf(100))
        .totalAmount(BigDecimal.valueOf(1000))
        .status(RepaymentStatus.UNPAID)
        .build();
        schedule.setId(1L);
    when(repaymentScheduleRepository.findByStatus(RepaymentStatus.UNPAID))
        .thenReturn(List.of(schedule));
    List<RepaymentScheduleResponse> result = repaymentScheduleService.getByStatus(RepaymentStatus.UNPAID);
    assertEquals(1, result.size());
    }

    @Test
    void should_update_schedule_status_successfully() {
    Long id = 1L;
    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(900))
        .interestAmount(BigDecimal.valueOf(100))
        .totalAmount(BigDecimal.valueOf(1000))
        .status(RepaymentStatus.UNPAID)
        .build();
        schedule.setId(id);
    UpdateRepaymentScheduleStatusRequest request = new UpdateRepaymentScheduleStatusRequest();
        request.setStatus(RepaymentStatus.PAID);
    when(repaymentScheduleRepository.findById(id))
        .thenReturn(Optional.of(schedule));
    RepaymentScheduleResponse response = repaymentScheduleService.updateScheduleStatus(id, request);
    assertEquals(RepaymentStatus.PAID, response.getStatus());
    }

    @Test
    void should_throw_not_found_when_update_status_not_found() {
    Long id = 99L;
    UpdateRepaymentScheduleStatusRequest request = new UpdateRepaymentScheduleStatusRequest();
        request.setStatus(RepaymentStatus.PAID);
    when(repaymentScheduleRepository.findById(id))
        .thenReturn(Optional.empty());
    assertThrows(RepaymentScheduleNotFoundException.class, () -> repaymentScheduleService.updateScheduleStatus(id, request));
    }

    @Test
    void should_delete_schedule_successfully() {
    Long id = 1L;
    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(900))
        .interestAmount(BigDecimal.valueOf(100))
        .totalAmount(BigDecimal.valueOf(1000))
        .status(RepaymentStatus.UNPAID)
        .build();
        schedule.setId(id);
    when(repaymentScheduleRepository.findById(id))
        .thenReturn(Optional.of(schedule));
        repaymentScheduleService.deleteSchedule(id);
    assertEquals(true, schedule.isDeleted());
    verify(repaymentScheduleRepository).save(schedule);
    }

    @Test
    void should_throw_not_found_when_delete_not_found() {
    Long id = 99L;
    when(repaymentScheduleRepository.findById(id))
        .thenReturn(Optional.empty());
    assertThrows(RepaymentScheduleNotFoundException.class, () -> repaymentScheduleService.deleteSchedule(id));
    }
}