package com.example.jpabackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.jpabackend.config.LoanInterestConfig;
import com.example.jpabackend.dto.request.CreateLoanApplicationRequest;
import com.example.jpabackend.dto.request.UpdateLoanStatusRequest;
import com.example.jpabackend.dto.response.LoanApplicationResponse;
import com.example.jpabackend.entity.CustomerEntity;
import com.example.jpabackend.entity.LoanApplicationEntity;
import com.example.jpabackend.entity.RepaymentScheduleEntity;
import com.example.jpabackend.enums.LoanStatus;
import com.example.jpabackend.enums.RepaymentStatus;
import com.example.jpabackend.exception.CustomerNotFoundException;
import com.example.jpabackend.exception.LoanApplicationNotFoundException;
import com.example.jpabackend.repository.CustomerRepository;
import com.example.jpabackend.repository.LoanApplicationRepository;
import com.example.jpabackend.repository.RepaymentScheduleRepository;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class LoanApplicationServiceTest {
    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @Mock
    private LoanInterestConfig loanInterestConfig;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    @Test
    void should_create_loan_application_successfully() {
    Long customerId = 1L;
    CreateLoanApplicationRequest request = CreateLoanApplicationRequest.builder()
        .customerId(customerId)
        .loanAmount(java.math.BigDecimal.valueOf(1000000))
        .tenorMonth(12)
        .purpose("Business")
        .build();
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .nik("123")
        .email("edith@mail.com")
        .phoneNumber("08123")
        .build();
        customer.setId(customerId);
    LoanApplicationEntity savedLoan = LoanApplicationEntity.builder()
        .customer(customer)
        .loanAmount(request.getLoanAmount())
        .tenorMonth(request.getTenorMonth())
        .purpose(request.getPurpose())
        .status(com.example.jpabackend.enums.LoanStatus.SUBMITTED)
        .build();
        savedLoan.setId(10L);
    when(customerRepository.findById(customerId))
        .thenReturn(java.util.Optional.of(customer));
    when(loanApplicationRepository.save(any()))
        .thenReturn(savedLoan);
    LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
    assertNotNull(response);
    assertEquals(10L, response.getId());
    assertEquals("Business", response.getPurpose());
    verify(loanApplicationRepository).save(any());
    }

    @Test
    void should_create_loan_application_and_return_response() {
    Long customerId = 1L;
    CreateLoanApplicationRequest request = CreateLoanApplicationRequest.builder()
        .customerId(customerId)
        .loanAmount(java.math.BigDecimal.valueOf(500000))
        .tenorMonth(12)
        .purpose("Startup")
        .build();
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("John")
        .nik("321")
        .email("john@mail.com")
        .phoneNumber("08123")
        .build();
        customer.setId(customerId);
    LoanApplicationEntity saved = LoanApplicationEntity.builder()
        .customer(customer)
        .loanAmount(request.getLoanAmount())
        .tenorMonth(request.getTenorMonth())
        .purpose(request.getPurpose())
        .status(com.example.jpabackend.enums.LoanStatus.SUBMITTED)
        .build();
        saved.setId(100L);
    when(customerRepository.findById(customerId))
        .thenReturn(java.util.Optional.of(customer));
    when(loanApplicationRepository.save(any(LoanApplicationEntity.class)))
        .thenReturn(saved);
    LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
    assertNotNull(response);
    assertEquals(100L, response.getId());
    assertEquals("Startup", response.getPurpose());
    assertEquals(customerId, response.getCustomer().getId());
    verify(customerRepository).findById(customerId);
    verify(loanApplicationRepository).save(any(LoanApplicationEntity.class));
    }

    @Test
    void should_throw_when_invalid_status_transition_from_submitted() {
    Long id = 1L;
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .status(com.example.jpabackend.enums.LoanStatus.SUBMITTED)
        .build();
        loan.setId(id);
    when(loanApplicationRepository.findById(id))
        .thenReturn(java.util.Optional.of(loan));
    UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
        .status(com.example.jpabackend.enums.LoanStatus.DISBURSED)
        .build();
    assertThrows(IllegalStateException.class, () -> loanApplicationService.updateLoanStatus(id, request));
    }

    @Test
    void should_throw_when_closing_with_unpaid_schedule() {
    Long id = 1L;
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .status(com.example.jpabackend.enums.LoanStatus.DISBURSED)
        .build();
        loan.setId(id);
    when(loanApplicationRepository.findById(id))
        .thenReturn(java.util.Optional.of(loan));
        com.example.jpabackend.entity.RepaymentScheduleEntity schedule =
        com.example.jpabackend.entity.RepaymentScheduleEntity.builder()
            .status(com.example.jpabackend.enums.RepaymentStatus.UNPAID)
            .build();
    when(repaymentScheduleRepository.findByLoanApplicationId(id))
        .thenReturn(java.util.List.of(schedule));
    UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
        .status(com.example.jpabackend.enums.LoanStatus.CLOSED)
        .build();
    assertThrows(IllegalStateException.class, () -> loanApplicationService.updateLoanStatus(id, request));
    }

    @Test
    void should_throw_when_rejected_state_updated() {
    Long id = 1L;
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .status(com.example.jpabackend.enums.LoanStatus.REJECTED)
        .build();
        loan.setId(id);
    when(loanApplicationRepository.findById(id))
        .thenReturn(java.util.Optional.of(loan));
    UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
        .status(com.example.jpabackend.enums.LoanStatus.APPROVED)
        .build();
    assertThrows(IllegalStateException.class, () -> loanApplicationService.updateLoanStatus(id, request));
    }

    @Test
    void should_throw_when_closed_state_updated() {
    Long id = 1L;
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .status(com.example.jpabackend.enums.LoanStatus.CLOSED)
        .build();
        loan.setId(id);
    when(loanApplicationRepository.findById(id))
        .thenReturn(java.util.Optional.of(loan));
    UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
        .status(com.example.jpabackend.enums.LoanStatus.DISBURSED)
        .build();
    assertThrows(IllegalStateException.class, () -> loanApplicationService.updateLoanStatus(id, request));
    }

    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
    CreateLoanApplicationRequest request = CreateLoanApplicationRequest.builder()
        .customerId(99L)
        .loanAmount(java.math.BigDecimal.ONE)
        .tenorMonth(6)
        .purpose("Test")
        .build();
    when(customerRepository.findById(99L))
        .thenReturn(java.util.Optional.empty());
    org.junit.jupiter.api.Assertions.assertThrows(CustomerNotFoundException.class, () -> loanApplicationService.createLoanApplication(request));
    verify(loanApplicationRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    void throw_not_found_when_loan_application_does_not_exist() {
    Long id = 99L;
    when(loanApplicationRepository.findByIdWithCustomer(id))
        .thenReturn(java.util.Optional.empty());
    assertThrows(
        LoanApplicationNotFoundException.class, () -> loanApplicationService.getLoanById(id));
    }

@Test
void should_get_all_loans_successfully() {
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .nik("123")
        .email("edith@mail.com")
        .phoneNumber("08123")
        .build();
        customer.setId(1L);
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .loanAmount(BigDecimal.valueOf(1000))
        .tenorMonth(6)
        .purpose("Test Loan")
        .status(LoanStatus.SUBMITTED)
        .customer(customer)
        .build();
        loan.setId(1L);
    when(loanApplicationRepository.findAll())
        .thenReturn(List.of(loan));
    var result = loanApplicationService.getAllLoans();
    assertEquals(1, result.size());
    assertEquals("Edith", result.get(0).getCustomer().getFullName());
    assertEquals(1L, result.get(0).getCustomer().getId());
    verify(loanApplicationRepository).findAll();
    }

    @Test
    void should_get_loan_application_by_id_successfully() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .nik("123")
        .email("edith@mail.com")
        .build();
        customer.setId(1L);
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .customer(customer)
        .loanAmount(java.math.BigDecimal.valueOf(1000))
        .tenorMonth(6)
        .purpose("Test")
        .status(com.example.jpabackend.enums.LoanStatus.SUBMITTED)
        .build();
        loan.setId(id);
    when(loanApplicationRepository.findByIdWithCustomer(id))
        .thenReturn(java.util.Optional.of(loan));
    LoanApplicationResponse response = loanApplicationService.getLoanById(id);
    assertNotNull(response);
    assertEquals(id, response.getId());
    assertEquals("Test", response.getPurpose());
    verify(loanApplicationRepository).findByIdWithCustomer(id);
    }

    @Test
    void should_get_loans_by_customer() {
    Long customerId = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("A")
        .nik("1")
        .email("a@mail.com")
        .build();
        customer.setId(customerId);
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .customer(customer)
        .loanAmount(java.math.BigDecimal.TEN)
        .tenorMonth(3)
        .purpose("Test")
        .status(com.example.jpabackend.enums.LoanStatus.SUBMITTED)
        .build();
        loan.setId(10L);
    when(loanApplicationRepository.findLoansByCustomerId(customerId))
        .thenReturn(java.util.List.of(loan));
    var result = loanApplicationService.getLoansByCustomer(customerId);
    assertEquals(1, result.size());
    assertEquals(10L, result.get(0).getId());
    verify(loanApplicationRepository).findLoansByCustomerId(customerId);
    }

    @Test
    void should_get_loans_by_status() {
    CustomerEntity customer = CustomerEntity.builder()
        .build();
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .loanAmount(BigDecimal.TEN)
        .tenorMonth(3)
        .purpose("Test")
        .status(LoanStatus.SUBMITTED)
        .customer(customer)
        .build();
        loan.setId(1L);
    when(loanApplicationRepository.findByStatus(LoanStatus.SUBMITTED))
        .thenReturn(List.of(loan));
    var result = loanApplicationService.getLoansByStatus(LoanStatus.SUBMITTED);
    assertEquals(1, result.size());
    assertEquals("Test", result.get(0).getPurpose());
    }

    @Test
    void should_get_loan_summary_successfully() {
    Object[] row1 = new Object[]{
        LoanStatus.SUBMITTED,
        5L,
        BigDecimal.valueOf(50000)
        };
    Object[] row2 = new Object[]{
        LoanStatus.APPROVED,
        2L,
        BigDecimal.valueOf(20000)
        };
    when(loanApplicationRepository.countLoanByStatus())
        .thenReturn(List.of(row1, row2));
    var result = loanApplicationService.getLoanSummary();
    assertEquals(2, result.size());
    assertEquals(LoanStatus.SUBMITTED, result.get(0).getStatus());
    assertEquals(5L, result.get(0).getTotalLoan());
    assertEquals(BigDecimal.valueOf(50000), result.get(0).getTotalAmount());
    assertEquals(LoanStatus.APPROVED, result.get(1).getStatus());
    assertEquals(2L, result.get(1).getTotalLoan());
    }

    @Test
    void should_get_outstanding_per_customer_successfully() {
    Object[] row1 = new Object[]{
        1L,
        "John Doe",
        BigDecimal.valueOf(100000)
        };
    Object[] row2 = new Object[]{
        2L,
        "Jane Doe",
        BigDecimal.valueOf(200000)
        };
    when(loanApplicationRepository.getOutstandingPerCustomer())
        .thenReturn(List.of(row1, row2));
    var result = loanApplicationService.getOutstandingPerCustomer();
    assertEquals(2, result.size());
    assertEquals(1L, result.get(0).getCustomerId());
    assertEquals("John Doe", result.get(0).getFullName());
    assertEquals(BigDecimal.valueOf(100000), result.get(0).getOutstandingAmount());
    assertEquals(2L, result.get(1).getCustomerId());
    assertEquals("Jane Doe", result.get(1).getFullName());
    assertEquals(BigDecimal.valueOf(200000), result.get(1).getOutstandingAmount());
    }

    @Test
    void should_get_paginated_loans_successfully() {
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .nik("123")
        .email("edith@mail.com")
        .build();
        customer.setId(1L);
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .loanAmount(BigDecimal.valueOf(1000))
        .tenorMonth(6)
        .purpose("Test Loan")
        .status(LoanStatus.SUBMITTED)
        .customer(customer)
        .build();
        loan.setId(1L);
    Page<LoanApplicationEntity> page =
        new org.springframework.data.domain.PageImpl<>(List.of(loan));
    when(loanApplicationRepository.findAll(any(Pageable.class)))
        .thenReturn(page);
    var result = loanApplicationService.getLoans(Pageable.unpaged());
    assertEquals(1, result.getTotalElements());
    assertEquals("Test Loan", result.getContent().get(0).getPurpose());
    assertEquals("Edith", result.getContent().get(0).getCustomer().getFullName());
    }

    @Test
    void should_update_loan_successfully() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .build();
        customer.setId(1L);      
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .loanAmount(BigDecimal.valueOf(1000))
        .tenorMonth(6)
        .purpose("Old")
        .customer(customer)
        .build();
        loan.setId(id);
    CreateLoanApplicationRequest request = CreateLoanApplicationRequest.builder()
        .loanAmount(BigDecimal.valueOf(2000))
        .tenorMonth(12)
        .purpose("New")
        .build();
    when(loanApplicationRepository.findById(id))
        .thenReturn(Optional.of(loan));
    when(loanApplicationRepository.save(any()))
        .thenAnswer(i -> i.getArgument(0));
    var result = loanApplicationService.updateLoan(id, request);
    assertEquals("New", result.getPurpose());
    assertEquals(12, result.getTenorMonth());
    }

    @Test
    void should_update_status_from_approved_to_disbursed_successfully() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .build();
        customer.setId(1L);
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .status(LoanStatus.APPROVED)
        .tenorMonth(6)
        .loanAmount(BigDecimal.valueOf(10000))
        .customer(customer)
        .build();
        loan.setId(id);
    when(loanApplicationRepository.findById(id))
        .thenReturn(Optional.of(loan));
    when(loanApplicationRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    when(repaymentScheduleRepository.findByLoanApplicationId(id))
        .thenReturn(List.of());
    UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
        .status(LoanStatus.DISBURSED)
        .build();
    LoanApplicationResponse response =
        loanApplicationService.updateLoanStatus(id, request);
    assertEquals(LoanStatus.DISBURSED, loan.getStatus());
    assertNotNull(response);
    verify(repaymentScheduleRepository).findByLoanApplicationId(id);
    }

