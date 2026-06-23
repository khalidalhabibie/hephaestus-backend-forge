package com.example.training_2.service;

import com.example.training_2.dto.CreateLoanApplicationRequest;
import com.example.training_2.dto.LoanApplicationResponse;
import com.example.training_2.dto.RepaymentScheduleResponse;
import com.example.training_2.dto.UpdateLoanStatusRequest;
import com.example.training_2.entity.Customer;
import com.example.training_2.entity.LoanApplication;
import com.example.training_2.entity.LoanApplicationStatus;
import com.example.training_2.entity.RepaymentSchedule;
import com.example.training_2.exception.NotFoundException;
import com.example.training_2.repository.CustomerRepository;
import com.example.training_2.repository.LoanApplicationRepository;
import com.example.training_2.repository.RepaymentScheduleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @Mock
    private RepaymentScheduleService repaymentScheduleService;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    private Customer customer;
    private LoanApplication loanApplication;

    @BeforeEach
    void setUp() {

        customer = new Customer();
        customer.setId(1L);
        customer.setFullName("Tony Stark");
        customer.setNik("123456789");
        customer.setEmail("tony@stark.com");

        loanApplication = new LoanApplication();
        loanApplication.setId(1L);
        loanApplication.setCustomer(customer);
        loanApplication.setLoanAmount(BigDecimal.valueOf(10000000));
        loanApplication.setTenorMonth(12);
        loanApplication.setPurpose("Business");
        loanApplication.setStatus(LoanApplicationStatus.SUBMITTED);
    }

    @Test
    void testCreate() {

        CreateLoanApplicationRequest request = new CreateLoanApplicationRequest();

        request.setCustomerId(1L);
        request.setLoanAmount(BigDecimal.valueOf(10000000));
        request.setTenorMonth(12);
        request.setPurpose("Business");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(loanApplicationRepository.save(any(LoanApplication.class)))
                .thenReturn(loanApplication);

        LoanApplicationResponse response = loanApplicationService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(
                LoanApplicationStatus.SUBMITTED,
                response.getStatus());

        verify(customerRepository).findById(1L);
        verify(loanApplicationRepository).save(any(LoanApplication.class));
    }

    @Test
    void testCreate_ShouldThrowException_WhenCustomerNotFound() {

        CreateLoanApplicationRequest request = new CreateLoanApplicationRequest();

        request.setCustomerId(99L);

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> loanApplicationService.create(request));

        verify(loanApplicationRepository, never())
                .save(any());
    }

    @Test
    void testGetAll() {

        when(loanApplicationRepository.findAll())
                .thenReturn(List.of(loanApplication));

        List<LoanApplicationResponse> responses = loanApplicationService.getAll(null);

        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getId());
    }

    @Test
    void testGetAllByStatus() {

        when(loanApplicationRepository.findByStatus(
                LoanApplicationStatus.SUBMITTED))
                .thenReturn(List.of(loanApplication));

        List<LoanApplicationResponse> responses = loanApplicationService.getAll(
                LoanApplicationStatus.SUBMITTED);

        assertEquals(1, responses.size());

        verify(loanApplicationRepository)
                .findByStatus(LoanApplicationStatus.SUBMITTED);
    }

    @Test
    void testGetById() {

        when(loanApplicationRepository.findById(1L))
                .thenReturn(Optional.of(loanApplication));

        LoanApplicationResponse response = loanApplicationService.getById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetById_ShouldThrowException_WhenNotFound() {

        when(loanApplicationRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loanApplicationService.getById(1L));

        assertEquals(
                "Loan Application Not Found",
                exception.getMessage());
    }

    @Test
    void testGetRepaymentSchedulesByLoanAppsId() {

        RepaymentSchedule schedule = new RepaymentSchedule();

        schedule.setId(1L);
        schedule.setLoanApplication(loanApplication);
        schedule.setInstallmentNumber(1);
        schedule.setPrincipalAmount(BigDecimal.valueOf(800000));
        schedule.setInterestAmount(BigDecimal.valueOf(100000));
        schedule.setTotalAmount(BigDecimal.valueOf(900000));
        schedule.setDueDate(LocalDate.now());

        when(loanApplicationRepository.findById(1L))
                .thenReturn(Optional.of(loanApplication));

        when(repaymentScheduleRepository.findByLoanApplicationId(1L))
                .thenReturn(List.of(schedule));

        List<RepaymentScheduleResponse> responses = loanApplicationService
                .getRepaymentSchedulesByLoanAppsId(1L);

        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getLoanApplicationId());
    }

    @Test
    void testGetRepaymentSchedulesByLoanAppsId_ShouldThrowException_WhenLoanNotFound() {

        when(loanApplicationRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loanApplicationService
                        .getRepaymentSchedulesByLoanAppsId(1L));

        assertEquals(
                "Loan Application not found",
                exception.getMessage());
    }

    @Test
    void testPatchStatus() {

        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();

        request.setStatus(LoanApplicationStatus.APPROVED);

        when(loanApplicationRepository.findById(1L))
                .thenReturn(Optional.of(loanApplication));

        LoanApplication approvedLoan = loanApplication;
        approvedLoan.setStatus(LoanApplicationStatus.APPROVED);

        when(loanApplicationRepository.save(any()))
                .thenReturn(approvedLoan);

        LoanApplicationResponse response = loanApplicationService.patchStatus(1L, request);

        assertEquals(
                LoanApplicationStatus.APPROVED,
                response.getStatus());

        verify(repaymentScheduleService)
                .generateSchedules(any(LoanApplication.class));
    }

    @Test
    void testPatchStatus_ShouldThrowException_WhenLoanNotFound() {

        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();

        request.setStatus(LoanApplicationStatus.APPROVED);

        when(loanApplicationRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loanApplicationService.patchStatus(1L, request));

        assertEquals(
                "Loan Application not found with id: 1",
                exception.getMessage());

        verify(loanApplicationRepository, never())
                .save(any());
    }

    @Test
    void testPatchStatus_ShouldNotGenerateSchedule_WhenStatusNotApproved() {

        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();

        request.setStatus(LoanApplicationStatus.REJECTED);

        when(loanApplicationRepository.findById(1L))
                .thenReturn(Optional.of(loanApplication));

        loanApplication.setStatus(LoanApplicationStatus.REJECTED);

        when(loanApplicationRepository.save(any()))
                .thenReturn(loanApplication);

        loanApplicationService.patchStatus(1L, request);

        verify(repaymentScheduleService, never())
                .generateSchedules(any());
    }
}