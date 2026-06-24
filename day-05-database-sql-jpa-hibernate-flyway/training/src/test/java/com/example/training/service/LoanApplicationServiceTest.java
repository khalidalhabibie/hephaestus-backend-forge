package com.example.training.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.training.auth.AuthContext;
import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.dto.UpdateLoanStatusRequest;
import com.example.training.entity.CustomerEntity;
import com.example.training.entity.LoanApplicationEntity;
import com.example.training.entity.RepaymentScheduleEntity;
import com.example.training.enums.LoanStatus;
import com.example.training.enums.RepaymentStatus;
import com.example.training.exception.ForbiddenException;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.CustomerRepository;
import com.example.training.repository.LoanApplicationRepository;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RepaymentScheduleService repaymentScheduleService;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    private CreateLoanApplicationRequest request;
    private CustomerEntity customerEntity;
    private AuthContext approverAuth;
    private AuthContext staffAuth;

    @BeforeEach
    void setUp() {
        request = new CreateLoanApplicationRequest();
        request.setCustomerId(UUID.fromString("6e52276f-142b-4841-b48b-b7f5ac244f01"));
        request.setLoanAmount(BigDecimal.valueOf(5000000));
        request.setTenorMonth(3);
        request.setPurpose("Buat beli rumah");

        customerEntity = new CustomerEntity();
        customerEntity.setId(request.getCustomerId());
        customerEntity.setFullName("Test User");
        customerEntity.setNik("123456789");
        customerEntity.setEmail("test@mail.com");
        customerEntity.setPhoneNumber("08123456789");

        approverAuth = new AuthContext("approver-1", "APPROVER");
        staffAuth = new AuthContext("staff-1", "STAFF");
    }

    // --- create ---

    @Test
    void should_create_loan_application_successfully() {
        // given
        LoanApplicationEntity savedLoan = new LoanApplicationEntity();
        savedLoan.setId(UUID.randomUUID());
        savedLoan.setCustomer(customerEntity);
        savedLoan.setLoanAmount(request.getLoanAmount());
        savedLoan.setTenorMonth(request.getTenorMonth());
        savedLoan.setPurpose(request.getPurpose());
        savedLoan.setStatus(LoanStatus.SUBMITTED);

        when(customerRepository.findById(request.getCustomerId()))
                .thenReturn(Optional.of(customerEntity));
        when(loanApplicationRepository.save(any())).thenReturn(savedLoan);

        // when
        LoanApplicationResponse response = loanApplicationService.create(request);

        // then
        assertNotNull(response);
        assertEquals(savedLoan.getId(), response.getId());
        assertEquals("SUBMITTED", response.getStatus());
    }

    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
        // given
        when(customerRepository.findById(request.getCustomerId()))
                .thenReturn(Optional.empty());

        // when & then
        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> loanApplicationService.create(request));
        assertEquals("CUSTOMER_NOT_FOUND", ex.getCode());
    }

    // --- findById ---

    @Test
    void should_get_loan_application_by_id_successfully() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        // when
        LoanApplicationResponse response = loanApplicationService.findById(id);

        // then
        assertNotNull(response);
        assertEquals(id, response.getId());
    }

    @Test
    void should_throw_not_found_when_loan_application_does_not_exist() {
        // given
        UUID id = UUID.randomUUID();
        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.empty());

        // when & then
        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> loanApplicationService.findById(id));
        assertEquals("LOAN_APPLICATION_NOT_FOUND", ex.getCode());
    }

    // --- approveLoan ---

    @Test
    void should_approve_loan_when_user_is_approver() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        // when
        LoanApplicationResponse response = loanApplicationService.approveLoan(id, approverAuth);

        // then
        assertNotNull(response);
        verify(loanApplicationRepository).save(any());
    }

    @Test
    void should_reject_loan_when_user_is_approver() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        // when
        LoanApplicationResponse response = loanApplicationService.rejectLoan(id, approverAuth);

        // then
        assertNotNull(response);
        verify(loanApplicationRepository).save(any());
    }

    @Test
    void should_throw_forbidden_when_staff_tries_to_approve_loan() {
        // given
        UUID id = UUID.randomUUID();

        // when & then
        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> loanApplicationService.approveLoan(id, staffAuth));
        assertEquals("FORBIDDEN", ex.getCode());
        verify(loanApplicationRepository, never()).save(any());
    }

    @Test
    void should_throw_forbidden_when_staff_tries_to_reject_loan() {
        // given
        UUID id = UUID.randomUUID();

        // when & then
        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> loanApplicationService.rejectLoan(id, staffAuth));
        assertEquals("FORBIDDEN", ex.getCode());
        verify(loanApplicationRepository, never()).save(any());
    }

    // --- findByStatus ---

    @Test
    void should_find_by_status_successfully() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(UUID.randomUUID());
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByStatus(LoanStatus.SUBMITTED))
                .thenReturn(List.of(loan));

        // when
        List<LoanApplicationResponse> result = loanApplicationService.findByStatus("submitted");

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void should_throw_on_invalid_status() {
        // given & when & then
        assertThrows(IllegalArgumentException.class,
                () -> loanApplicationService.findByStatus("invalid"));
    }

    // --- findByCustomerId ---

    @Test
    void should_find_by_customer_id_successfully() {
        // given
        UUID customerId = request.getCustomerId();
        when(customerRepository.existsById(customerId)).thenReturn(true);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(UUID.randomUUID());
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setLoanAmount(BigDecimal.valueOf(1000));
        loan.setTenorMonth(3);
        loan.setPurpose("test");

        when(loanApplicationRepository.findLoansByCustomerId(customerId))
                .thenReturn(List.of(loan));

        // when
        List<LoanApplicationResponse> result = loanApplicationService.findByCustomerId(customerId);

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_throw_not_found_when_customer_id_does_not_exist_for_find_by() {
        // given
        UUID customerId = request.getCustomerId();
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // when & then
        assertThrows(NotFoundException.class,
                () -> loanApplicationService.findByCustomerId(customerId));
    }

    // --- findAll ---

    @Test
    void should_find_all_loan_applications() {
        // given
        LoanApplicationEntity loan1 = new LoanApplicationEntity();
        loan1.setId(UUID.randomUUID());
        loan1.setCustomer(customerEntity);
        loan1.setStatus(LoanStatus.SUBMITTED);
        loan1.setLoanAmount(BigDecimal.valueOf(1000));
        loan1.setTenorMonth(3);
        loan1.setPurpose("a");

        LoanApplicationEntity loan2 = new LoanApplicationEntity();
        loan2.setId(UUID.randomUUID());
        loan2.setCustomer(customerEntity);
        loan2.setStatus(LoanStatus.APPROVED);
        loan2.setLoanAmount(BigDecimal.valueOf(2000));
        loan2.setTenorMonth(6);
        loan2.setPurpose("b");

        when(loanApplicationRepository.findAll()).thenReturn(List.of(loan1, loan2));

        // when
        List<LoanApplicationResponse> result = loanApplicationService.findAll();

        // then
        assertEquals(2, result.size());
    }

    // --- updateStatus ---

    @Test
    void should_update_status_to_approved() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("APPROVED");

        // when
        LoanApplicationResponse result = loanApplicationService.updateStatus(id, req);

        // then
        assertNotNull(result);
    }

    @Test
    void should_update_status_to_rejected() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("REJECTED");

        // when
        LoanApplicationResponse result = loanApplicationService.updateStatus(id, req);

        // then
        assertNotNull(result);
    }

    @Test
    void should_update_status_to_disbursed_and_generate_schedule() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.APPROVED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("DISBURSED");

        // when
        LoanApplicationResponse result = loanApplicationService.updateStatus(id, req);

        // then
        assertNotNull(result);
        verify(repaymentScheduleService, times(1)).generateSchedule(id);
    }

    @Test
    void should_throw_when_update_status_not_found() {
        // given
        UUID id = UUID.randomUUID();
        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.empty());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("APPROVED");

        // when & then
        assertThrows(NotFoundException.class,
                () -> loanApplicationService.updateStatus(id, req));
    }

    @Test
    void should_throw_when_transition_from_submitted_to_disbursed() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("DISBURSED");

        // when & then
        assertThrows(IllegalStateException.class,
                () -> loanApplicationService.updateStatus(id, req));
    }

    @Test
    void should_throw_when_transition_from_approved_to_rejected() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.APPROVED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("REJECTED");

        // when & then
        assertThrows(IllegalStateException.class,
                () -> loanApplicationService.updateStatus(id, req));
    }

    @Test
    void should_throw_when_loan_already_in_final_status_rejected() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.REJECTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("APPROVED");

        // when & then
        assertThrows(IllegalStateException.class,
                () -> loanApplicationService.updateStatus(id, req));
    }

    @Test
    void should_throw_when_loan_already_in_final_status_closed() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.CLOSED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("APPROVED");

        // when & then
        assertThrows(IllegalStateException.class,
                () -> loanApplicationService.updateStatus(id, req));
    }

    @Test
    void should_throw_when_closing_loan_with_empty_schedules() {
        // given
        UUID id = UUID.randomUUID();
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.DISBURSED);
        loan.setRepaymentSchedules(List.of()); // empty schedules → cannot close

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("CLOSED");

        // when & then
        assertThrows(IllegalStateException.class,
                () -> loanApplicationService.updateStatus(id, req));
    }

    @Test
    void should_throw_when_closing_loan_with_some_schedules_unpaid() {
        // given
        UUID id = UUID.randomUUID();

        RepaymentScheduleEntity unpaid = new RepaymentScheduleEntity();
        unpaid.setStatus(RepaymentStatus.UNPAID);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.DISBURSED);
        loan.setRepaymentSchedules(List.of(unpaid));

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("CLOSED");

        // when & then
        assertThrows(IllegalStateException.class,
                () -> loanApplicationService.updateStatus(id, req));
    }

    @Test
    void should_close_loan_when_all_schedules_are_paid() {
        // given
        UUID id = UUID.randomUUID();

        RepaymentScheduleEntity paidSchedule = new RepaymentScheduleEntity();
        paidSchedule.setStatus(RepaymentStatus.PAID);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.DISBURSED);
        loan.setRepaymentSchedules(List.of(paidSchedule));

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any())).thenReturn(loan);

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("CLOSED");

        // when
        LoanApplicationResponse result = loanApplicationService.updateStatus(id, req);

        // then
        assertNotNull(result);
        verify(loanApplicationRepository).save(any());
    }
}
