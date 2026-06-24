package com.fif.loanapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fif.loanapplication.dto.loanapplication.CreateLoanApplicationRequest;
import com.fif.loanapplication.dto.loanapplication.LoanApplicationResponse;
import com.fif.loanapplication.dto.loanapplication.UpdateLoanStatusRequest;
import com.fif.loanapplication.entity.CustomerEntity;
import com.fif.loanapplication.entity.LoanApplicationEntity;
import com.fif.loanapplication.entity.RepaymentScheduleEntity;
import com.fif.loanapplication.entity.enums.LoanStatus;
import com.fif.loanapplication.exception.CustomerNotFoundException;
import com.fif.loanapplication.exception.LoanApplicationNotFoundException;
import com.fif.loanapplication.repository.CustomerRepository;
import com.fif.loanapplication.repository.LoanApplicationRepository;
import com.fif.loanapplication.repository.RepaymentScheduleRepository;
import com.fif.loanapplication.utils.LoanApplicationUtils;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanApplicationUtils loanApplicationUtils;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @Mock
    private RepaymentScheduleService repaymentScheduleService;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    @Test
    void createLoanApplication_whenRequestValid_shouldCreateLoanAndRepaymentSchedules() {
        // Given
        UUID customerUid = UUID.randomUUID();
        UUID loanUid = UUID.randomUUID();

        CreateLoanApplicationRequest request = createLoanApplicationRequest(customerUid);
        CustomerEntity customer = createCustomerEntity(customerUid);

        LoanApplicationEntity savedLoanApplication = createLoanApplicationEntity(
                loanUid,
                customer,
                LoanStatus.SUBMITTED);

        RepaymentScheduleEntity repaymentSchedule = RepaymentScheduleEntity.builder()
                .build();

        List<RepaymentScheduleEntity> repaymentSchedules = List.of(repaymentSchedule);

        when(customerRepository.findById(customerUid))
                .thenReturn(Optional.of(customer));

        when(loanApplicationRepository.save(any(LoanApplicationEntity.class)))
                .thenReturn(savedLoanApplication);

        when(repaymentScheduleService.generateRepaymentSchedules(savedLoanApplication))
                .thenReturn(repaymentSchedules);

        // When
        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);

        // Then
        assertEquals(loanUid, response.getUid());
        assertEquals(BigDecimal.valueOf(10_000_000), response.getLoanAmount());
        assertEquals(12, response.getTenorMonth());
        assertEquals("Modal usaha", response.getPurpose());
        assertEquals(LoanStatus.SUBMITTED, response.getStatus());
        assertEquals(customerUid, response.getCustomer().getUid());

        verify(customerRepository).findById(customerUid);
        verify(loanApplicationRepository).save(any(LoanApplicationEntity.class));
        verify(repaymentScheduleService).generateRepaymentSchedules(savedLoanApplication);
        verify(repaymentScheduleRepository).saveAll(repaymentSchedules);
    }

    @Test
    void createLoanApplication_whenCustomerNotFound_shouldThrowCustomerNotFoundException() {
        // Given
        UUID customerUid = UUID.randomUUID();

        CreateLoanApplicationRequest request = createLoanApplicationRequest(customerUid);

        when(customerRepository.findById(customerUid))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                CustomerNotFoundException.class,
                () -> loanApplicationService.createLoanApplication(request));

        verify(loanApplicationRepository, never()).save(any(LoanApplicationEntity.class));
        verify(repaymentScheduleService, never()).generateRepaymentSchedules(any(LoanApplicationEntity.class));
        verify(repaymentScheduleRepository, never()).saveAll(any());
    }

    @Test
    void getLoanByUid_whenLoanExists_shouldReturnLoanResponse() {
        // Given
        UUID customerUid = UUID.randomUUID();
        UUID loanUid = UUID.randomUUID();

        CustomerEntity customer = createCustomerEntity(customerUid);

        LoanApplicationEntity loanApplication = createLoanApplicationEntity(
                loanUid,
                customer,
                LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByUidWithCustomer(loanUid))
                .thenReturn(Optional.of(loanApplication));

        // When
        LoanApplicationResponse response = loanApplicationService.getLoanByUid(loanUid);

        // Then
        assertEquals(loanUid, response.getUid());
        assertEquals(customerUid, response.getCustomer().getUid());
        assertEquals(LoanStatus.SUBMITTED, response.getStatus());
    }

    @Test
    void getLoanByUid_whenLoanNotFound_shouldThrowLoanApplicationNotFoundException() {
        // Given
        UUID loanUid = UUID.randomUUID();

        when(loanApplicationRepository.findByUidWithCustomer(loanUid))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                LoanApplicationNotFoundException.class,
                () -> loanApplicationService.getLoanByUid(loanUid));
    }

    @Test
    void getLoans_whenStatusIsNull_shouldReturnAllLoans() {
        // Given
        CustomerEntity customer = createCustomerEntity(UUID.randomUUID());

        LoanApplicationEntity loan1 = createLoanApplicationEntity(
                UUID.randomUUID(),
                customer,
                LoanStatus.SUBMITTED);

        LoanApplicationEntity loan2 = createLoanApplicationEntity(
                UUID.randomUUID(),
                customer,
                LoanStatus.APPROVED);

        when(loanApplicationRepository.findAllWithCustomer())
                .thenReturn(List.of(loan1, loan2));

        // When
        List<LoanApplicationResponse> responses = loanApplicationService.getLoans(null);

        // Then
        assertEquals(2, responses.size());

        verify(loanApplicationRepository).findAllWithCustomer();
        verify(loanApplicationRepository, never()).findByStatus(any());
    }

    @Test
    void getLoans_whenStatusProvided_shouldReturnFilteredLoans() {
        // Given
        CustomerEntity customer = createCustomerEntity(UUID.randomUUID());

        LoanApplicationEntity loan = createLoanApplicationEntity(
                UUID.randomUUID(),
                customer,
                LoanStatus.APPROVED);

        when(loanApplicationRepository.findByStatus(LoanStatus.APPROVED))
                .thenReturn(List.of(loan));

        // When
        List<LoanApplicationResponse> responses = loanApplicationService.getLoans(LoanStatus.APPROVED);

        // Then
        assertEquals(1, responses.size());
        assertEquals(LoanStatus.APPROVED, responses.get(0).getStatus());

        verify(loanApplicationRepository).findByStatus(LoanStatus.APPROVED);
        verify(loanApplicationRepository, never()).findAllWithCustomer();
    }

    @Test
    void approveLoanApplicationByUid_whenLoanNotFound_shouldThrowLoanApplicationNotFoundException() {
        // Given
        UUID loanUid = UUID.randomUUID();

        UpdateLoanStatusRequest request = createUpdateLoanStatusRequest(LoanStatus.APPROVED);

        when(loanApplicationRepository.findById(loanUid))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                LoanApplicationNotFoundException.class,
                () -> loanApplicationService.approveLoanApplicationByUid(loanUid, request));

        verify(loanApplicationUtils, never()).validateStatusTransition(any(), any());
        verify(loanApplicationRepository, never()).save(any(LoanApplicationEntity.class));
    }

    @Test
    void approveLoanApplicationByUid_whenNewStatusApproved_shouldUpdateStatusToApproved() {
        // Given
        UUID customerUid = UUID.randomUUID();
        UUID loanUid = UUID.randomUUID();

        CustomerEntity customer = createCustomerEntity(customerUid);

        LoanApplicationEntity loanApplication = createLoanApplicationEntity(
                loanUid,
                customer,
                LoanStatus.SUBMITTED);

        UpdateLoanStatusRequest request = createUpdateLoanStatusRequest(LoanStatus.APPROVED);

        when(loanApplicationRepository.findById(loanUid))
                .thenReturn(Optional.of(loanApplication));

        when(loanApplicationRepository.save(loanApplication))
                .thenReturn(loanApplication);

        // When
        LoanApplicationResponse response = loanApplicationService.approveLoanApplicationByUid(loanUid, request);

        // Then
        assertEquals(LoanStatus.APPROVED, response.getStatus());

        verify(loanApplicationUtils).validateStatusTransition(
                LoanStatus.SUBMITTED,
                LoanStatus.APPROVED);

        verify(loanApplicationRepository).save(loanApplication);
    }

    @Test
    void approveLoanApplicationByUid_whenNewStatusRejected_shouldUpdateStatusToRejected() {
        // Given
        UUID customerUid = UUID.randomUUID();
        UUID loanUid = UUID.randomUUID();

        CustomerEntity customer = createCustomerEntity(customerUid);

        LoanApplicationEntity loanApplication = createLoanApplicationEntity(
                loanUid,
                customer,
                LoanStatus.SUBMITTED);

        UpdateLoanStatusRequest request = createUpdateLoanStatusRequest(LoanStatus.REJECTED);

        when(loanApplicationRepository.findById(loanUid))
                .thenReturn(Optional.of(loanApplication));

        when(loanApplicationRepository.save(loanApplication))
                .thenReturn(loanApplication);

        // When
        LoanApplicationResponse response = loanApplicationService.approveLoanApplicationByUid(loanUid, request);

        // Then
        assertEquals(LoanStatus.REJECTED, response.getStatus());

        verify(loanApplicationUtils).validateStatusTransition(
                LoanStatus.SUBMITTED,
                LoanStatus.REJECTED);

        verify(loanApplicationRepository).save(loanApplication);
    }

    @Test
    void approveLoanApplicationByUid_whenNewStatusDisbursed_shouldUseGenericStatusBranch() {
        // Given
        UUID customerUid = UUID.randomUUID();
        UUID loanUid = UUID.randomUUID();

        CustomerEntity customer = createCustomerEntity(customerUid);

        LoanApplicationEntity loanApplication = createLoanApplicationEntity(
                loanUid,
                customer,
                LoanStatus.APPROVED);

        UpdateLoanStatusRequest request = createUpdateLoanStatusRequest(LoanStatus.DISBURSED);

        when(loanApplicationRepository.findById(loanUid))
                .thenReturn(Optional.of(loanApplication));

        when(loanApplicationRepository.save(loanApplication))
                .thenReturn(loanApplication);

        // When
        LoanApplicationResponse response = loanApplicationService.approveLoanApplicationByUid(loanUid, request);

        // Then
        assertEquals(LoanStatus.DISBURSED, response.getStatus());

        verify(loanApplicationUtils).validateStatusTransition(
                LoanStatus.APPROVED,
                LoanStatus.DISBURSED);

        verify(loanApplicationRepository).save(loanApplication);
    }

    @Test
    void getLoanApplicationByCustomerUid_shouldReturnLoanApplications() {
        // Given
        UUID customerUid = UUID.randomUUID();

        CustomerEntity customer = createCustomerEntity(customerUid);

        LoanApplicationEntity loan1 = createLoanApplicationEntity(
                UUID.randomUUID(),
                customer,
                LoanStatus.SUBMITTED);

        LoanApplicationEntity loan2 = createLoanApplicationEntity(
                UUID.randomUUID(),
                customer,
                LoanStatus.APPROVED);

        when(loanApplicationRepository.findByCustomerUid(customerUid))
                .thenReturn(List.of(loan1, loan2));

        // When
        List<LoanApplicationResponse> responses = loanApplicationService.getLoanApplicationByCustomerUid(customerUid);

        // Then
        assertEquals(2, responses.size());
        assertEquals(customerUid, responses.get(0).getCustomer().getUid());
        assertEquals(customerUid, responses.get(1).getCustomer().getUid());
    }

    private CreateLoanApplicationRequest createLoanApplicationRequest(UUID customerUid) {
        return CreateLoanApplicationRequest.builder()
                .customerUid(customerUid)
                .loanAmount(BigDecimal.valueOf(10_000_000))
                .tenorMonth(12)
                .purpose("Modal usaha")
                .build();
    }

    private UpdateLoanStatusRequest createUpdateLoanStatusRequest(LoanStatus status) {
        return UpdateLoanStatusRequest.builder()
                .status(status)
                .build();
    }

    private CustomerEntity createCustomerEntity(UUID customerUid) {
        return CustomerEntity.builder()
                .uid(customerUid)
                .nik("1234567890123456")
                .fullName("steven")
                .email("steven@gmail.com")
                .phoneNumber("081234567890")
                .build();
    }

    private LoanApplicationEntity createLoanApplicationEntity(
            UUID loanUid,
            CustomerEntity customer,
            LoanStatus status) {
        return LoanApplicationEntity.builder()
                .uid(loanUid)
                .customer(customer)
                .loanAmount(BigDecimal.valueOf(10_000_000))
                .tenorMonth(12)
                .purpose("Modal usaha")
                .status(status)
                .build();
    }
}