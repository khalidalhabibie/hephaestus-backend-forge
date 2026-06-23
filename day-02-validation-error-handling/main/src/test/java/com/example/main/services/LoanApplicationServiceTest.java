package com.example.main.services;

import com.example.main.dto.request.LoanApplicationRequest;
import com.example.main.dto.response.LoanApplicationResponse;
import com.example.main.dto.response.RepaymentScheduleResponse;
import com.example.main.entity.CustomerEntity;
import com.example.main.entity.LoanApplicationEntity;
import com.example.main.entity.RepaymentScheduleEntity;
import com.example.main.enums.LoanStatus;
import com.example.main.enums.ScheduleStatus;
import com.example.main.exceptions.BadRequestException;
import com.example.main.exceptions.ForbiddenException;
import com.example.main.exceptions.LoanApplicationNotFoundException;
import com.example.main.exceptions.NotFoundException;
import com.example.main.repositories.CustomerRepository;
import com.example.main.repositories.LoanApplicationRepository;
import com.example.main.repositories.RepaymentScheduleRepository;
import com.example.main.security.UserRole;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    private LoanApplicationEntity loan1;
    private LoanApplicationEntity loan2;
    private final String CORRELATION_ID = "REQ-LOAN-2026";

    @BeforeEach
    void setUp() {
        MDC.put("correlation_id", CORRELATION_ID);

        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        loan1 = new LoanApplicationEntity();
        loan1.setId(101L);
        loan1.setCustomer(customer);
        loan1.setLoanAmount(BigDecimal.valueOf(5000000));
        loan1.setStatus(LoanStatus.APPROVED);

        loan2 = new LoanApplicationEntity();
        loan2.setId(102L);
        loan2.setCustomer(customer);
        loan2.setLoanAmount(BigDecimal.valueOf(10000000));
        loan2.setStatus(LoanStatus.REJECTED);
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void should_throw_loan_application_not_found_exception_when_approving_non_existent_loan() {
        // given
        Long nonExistentId = 999L;
        UserRole approverRole = UserRole.MANAGER;

        when(loanApplicationRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        LoanApplicationNotFoundException exception = assertThrows(
                LoanApplicationNotFoundException.class, 
                () -> loanApplicationService.approveLoanApplication(nonExistentId, approverRole)
        );

        assertEquals("Loan application not found", exception.getMessage());
        verify(loanApplicationRepository, times(1)).findById(nonExistentId);
        
        verify(loanApplicationRepository, never()).save(any(LoanApplicationEntity.class));
        verifyNoInteractions(repaymentScheduleService); 
    }

    @Test
    void should_throw_loan_application_not_found_exception_when_loan_id_does_not_exist() {
        // given
        Long nonExistentId = 999L;
        String requestedStatus = "APPROVED";

        when(loanApplicationRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        LoanApplicationNotFoundException exception = assertThrows(
                LoanApplicationNotFoundException.class, 
                () -> loanApplicationService.updateLoanStatus(nonExistentId, requestedStatus)
        );

        assertEquals("Loan application not found", exception.getMessage());
        verify(loanApplicationRepository, times(1)).findById(nonExistentId);
        verify(loanApplicationRepository, never()).save(any(LoanApplicationEntity.class));
    }

    @Test
    void should_throw_bad_request_exception_when_status_string_is_invalid_enum() {
        // given
        Long loanId = 101L;
        String invalidStatusStr = "INVALID_STATUS_XYZ";

        LoanApplicationEntity existingLoan = new LoanApplicationEntity();
        existingLoan.setId(loanId);
        existingLoan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.of(existingLoan));

        // when & then
        BadRequestException exception = assertThrows(
                BadRequestException.class, 
                () -> loanApplicationService.updateLoanStatus(loanId, invalidStatusStr)
        );

        assertEquals("Invalid loan status value: " + invalidStatusStr, exception.getMessage());
        verify(loanApplicationRepository, times(1)).findById(loanId);
        verify(loanApplicationRepository, never()).save(any(LoanApplicationEntity.class));
    }

    @Test
    void should_return_repayment_schedules_with_correct_mapping() {
        Long loanId = 101L;
        
        RepaymentScheduleEntity scheduleEntity = new RepaymentScheduleEntity();
        scheduleEntity.setId(501L);
        scheduleEntity.setInstallmentNumber(1);
        scheduleEntity.setPrincipalAmount(BigDecimal.valueOf(1000000));
        scheduleEntity.setInterestAmount(BigDecimal.valueOf(10000));
        scheduleEntity.setTotalAmount(BigDecimal.valueOf(1010000));
        scheduleEntity.setStatus(ScheduleStatus.UNPAID);

        when(loanApplicationRepository.existsById(loanId)).thenReturn(true);
        when(repaymentScheduleRepository.findByLoanApplicationId(loanId))
                .thenReturn(List.of(scheduleEntity));

        List<RepaymentScheduleResponse> responses = loanApplicationService.getRepaymentSchedulesByLoanId(loanId);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        
        RepaymentScheduleResponse mappedResponse = responses.get(0);
        assertEquals(501L, mappedResponse.getId());
        assertEquals(1, mappedResponse.getInstallmentNumber());
        assertEquals(BigDecimal.valueOf(1000000), mappedResponse.getPrincipalAmount());
        assertEquals(BigDecimal.valueOf(10000), mappedResponse.getInterestAmount());
        assertEquals(BigDecimal.valueOf(1010000), mappedResponse.getTotalAmount());
    }


    @Test
    void should_throw_exception_when_cancelling_non_existent_loan() {
        // given
        Long id = 999L;
        when(loanApplicationRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(LoanApplicationNotFoundException.class, 
                () -> loanApplicationService.cancelLoanApplication(id));
                
        verify(loanApplicationRepository, times(1)).findById(id);
        verify(loanApplicationRepository, never()).save(any());
    }


    @Test
    void should_get_loans_by_customer_id_successfully() {
        // given
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(loanApplicationRepository.findLoansByCustomerId(customerId)).thenReturn(List.of(loan1));

        // when
        List<LoanApplicationResponse> responses = loanApplicationService.getLoansByCustomerId(customerId);

        // then
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        verify(customerRepository, times(1)).existsById(customerId);
        verify(loanApplicationRepository, times(1)).findLoansByCustomerId(customerId);
    }

    @Test
    void should_throw_exception_when_customer_not_found_for_loans() {
        // given
        Long customerId = 99L;
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // when & then
        assertThrows(NotFoundException.class, 
                () -> loanApplicationService.getLoansByCustomerId(customerId));

        verify(customerRepository, times(1)).existsById(customerId);
        verify(loanApplicationRepository, never()).findLoansByCustomerId(anyLong());
    }


    @Test
    void should_get_loans_by_valid_status_enum() {
        String statusStr = "approved "; 
        when(loanApplicationRepository.findByStatus(LoanStatus.APPROVED)).thenReturn(List.of(loan1));

        // when
        List<LoanApplicationResponse> responses = loanApplicationService.getLoansByStatus(statusStr);

        // then
        assertFalse(responses.isEmpty());
        verify(loanApplicationRepository, times(1)).findByStatus(LoanStatus.APPROVED);
    }

    @Test
    void should_throw_bad_request_when_status_enum_invalid() {
        // given
        String invalidStatus = "CORRUPT_STATUS";

        // when & then
        assertThrows(BadRequestException.class, 
                () -> loanApplicationService.getLoansByStatus(invalidStatus));
        
        verify(loanApplicationRepository, never()).findByStatus(any());
    }



    @Test
    void should_throw_bad_request_when_updating_from_terminal_status() {
        // given
        Long id = 101L;
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setStatus(LoanStatus.REJECTED); 

        when(loanApplicationRepository.findById(id)).thenReturn(Optional.of(loan));

        // when & then
        BadRequestException exception = assertThrows(BadRequestException.class, 
                () -> loanApplicationService.updateLoanStatus(id, "APPROVED"));

        assertTrue(exception.getMessage().contains("Cannot change status"));
        verify(loanApplicationRepository, never()).save(any());
    }


    @Test
    void should_return_repayment_schedules_when_loan_exists() {
        // given
        Long loanId = 101L;
        when(loanApplicationRepository.existsById(loanId)).thenReturn(true);
        when(repaymentScheduleRepository.findByLoanApplicationId(loanId)).thenReturn(List.of());

        // when
        List<RepaymentScheduleResponse> responses = loanApplicationService.getRepaymentSchedulesByLoanId(loanId);

        // then
        assertNotNull(responses);
        verify(loanApplicationRepository, times(1)).existsById(loanId);
        verify(repaymentScheduleRepository, times(1)).findByLoanApplicationId(loanId);
    }


    @Test
    void should_return_loans_filtered_by_customer_id_only_successfully() {
        // given
        String statusParam = null;
        Long customerIdParam = 1L;

        when(loanApplicationRepository.findLoansByCustomerId(customerIdParam))
                .thenReturn(Arrays.asList(loan1, loan2));

        // when
        List<LoanApplicationResponse> responses = loanApplicationService.getAllLoanApplications(statusParam, customerIdParam);

        // then
        assertNotNull(responses);
        assertEquals(2, responses.size());

        verify(loanApplicationRepository, times(1)).findLoansByCustomerId(customerIdParam);
        verify(loanApplicationRepository, never()).findByStatus(any());
        verify(loanApplicationRepository, never()).findAll();
    }

    @Test
    void should_return_all_loan_applications_when_no_parameters_provided() {
        // given
        String statusParam = null;
        Long customerIdParam = null;

        when(loanApplicationRepository.findAll()).thenReturn(Arrays.asList(loan1, loan2));

        // when
        List<LoanApplicationResponse> responses = loanApplicationService.getAllLoanApplications(statusParam, customerIdParam);

        // then
        assertNotNull(responses);
        assertEquals(2, responses.size());

        verify(loanApplicationRepository, times(1)).findAll();
        verify(loanApplicationRepository, never()).findLoansByCustomerId(anyLong());
        verify(loanApplicationRepository, never()).findByStatus(any());
    }

    // ! SHOULD CREATE LOAN APPLICATION SUCCESSFULLY
    @Test
    void should_create_loan_application_successfully() {
        // given
        LoanApplicationRequest request = new LoanApplicationRequest(1L, new BigDecimal("5000000"), 12, "Business Expansion");
        
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        LoanApplicationEntity savedLoan = new LoanApplicationEntity();
        savedLoan.setId(100L);
        savedLoan.setCustomer(customer);
        savedLoan.setLoanAmount(request.getLoanAmount());
        savedLoan.setTenorMonth(request.getTenorMonth());
        savedLoan.setPurpose(request.getPurpose());
        savedLoan.setStatus(LoanStatus.SUBMITTED);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(loanApplicationRepository.save(any(LoanApplicationEntity.class))).thenReturn(savedLoan);

        // when
        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);

        // then
        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(LoanStatus.SUBMITTED, response.getStatus());
        verify(loanApplicationRepository, times(1)).save(any(LoanApplicationEntity.class));
    }

    // ! SHOULD THROW NOT FOUND WHEN CUSTOMER DOES NOT EXIST
    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
        // given
        LoanApplicationRequest request = new LoanApplicationRequest(99L, new BigDecimal("5000000"), 12, "Renovation");
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> loanApplicationService.createLoanApplication(request));

        assertEquals("Customer Not Found", exception.getMessage());
        verify(loanApplicationRepository, never()).save(any(LoanApplicationEntity.class));
    }

    // ! SHOULD GET LOAN APPLICATION BY ID SUCCESSFULLY
    @Test
    void should_get_loan_application_by_id_successfully() {
        // given
        Long loanId = 100L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer);
        loan.setLoanAmount(new BigDecimal("2000000"));
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // when
        LoanApplicationResponse response = loanApplicationService.getLoanApplicationById(loanId);

        // then
        assertNotNull(response);
        assertEquals(loanId, response.getId());
        verify(loanApplicationRepository, times(1)).findById(loanId);
    }

    // ! SHOULD THROW NOT FOUND WHEN LOAN APPLICATION DOES NOT EXIST
    @Test
    void should_throw_not_found_when_loan_application_does_not_exist() {
        // given
        Long nonExistentLoanId = 999L;
        when(loanApplicationRepository.findById(nonExistentLoanId)).thenReturn(Optional.empty());

        // when & then
        LoanApplicationNotFoundException exception = assertThrows(LoanApplicationNotFoundException.class,
                () -> loanApplicationService.getLoanApplicationById(nonExistentLoanId));

        assertEquals("Loan application not found", exception.getMessage());
    }

    // ! SHOULD APPROVE LOAN WHEN USER IS APPROVER====================================
    @Test
    void should_approve_loan_when_user_is_approver() {
        // given
        Long loanId = 100L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer);
        loan.setLoanAmount(new BigDecimal("5000000"));
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any(LoanApplicationEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        LoanApplicationResponse response = loanApplicationService.approveLoanApplication(loanId, UserRole.APPROVER);

        // then
        assertNotNull(response);
        assertEquals(LoanStatus.APPROVED, response.getStatus());
        verify(repaymentScheduleService, times(1)).createRepaymentSchedules(any(LoanApplicationEntity.class));
        verify(loanApplicationRepository, times(1)).save(loan);
    }

    // ! SHOULD REJECT LOAN
    @Test
    void should_reject_loan_successfully() {
        // given
        Long loanId = 100L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any(LoanApplicationEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        LoanApplicationResponse response = loanApplicationService.rejectLoanApplication(loanId);

        // then
        assertNotNull(response);
        assertEquals(LoanStatus.REJECTED, response.getStatus());
        verify(loanApplicationRepository, times(1)).save(loan);
    }

    // ! SHOULD THROW FORBIDDEN WHEN MANAGER TRIES TO APPROVE LOAN <= 10M
    @Test
    void should_throw_forbidden_when_manager_tries_to_approve_loan_under_or_equal_to_10m() {
        // given
        Long loanId = 100L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer);
        loan.setLoanAmount(new BigDecimal("10000000")); // Batas kritis batas maksimum manajer ditolak (<= 10.000.000)
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // when & then
        ForbiddenException exception = assertThrows(ForbiddenException.class,
                () -> loanApplicationService.approveLoanApplication(loanId, UserRole.MANAGER));

        assertEquals("Manager is only allowed to approve loans above 10,000,000", exception.getMessage());
        verify(loanApplicationRepository, never()).save(any());
        verify(repaymentScheduleService, never()).createRepaymentSchedules(any());
    }

    // ! SHOULD ALLOW MANAGER TO APPROVE LOAN > 10M (HAPPY PATH FOR MANAGER)
    @Test
    void should_allow_manager_to_approve_loan_above_10m() {
        // given
        Long loanId = 100L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer);
        loan.setLoanAmount(new BigDecimal("15000000")); // > 10.000.000 (Valid untuk level Manager)
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any(LoanApplicationEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        LoanApplicationResponse response = loanApplicationService.approveLoanApplication(loanId, UserRole.MANAGER);

        // then
        assertNotNull(response);
        assertEquals(LoanStatus.APPROVED, response.getStatus());
        verify(loanApplicationRepository, times(1)).save(loan);
        verify(repaymentScheduleService, times(1)).createRepaymentSchedules(any(LoanApplicationEntity.class));
    }
}