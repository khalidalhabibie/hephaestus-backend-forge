package com.example.training.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
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

    private CustomerEntity customer;
    private LoanApplicationEntity loan;
    private UUID customerId;
    private UUID loanId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        loanId = UUID.randomUUID();

        customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setFullName("Budi Santoso");
        customer.setNik("3173010101900001");
        customer.setEmail("budi@example.com");
        customer.setPhoneNumber("08123456789");

        loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer);
        loan.setLoanAmount(new BigDecimal("50000000"));
        loan.setTenorMonth(12);
        loan.setPurpose("Modal usaha");
        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setCreatedAt(ZonedDateTime.now());
        loan.setUpdatedAt(ZonedDateTime.now());
    }

    @Test
    void create_shouldReturnResponse_whenCustomerExists() {
        CreateLoanApplicationRequest request = new CreateLoanApplicationRequest();
        request.setCustomerId(customerId);
        request.setLoanAmount(new BigDecimal("50000000"));
        request.setTenorMonth(12);
        request.setPurpose("Modal usaha");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(loanApplicationRepository.save(any(LoanApplicationEntity.class))).thenReturn(loan);

        LoanApplicationResponse response = loanApplicationService.create(request);

        assertThat(response.getId()).isEqualTo(loanId);
        assertThat(response.getCustomer().getId()).isEqualTo(customerId);
        assertThat(response.getCustomer().getFullName()).isEqualTo("Budi Santoso");
        assertThat(response.getLoanAmount()).isEqualByComparingTo("50000000");
        assertThat(response.getTenorMonth()).isEqualTo(12);
        assertThat(response.getPurpose()).isEqualTo("Modal usaha");
        assertThat(response.getStatus()).isEqualTo("SUBMITTED");

        verify(loanApplicationRepository).save(any(LoanApplicationEntity.class));
    }

    @Test
    void create_shouldThrowNotFound_whenCustomerDoesNotExist() {
        CreateLoanApplicationRequest request = new CreateLoanApplicationRequest();
        request.setCustomerId(customerId);
        request.setLoanAmount(new BigDecimal("50000000"));
        request.setTenorMonth(12);
        request.setPurpose("Modal usaha");

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanApplicationService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void findById_shouldReturnResponse_whenLoanExists() {
        when(loanApplicationRepository.findByIdWithCustomer(loanId)).thenReturn(Optional.of(loan));

        LoanApplicationResponse response = loanApplicationService.findById(loanId);

        assertThat(response.getId()).isEqualTo(loanId);
        assertThat(response.getCustomer().getFullName()).isEqualTo("Budi Santoso");
    }

    @Test
    void findById_shouldThrowNotFound_whenLoanDoesNotExist() {
        when(loanApplicationRepository.findByIdWithCustomer(loanId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanApplicationService.findById(loanId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Loan application not found");
    }

    @Test
    void findAll_shouldReturnList() {
        when(loanApplicationRepository.findAll()).thenReturn(List.of(loan));

        List<LoanApplicationResponse> result = loanApplicationService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(loanId);
    }

    @Test
    void findByStatus_shouldReturnFilteredList() {
        when(loanApplicationRepository.findByStatus(LoanStatus.SUBMITTED)).thenReturn(List.of(loan));

        List<LoanApplicationResponse> result = loanApplicationService.findByStatus("SUBMITTED");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo("SUBMITTED");
    }

    @Test
    void findByCustomerId_shouldReturnLoans_whenCustomerExists() {
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(loanApplicationRepository.findLoansByCustomerId(customerId)).thenReturn(List.of(loan));

        List<LoanApplicationResponse> result = loanApplicationService.findByCustomerId(customerId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomer().getId()).isEqualTo(customerId);
    }

    @Test
    void findByCustomerId_shouldThrowNotFound_whenCustomerDoesNotExist() {
        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThatThrownBy(() -> loanApplicationService.findByCustomerId(customerId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void updateStatus_shouldUpdateAndReturnResponse() {
        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
        request.setStatus("APPROVED");

        LoanApplicationEntity approvedLoan = new LoanApplicationEntity();
        approvedLoan.setId(loanId);
        approvedLoan.setCustomer(customer);
        approvedLoan.setLoanAmount(new BigDecimal("50000000"));
        approvedLoan.setTenorMonth(12);
        approvedLoan.setPurpose("Modal usaha");
        approvedLoan.setStatus(LoanStatus.APPROVED);
        approvedLoan.setCreatedAt(loan.getCreatedAt());
        approvedLoan.setUpdatedAt(ZonedDateTime.now());

        when(loanApplicationRepository.findByIdWithCustomer(loanId)).thenReturn(Optional.of(loan));
        when(loanApplicationRepository.save(any(LoanApplicationEntity.class))).thenReturn(approvedLoan);

        LoanApplicationResponse response = loanApplicationService.updateStatus(loanId, request);

        assertThat(response.getStatus()).isEqualTo("APPROVED");
    }

    @Test
    void updateStatus_shouldThrowNotFound_whenLoanDoesNotExist() {
        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
        request.setStatus("APPROVED");

        when(loanApplicationRepository.findByIdWithCustomer(loanId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanApplicationService.updateStatus(loanId, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Loan application not found");
    }
}
