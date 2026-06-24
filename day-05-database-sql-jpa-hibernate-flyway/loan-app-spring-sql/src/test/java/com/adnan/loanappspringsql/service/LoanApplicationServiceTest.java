package com.adnan.loanappspringsql.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.adnan.loanappspringsql.dto.CreateLoanApplicationRequest;
import com.adnan.loanappspringsql.dto.LoanApplicationResponse;
import com.adnan.loanappspringsql.dto.LoanStatusSummaryResponse;
import com.adnan.loanappspringsql.dto.OutstandingAmountResponse;
import com.adnan.loanappspringsql.dto.UpdateLoanStatusRequest;
import com.adnan.loanappspringsql.dto.pagination.PageResponse;
import com.adnan.loanappspringsql.enums.LoanStatusEnum;
import com.adnan.loanappspringsql.enums.RepaymentStatusEnum;
import com.adnan.loanappspringsql.exception.BadRequestException;
import com.adnan.loanappspringsql.exception.NotFoundException;
import com.adnan.loanappspringsql.model.Customer;
import com.adnan.loanappspringsql.model.LoanApplication;
import com.adnan.loanappspringsql.model.RepaymentSchedule;
import com.adnan.loanappspringsql.repository.CustomerRepository;
import com.adnan.loanappspringsql.repository.LoanApplicationRepository;
import com.adnan.loanappspringsql.repository.RepaymentScheduleRepository;
import com.adnan.loanappspringsql.service.impl.LoanApplicationServiceImpl;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {
    @Mock
    private LoanApplicationRepository loanApplicationRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @InjectMocks
    private LoanApplicationServiceImpl loanApplicationService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(
                loanApplicationService,
                "annualInterestRate",
                new BigDecimal("0.12"));
    }

    @Test
    void create_shouldCreateLoanApplicationSuccessfully() {
        // Given
        CreateLoanApplicationRequest request = CreateLoanApplicationRequest.builder()
                .customerId(1L)
                .loanAmount(BigDecimal.valueOf(10_000_000))
                .purpose("Motor Baru")
                .tenorMonth(12)
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .build();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(loanApplicationRepository.save(any(LoanApplication.class)))
                .thenAnswer(invocation -> {
                    LoanApplication loan = invocation.getArgument(0);
                    loan.setId(1L);
                    return loan;
                });

        // When
        LoanApplicationResponse response = loanApplicationService.create(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(LoanStatusEnum.SUBMITTED, response.getStatus());
        assertEquals(request.getLoanAmount(), response.getLoanAmount());

        verify(customerRepository).findById(1L);
        verify(loanApplicationRepository).save(any(LoanApplication.class));
    }

    @Test
    void create_shouldThrowNotFoundException_whenCustomerNotFound() {
        // Given
        CreateLoanApplicationRequest request = CreateLoanApplicationRequest.builder()
                .customerId(99L)
                .loanAmount(BigDecimal.valueOf(10_000_000))
                .purpose("Motor")
                .tenorMonth(12)
                .build();
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> loanApplicationService.create(request));
        assertEquals("Customer not found with id: 99", exception.getMessage());

        verify(customerRepository).findById(99L);
        verify(loanApplicationRepository, never()).save(any());
    }

    @Test
    void findById_shouldReturnLoanApplicationSuccessfully() {
        // Given
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(20_000_000))
                .purpose("Motor Baru")
                .tenorMonth(12)
                .status(LoanStatusEnum.SUBMITTED)
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        // When
        LoanApplicationResponse response = loanApplicationService.findById(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(LoanStatusEnum.SUBMITTED, response.getStatus());
        assertEquals(BigDecimal.valueOf(20_000_000), response.getLoanAmount());
        assertEquals("Motor Baru", response.getPurpose());

        verify(loanApplicationRepository).findByIdWithCustomer(1L);
    }

    @Test
    void findById_shouldThrowNotFoundException_whenLoanApplicationNotFound() {
        // Given
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> loanApplicationService.findById(1L));

        assertEquals("Loan application not found with id: 1", exception.getMessage());

        verify(loanApplicationRepository).findByIdWithCustomer(1L);
    }

    @Test
    void findAll_shouldReturnPagedLoanApplicationsSuccessfully() {
        // Given
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(20_000_000))
                .purpose("Motor Baru")
                .tenorMonth(12)
                .status(LoanStatusEnum.SUBMITTED)
                .build();

        Page<LoanApplication> page = new PageImpl<>(
                List.of(loan),
                PageRequest.of(0, 10),
                1);

        when(loanApplicationRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(page);

        // When
        PageResponse<LoanApplicationResponse> response = loanApplicationService.findAll(
                null,
                null,
                null,
                0,
                10);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertEquals(0, response.getPage());
        assertEquals(10, response.getSize());

        LoanApplicationResponse loanResponse = response.getContent().get(0);

        assertEquals(1L, loanResponse.getId());
        assertEquals(LoanStatusEnum.SUBMITTED, loanResponse.getStatus());
        assertEquals(BigDecimal.valueOf(20_000_000),
                loanResponse.getLoanAmount());

        verify(loanApplicationRepository)
                .findAll(PageRequest.of(0, 10));
    }

    @Test
    void findAll_shouldReturnLoanApplicationsByStatusSuccessfully() {
        // Given
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .build();

        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(20_000_000))
                .purpose("Motor Baru")
                .tenorMonth(12)
                .status(LoanStatusEnum.APPROVED)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        Page<LoanApplication> loanPage = new PageImpl<>(
                List.of(loan),
                pageable,
                1);

        when(loanApplicationRepository.findByStatus(LoanStatusEnum.APPROVED, pageable)).thenReturn(loanPage);

        // When
        PageResponse<LoanApplicationResponse> response = loanApplicationService.findAll(
                LoanStatusEnum.APPROVED,
                null,
                null,
                0,
                10);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(LoanStatusEnum.APPROVED,
                response.getContent().get(0).getStatus());

        verify(loanApplicationRepository).findByStatus(LoanStatusEnum.APPROVED, pageable);
    }

    @Test
    void findAll_shouldReturnLoanApplicationsByCreatedDateSuccessfully() {
        // Given
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .build();

        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(20_000_000))
                .purpose("Motor Baru")
                .tenorMonth(12)
                .status(LoanStatusEnum.SUBMITTED)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        Page<LoanApplication> loanPage = new PageImpl<>(
                List.of(loan),
                pageable,
                1);
        ZonedDateTime start = startDate.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime end = endDate.atTime(LocalTime.MAX)
                .atZone(ZoneId.systemDefault());

        when(loanApplicationRepository.findByCreatedAtBetween(start, end, pageable)).thenReturn(loanPage);

        // When
        PageResponse<LoanApplicationResponse> response = loanApplicationService.findAll(
                null,
                startDate,
                endDate,
                0,
                10);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getContent().size());

        verify(loanApplicationRepository).findByCreatedAtBetween(start, end, pageable);
    }

    @Test
    void findAll_shouldReturnLoanApplicationsByStatusAndCreatedDateSuccessfully() {
        // Given
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .build();

        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(20_000_000))
                .purpose("Motor Baru")
                .tenorMonth(12)
                .status(LoanStatusEnum.APPROVED)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        Page<LoanApplication> loanPage = new PageImpl<>(
                List.of(loan),
                pageable,
                1);

        ZonedDateTime start = startDate.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime end = endDate.atTime(LocalTime.MAX)
                .atZone(ZoneId.systemDefault());

        when(loanApplicationRepository.findByStatusAndCreatedAtBetween(LoanStatusEnum.APPROVED, start, end, pageable))
                .thenReturn(loanPage);

        // When
        PageResponse<LoanApplicationResponse> response = loanApplicationService.findAll(
                LoanStatusEnum.APPROVED,
                startDate,
                endDate,
                0,
                10);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(LoanStatusEnum.APPROVED, response.getContent().get(0).getStatus());

        verify(loanApplicationRepository).findByStatusAndCreatedAtBetween(LoanStatusEnum.APPROVED, start, end,
                pageable);
    }

    @Test
    void findAll_shouldIgnoreIncompleteDateRange() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<LoanApplication> page = new PageImpl<>(
                Collections.emptyList(),
                pageable,
                0);
        when(loanApplicationRepository.findByStatus(LoanStatusEnum.APPROVED, pageable)).thenReturn(page);

        // When
        PageResponse<LoanApplicationResponse> response = loanApplicationService.findAll(
                LoanStatusEnum.APPROVED,
                LocalDate.now(),
                null,
                0,
                10);

        // Then
        assertNotNull(response);

        verify(loanApplicationRepository).findByStatus(LoanStatusEnum.APPROVED, pageable);
    }

    @Test
    void findAll_shouldReturnAll_whenOnlyStartDateProvided() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<LoanApplication> page = new PageImpl<>(
                Collections.emptyList(),
                pageable,
                0);
        when(loanApplicationRepository.findAll(pageable))
                .thenReturn(page);

        // When
        PageResponse<LoanApplicationResponse> response = loanApplicationService.findAll(
                null,
                LocalDate.now(),
                null,
                0,
                10);

        // Then
        assertNotNull(response);

        verify(loanApplicationRepository).findAll(pageable);
    }

    @Test
    void findAll_shouldReturnAll_whenOnlyEndDateProvided() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<LoanApplication> page = new PageImpl<>(
                Collections.emptyList(),
                pageable,
                0);
        when(loanApplicationRepository.findAll(pageable)).thenReturn(page);

        // When
        PageResponse<LoanApplicationResponse> response = loanApplicationService.findAll(
                null,
                null,
                LocalDate.now(),
                0,
                10);

        // Then
        assertNotNull(response);

        verify(loanApplicationRepository).findAll(pageable);
    }

    @Test
    void findByCustomerId_shouldReturnLoanApplicationsSuccessfully() {
        // Given
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .email("budi@mail.com")
                .nik("3173010101900001")
                .build();
        LoanApplication loan1 = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(20_000_000))
                .purpose("Motor")
                .tenorMonth(12)
                .status(LoanStatusEnum.SUBMITTED)
                .build();
        LoanApplication loan2 = LoanApplication.builder()
                .id(2L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(30_000_000))
                .purpose("Mobil")
                .tenorMonth(24)
                .status(LoanStatusEnum.APPROVED)
                .build();
        when(loanApplicationRepository.findByCustomerId(1L)).thenReturn(List.of(loan1, loan2));

        // When
        List<LoanApplicationResponse> responses = loanApplicationService.findByCustomerId(1L);

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals(LoanStatusEnum.SUBMITTED, responses.get(0).getStatus());
        assertEquals(2L, responses.get(1).getId());
        assertEquals(LoanStatusEnum.APPROVED, responses.get(1).getStatus());

        verify(loanApplicationRepository).findByCustomerId(1L);
    }

    @Test
    void findByCustomerId_shouldReturnEmptyListWhenCustomerHasNoLoanApplications() {
        // Given
        when(loanApplicationRepository.findByCustomerId(1L)).thenReturn(Collections.emptyList());

        // When
        List<LoanApplicationResponse> responses = loanApplicationService.findByCustomerId(1L);

        // Then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());

        verify(loanApplicationRepository).findByCustomerId(1L);
    }

    @Test
    void getLoanSummaryByStatus_shouldReturnSummarySuccessfully() {
        // Given
        List<Object[]> rows = List.of(
                new Object[] { LoanStatusEnum.SUBMITTED, 3L },
                new Object[] { LoanStatusEnum.APPROVED, 2L },
                new Object[] { LoanStatusEnum.REJECTED, 1L });
        when(loanApplicationRepository.countLoanByStatus()).thenReturn(rows);

        // When
        List<LoanStatusSummaryResponse> responses = loanApplicationService.getLoanSummaryByStatus();

        // Then
        assertNotNull(responses);
        assertEquals(3, responses.size());
        assertEquals(LoanStatusEnum.SUBMITTED, responses.get(0).getStatus());
        assertEquals(3L, responses.get(0).getTotal());
        assertEquals(LoanStatusEnum.APPROVED, responses.get(1).getStatus());
        assertEquals(2L, responses.get(1).getTotal());

        verify(loanApplicationRepository).countLoanByStatus();
    }

    @Test
    void getLoanSummaryByStatus_shouldReturnEmptyList() {
        // Given
        when(loanApplicationRepository.countLoanByStatus()).thenReturn(Collections.emptyList());

        // When
        List<LoanStatusSummaryResponse> responses = loanApplicationService.getLoanSummaryByStatus();

        // Then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());

        verify(loanApplicationRepository).countLoanByStatus();
    }

    @Test
    void getOutstandingAmountCustomer_shouldReturnOutstandingAmountSuccessfully() {
        // Given
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(loanApplicationRepository.getTotalRepayment(1L)).thenReturn(BigDecimal.valueOf(25_000_000));
        when(loanApplicationRepository.getTotalPaid(1L)).thenReturn(BigDecimal.valueOf(10_000_000));

        // When
        OutstandingAmountResponse response = loanApplicationService.getOutstandingAmountCustomer(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
        assertEquals(BigDecimal.valueOf(15_000_000), response.getOutstandingAmount());

        verify(customerRepository).findById(1L);
        verify(loanApplicationRepository).getTotalRepayment(1L);
        verify(loanApplicationRepository).getTotalPaid(1L);
    }

    @Test
    void getOutstandingAmountCustomer_shouldThrowNotFoundException() {
        // Given
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> loanApplicationService.getOutstandingAmountCustomer(1L));

        assertEquals("Customer not found", exception.getMessage());

        verify(customerRepository).findById(1L);
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void getOutstandingAmountCustomer_shouldReturnZeroOutstanding() {
        // Given
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .build();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(loanApplicationRepository.getTotalRepayment(1L)).thenReturn(BigDecimal.valueOf(10_000_000));
        when(loanApplicationRepository.getTotalPaid(1L)).thenReturn(BigDecimal.valueOf(10_000_000));

        // When
        OutstandingAmountResponse response = loanApplicationService.getOutstandingAmountCustomer(1L);

        // Then
        assertEquals(BigDecimal.ZERO, response.getOutstandingAmount());

        verify(customerRepository).findById(1L);
    }

    @Test
    void updateStatus_shouldApproveLoanSuccessfully() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.APPROVED)
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(24_000_000))
                .tenorMonth(12)
                .purpose("Motor")
                .status(LoanStatusEnum.SUBMITTED)
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        // When
        LoanApplicationResponse response = loanApplicationService.updateStatus(1L, request);

        // Then
        assertNotNull(response);
        assertEquals(LoanStatusEnum.APPROVED, response.getStatus());

        verify(loanApplicationRepository).save(any());
        verify(repaymentScheduleRepository, never()).saveAll(anyList());
    }

    @Test
    void updateStatus_shouldRejectLoanSuccessfully() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.REJECTED)
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .status(LoanStatusEnum.SUBMITTED)
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        // When
        LoanApplicationResponse response = loanApplicationService.updateStatus(1L, request);

        // Then
        assertEquals(LoanStatusEnum.REJECTED, response.getStatus());

        verify(loanApplicationRepository).save(any());
        verify(repaymentScheduleRepository, never()).saveAll(anyList());
    }

    @Test
    void updateStatus_shouldDisburseLoanSuccessfully() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.DISBURSED)
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(24_000_000))
                .tenorMonth(12)
                .purpose("Motor")
                .status(LoanStatusEnum.APPROVED)
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(repaymentScheduleRepository.findByLoanApplicationId(1L)).thenReturn(Collections.emptyList());
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        // When
        LoanApplicationResponse response = loanApplicationService.updateStatus(1L, request);

        // Then
        assertNotNull(response);
        assertEquals(LoanStatusEnum.DISBURSED, response.getStatus());

        verify(repaymentScheduleRepository).saveAll(anyList());
        verify(loanApplicationRepository).save(any());
    }

    @Test
    void updateStatus_shouldCloseLoanSuccessfully() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.CLOSED)
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .status(LoanStatusEnum.DISBURSED)
                .customer(Customer.builder().id(1L).build())
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(repaymentScheduleRepository.existsByLoanApplicationIdAndStatus(1L, RepaymentStatusEnum.UNPAID))
                .thenReturn(false);
        when(loanApplicationRepository.save(any()))
                .thenReturn(loan);

        // When
        LoanApplicationResponse response = loanApplicationService.updateStatus(1L, request);

        // Then
        assertEquals(LoanStatusEnum.CLOSED, response.getStatus());

        verify(loanApplicationRepository).save(any());
    }

    @Test
    void updateStatus_shouldThrowBadRequest_whenLoanHasUnpaidInstallment() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.CLOSED)
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .status(LoanStatusEnum.DISBURSED)
                .customer(Customer.builder().id(1L).build())
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(repaymentScheduleRepository.existsByLoanApplicationIdAndStatus(1L, RepaymentStatusEnum.UNPAID))
                .thenReturn(true);

        // When & Then
        assertThrows(BadRequestException.class, () -> loanApplicationService.updateStatus(1L, request));

        verify(loanApplicationRepository, never()).save(any());
    }

    @Test
    void updateStatus_shouldThrowNotFoundException_whenLoanNotFound() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.APPROVED)
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> loanApplicationService.updateStatus(1L, request));

        verify(loanApplicationRepository).findByIdWithCustomer(1L);
    }

    @Test
    void updateStatus_shouldThrowBadRequest_whenStatusTransitionInvalid() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.APPROVED)
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .status(LoanStatusEnum.REJECTED)
                .customer(Customer.builder().id(1L).build())
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        // When & Then
        assertThrows(BadRequestException.class, () -> loanApplicationService.updateStatus(1L, request));

        verify(loanApplicationRepository, never()).save(any());
    }

    @Test
    void updateStatus_shouldThrowBadRequest_whenRepaymentScheduleAlreadyGenerated() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.DISBURSED)
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Budi")
                .nik("3173010101900001")
                .email("budi@mail.com")
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(24_000_000))
                .tenorMonth(12)
                .purpose("Motor")
                .status(LoanStatusEnum.APPROVED)
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(repaymentScheduleRepository.findByLoanApplicationId(1L))
                .thenReturn(List.of(RepaymentSchedule.builder().build()));

        // When & Then
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> loanApplicationService.updateStatus(1L, request));

        assertEquals("Repayment schedule has already been generated", exception.getMessage());
        verify(repaymentScheduleRepository, never()).saveAll(anyList());
    }

    @Test
    void updateStatus_shouldThrowBadRequest_whenCurrentStatusIsClosed() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.APPROVED)
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .status(LoanStatusEnum.CLOSED)
                .customer(Customer.builder().id(1L).build())
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        // When & Then
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> loanApplicationService.updateStatus(1L, request));

        assertEquals("Status cannot be changed", exception.getMessage());

        verify(loanApplicationRepository, never()).save(any());
    }

    @Test
    void updateStatus_shouldThrowBadRequest_whenApprovedToRejected() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.REJECTED)
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .status(LoanStatusEnum.APPROVED)
                .customer(Customer.builder().id(1L).build())
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        // When & Then
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> loanApplicationService.updateStatus(1L, request));

        assertEquals("Invalid loan status transition", exception.getMessage());

        verify(loanApplicationRepository, never()).save(any());
    }

    @Test
    void updateStatus_shouldThrowBadRequest_whenDisbursedToApproved() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.APPROVED)
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .status(LoanStatusEnum.DISBURSED)
                .customer(Customer.builder().id(1L).build())
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        // When & Then
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> loanApplicationService.updateStatus(1L, request));

        assertEquals("Invalid loan status transition", exception.getMessage());

        verify(loanApplicationRepository, never()).save(any());
    }

    @Test
    void updateStatus_shouldThrowBadRequest_whenSubmittedToClosed() {
        // Given
        UpdateLoanStatusRequest request = UpdateLoanStatusRequest.builder()
                .status(LoanStatusEnum.CLOSED)
                .build();
        LoanApplication loan = LoanApplication.builder()
                .id(1L)
                .status(LoanStatusEnum.SUBMITTED)
                .customer(Customer.builder().id(1L).build())
                .build();
        when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        // When & Then
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> loanApplicationService.updateStatus(1L, request));

        assertEquals("Invalid loan status transition", exception.getMessage());

        verify(loanApplicationRepository, never())
                .save(any());
    }
}