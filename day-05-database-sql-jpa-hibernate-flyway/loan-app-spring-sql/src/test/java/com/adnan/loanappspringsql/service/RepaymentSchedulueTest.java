package com.adnan.loanappspringsql.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adnan.loanappspringsql.dto.RepaymentScheduleResponse;
import com.adnan.loanappspringsql.enums.RepaymentStatusEnum;
import com.adnan.loanappspringsql.exception.NotFoundException;
import com.adnan.loanappspringsql.model.RepaymentSchedule;
import com.adnan.loanappspringsql.repository.LoanApplicationRepository;
import com.adnan.loanappspringsql.repository.RepaymentScheduleRepository;
import com.adnan.loanappspringsql.service.impl.RepaymentScheduleServiceImpl;

@ExtendWith(MockitoExtension.class)
class RepaymentSchedulueTest {
  @Mock
  private RepaymentScheduleRepository repaymentScheduleRepository;
  @Mock
  private LoanApplicationRepository loanApplicationRepository;

  @InjectMocks
  private RepaymentScheduleServiceImpl repaymentScheduleService;

  @Test
  void findByLoanApplicationId_shouldReturnSchedulesSuccessfully() {
    // Given
    RepaymentSchedule schedule = RepaymentSchedule.builder()
        .id(1L)
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(2_000_000))
        .interestAmount(BigDecimal.valueOf(200_000))
        .totalAmount(BigDecimal.valueOf(2_200_000))
        .status(RepaymentStatusEnum.UNPAID)
        .build();
    when(loanApplicationRepository.existsById(1L)).thenReturn(true);
    when(repaymentScheduleRepository.findByLoanApplicationId(1L)).thenReturn(List.of(schedule));

    // When
    List<RepaymentScheduleResponse> responses = repaymentScheduleService.findByLoanApplicationId(1L);

    // Then
    assertEquals(1, responses.size());
    assertEquals(1L, responses.get(0).getId());

    verify(loanApplicationRepository).existsById(1L);
    verify(repaymentScheduleRepository).findByLoanApplicationId(1L);
  }

  @Test
  void findByLoanApplicationId_shouldThrowNotFoundException() {
    // Given
    when(loanApplicationRepository.existsById(1L)).thenReturn(false);

    // When & Then
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> repaymentScheduleService.findByLoanApplicationId(1L));

    assertEquals("Loan application not found with id: 1", exception.getMessage());

    verify(repaymentScheduleRepository, never()).findByLoanApplicationId(anyLong());
  }

  @Test
  void findById_shouldReturnScheduleSuccessfully() {
    // Given
    RepaymentSchedule schedule = RepaymentSchedule.builder()
        .id(1L)
        .installmentNumber(1)
        .dueDate(LocalDate.now())
        .principalAmount(BigDecimal.valueOf(2_000_000))
        .interestAmount(BigDecimal.valueOf(200_000))
        .totalAmount(BigDecimal.valueOf(2_200_000))
        .status(RepaymentStatusEnum.UNPAID)
        .build();
    when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

    // When
    RepaymentScheduleResponse response = repaymentScheduleService.findById(1L);

    // Then
    assertEquals(1L, response.getId());

    verify(repaymentScheduleRepository).findById(1L);
  }

  @Test
  void findById_shouldThrowNotFoundException() {
    // Given
    when(repaymentScheduleRepository.findById(1L)).thenReturn(Optional.empty());

    // When & Then
    NotFoundException exception = assertThrows(NotFoundException.class, () -> repaymentScheduleService.findById(1L));

    assertEquals("Repayment schedule not found with id: 1", exception.getMessage());
  }

  @Test
  void findByStatus_shouldReturnSchedulesSuccessfully() {
    // Given
    RepaymentSchedule schedule = RepaymentSchedule.builder()
        .id(1L)
        .status(RepaymentStatusEnum.UNPAID)
        .build();
    when(repaymentScheduleRepository.findByStatus(RepaymentStatusEnum.UNPAID)).thenReturn(List.of(schedule));

    // When
    List<RepaymentScheduleResponse> responses = repaymentScheduleService.findByStatus(RepaymentStatusEnum.UNPAID);

    // Then
    assertEquals(1, responses.size());

    verify(repaymentScheduleRepository).findByStatus(RepaymentStatusEnum.UNPAID);
  }

  @Test
  void findByStatus_shouldReturnEmptyList() {
    // Given
    when(repaymentScheduleRepository.findByStatus(RepaymentStatusEnum.UNPAID)).thenReturn(Collections.emptyList());

    // When
    List<RepaymentScheduleResponse> responses = repaymentScheduleService.findByStatus(RepaymentStatusEnum.UNPAID);

    // Then
    assertTrue(responses.isEmpty());
  }

  @Test
  void findByLoanApplicationIdAndStatus_shouldReturnSchedulesSuccessfully() {
    // Given
    RepaymentSchedule schedule = RepaymentSchedule.builder()
        .id(1L)
        .status(RepaymentStatusEnum.PAID)
        .build();
    when(repaymentScheduleRepository.findByLoanApplicationIdAndStatus(1L, RepaymentStatusEnum.PAID))
        .thenReturn(List.of(schedule));

    // When
    List<RepaymentScheduleResponse> responses = repaymentScheduleService.findByLoanApplicationIdAndStatus(1L,
        RepaymentStatusEnum.PAID);

    // Then
    assertEquals(1, responses.size());

    verify(repaymentScheduleRepository).findByLoanApplicationIdAndStatus(1L, RepaymentStatusEnum.PAID);
  }

  @Test
  void findByLoanApplicationIdAndStatus_shouldReturnEmptyList() {
    // Given
    when(repaymentScheduleRepository.findByLoanApplicationIdAndStatus(1L, RepaymentStatusEnum.PAID))
        .thenReturn(Collections.emptyList());

    // When
    List<RepaymentScheduleResponse> responses = repaymentScheduleService.findByLoanApplicationIdAndStatus(1L,
        RepaymentStatusEnum.PAID);

    // Then
    assertTrue(responses.isEmpty());
  }
}