@Test
void should_update_disbursed_to_closed_successfully() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .nik("123")
        .email("edith@mail.com")
        .build();
        customer.setId(1L);
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .status(LoanStatus.DISBURSED)
        .tenorMonth(3)
        .loanAmount(BigDecimal.TEN)
        .customer(customer)
        .build();
        loan.setId(id);
    RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
        .status(RepaymentStatus.PAID)
        .build();
    when(loanApplicationRepository.findById(id))
        .thenReturn(Optional.of(loan));
    when(repaymentScheduleRepository.findByLoanApplicationId(id))
        .thenReturn(List.of(schedule));
    when(loanApplicationRepository.save(any()))
        .thenAnswer(i -> i.getArgument(0));
    UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
        .status(LoanStatus.CLOSED)
        .build();
    var result = loanApplicationService.updateLoanStatus(id, request);
    assertEquals(LoanStatus.CLOSED, loan.getStatus());
    assertNotNull(result);
    verify(repaymentScheduleRepository).findByLoanApplicationId(id);
    }

    @Test
    void should_soft_delete_loan() {
    Long id = 1L;
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .build();
        loan.setId(id);
    when(loanApplicationRepository.findById(id))
        .thenReturn(java.util.Optional.of(loan));
    when(loanApplicationRepository.save(any()))
        .thenAnswer(i -> i.getArgument(0));
    loanApplicationService.deleteLoan(id);
    assertEquals(true, loan.isDeleted());
    verify(loanApplicationRepository).save(loan);
    }

    @Test
    void should_not_generate_schedule_if_already_exists() {
    Long id = 1L;
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .loanAmount(java.math.BigDecimal.valueOf(1200))
        .tenorMonth(12)
        .build();
        loan.setId(id);
    when(repaymentScheduleRepository.findByLoanApplicationId(id))
        .thenReturn(java.util.List.of(
            com.example.jpabackend.entity.RepaymentScheduleEntity.builder().build()
        ));
    org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
        var method = LoanApplicationService.class
            .getDeclaredMethod("generateRepaymentSchedule", LoanApplicationEntity.class);
        method.setAccessible(true);
        method.invoke(loanApplicationService, loan);
        });
    verify(repaymentScheduleRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    void should_generate_repayment_schedule() {
    Long id = 1L;
    when(loanInterestConfig.getAnnualRate()).thenReturn(0.12);
    LoanApplicationEntity loan = LoanApplicationEntity.builder()
        .loanAmount(java.math.BigDecimal.valueOf(1200))
        .tenorMonth(3)
        .build();
        loan.setId(id);
    when(repaymentScheduleRepository.findByLoanApplicationId(id))
        .thenReturn(java.util.List.of());
    when(repaymentScheduleRepository.save(any()))
        .thenAnswer(i -> i.getArgument(0));
    org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
        var method = LoanApplicationService.class
            .getDeclaredMethod("generateRepaymentSchedule", LoanApplicationEntity.class);
        method.setAccessible(true);
        method.invoke(loanApplicationService, loan);
        });
    verify(repaymentScheduleRepository, org.mockito.Mockito.times(3))
        .save(any());
    }
}
