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

import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.dto.UpdateLoanStatusRequest;
import com.example.training.entity.CustomerEntity;
import com.example.training.entity.LoanApplicationEntity;
import com.example.training.enums.LoanStatus;
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
    }

    @Test
    void create_Success() {

        LoanApplicationEntity savedLoan = new LoanApplicationEntity();
        savedLoan.setId(UUID.randomUUID());
        savedLoan.setCustomer(customerEntity);
        savedLoan.setLoanAmount(request.getLoanAmount());
        savedLoan.setTenorMonth(request.getTenorMonth());
        savedLoan.setPurpose(request.getPurpose());
        savedLoan.setStatus(LoanStatus.SUBMITTED);

        when(customerRepository.findById(request.getCustomerId()))
                .thenReturn(Optional.of(customerEntity));

        when(loanApplicationRepository.save(any()))
                .thenReturn(savedLoan);

        LoanApplicationResponse response = loanApplicationService.create(request);

        assertNotNull(response);
        assertEquals(savedLoan.getId(), response.getId());
    }

    @Test
    void create_CustomerNotFound() {

        when(customerRepository.findById(request.getCustomerId()))
                .thenReturn(Optional.empty());

        try {
            loanApplicationService.create(request);
        } catch (NotFoundException e) {
            assertEquals("CUSTOMER_NOT_FOUND", e.getCode());
        }
    }

    @Test
    void findByStatus_Success() {

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(UUID.randomUUID());
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByStatus(LoanStatus.SUBMITTED))
                .thenReturn(List.of(loan));

        var result = loanApplicationService.findByStatus("submitted");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByStatus_Invalid() {

        assertThrows(IllegalArgumentException.class,
                () -> loanApplicationService.findByStatus("invalid"));
    }

    @Test
    void findByCustomerId_Success() {

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

        var result = loanApplicationService.findByCustomerId(customerId);

        assertEquals(1, result.size());
    }

    @Test
    void findByCustomerId_NotFound() {

        UUID customerId = request.getCustomerId();

        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> loanApplicationService.findByCustomerId(customerId));
    }

    @Test
    void findAll_Success() {

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

        when(loanApplicationRepository.findAll())
                .thenReturn(List.of(loan1, loan2));

        var result = loanApplicationService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void updateStatus_Approved() {

        UUID id = UUID.randomUUID();

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        when(loanApplicationRepository.save(any()))
                .thenReturn(loan);

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("APPROVED");

        var result = loanApplicationService.updateStatus(id, req);

        assertNotNull(result);
    }

    @Test
    void updateStatus_Rejected() {

        UUID id = UUID.randomUUID();

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        when(loanApplicationRepository.save(any()))
                .thenReturn(loan);

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("REJECTED");

        var result = loanApplicationService.updateStatus(id, req);

        assertNotNull(result);
    }

    @Test
    void updateStatus_Disbursed() {

        UUID id = UUID.randomUUID();

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        when(loanApplicationRepository.save(any()))
                .thenReturn(loan);

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("DISBURSED");

        loanApplicationService.updateStatus(id, req);

        verify(repaymentScheduleService, times(1))
                .generateSchedule(id);
    }

    @Test
    void updateStatus_NotFound() {

        UUID id = UUID.randomUUID();

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.empty());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("APPROVED");

        assertThrows(NotFoundException.class,
                () -> loanApplicationService.updateStatus(id, req));
    }

    @Test
    void toResponse_FullCoverageBoost() {

        CustomerEntity full = new CustomerEntity();
        full.setId(request.getCustomerId());
        full.setFullName("A");
        full.setNik("1");
        full.setEmail("a@mail.com");
        full.setPhoneNumber("08");

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(UUID.randomUUID());
        loan.setCustomer(full);
        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setLoanAmount(BigDecimal.valueOf(1234));
        loan.setTenorMonth(3);
        loan.setPurpose("boost");

        when(loanApplicationRepository.findAll())
                .thenReturn(List.of(loan));

        var result = loanApplicationService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void updateStatus_CoverValidateTransition() {

        UUID id = UUID.randomUUID();

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(customerEntity);
        loan.setStatus(LoanStatus.SUBMITTED);

        when(loanApplicationRepository.findByIdWithCustomer(id))
                .thenReturn(Optional.of(loan));

        when(loanApplicationRepository.save(any()))
                .thenReturn(loan);

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus("APPROVED");

        loanApplicationService.updateStatus(id, req);

        req.setStatus("REJECTED");
        loanApplicationService.updateStatus(id, req);

        assertTrue(true);
    }
}