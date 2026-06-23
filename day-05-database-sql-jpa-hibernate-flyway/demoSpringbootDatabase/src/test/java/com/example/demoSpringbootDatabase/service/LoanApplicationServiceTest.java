package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.*;
import com.example.demoSpringbootDatabase.entity.CustomerEntity;
import com.example.demoSpringbootDatabase.entity.LoanApplicationEntity;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.CustomerNotFoundException;
import com.example.demoSpringbootDatabase.exception.LoanApplicationNotFoundException;
import com.example.demoSpringbootDatabase.repository.CustomerRepository;
import com.example.demoSpringbootDatabase.repository.LoanApplicationRepository;
import com.example.demoSpringbootDatabase.repository.RepaymentScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock private LoanApplicationRepository loanRepository;
    @Mock private CustomerRepository customerRepository;
    @Mock private RepaymentScheduleRepository scheduleRepository;

    @InjectMocks private LoanApplicationService loanApplicationService;

    private CustomerEntity customer;
    private LoanApplicationEntity loanApplication;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(loanApplicationService, "annualInterestRate", 0.12);

        customer = CustomerEntity.builder().id(1L).fullName("Budi").nik("123").email("budi@mail.com").build();
        loanApplication = LoanApplicationEntity.builder()
                .id(10L).customer(customer).loanAmount(20000000L).tenorMonth(6).status("SUBMITTED").build();
    }

    // --- SECTION 1: CREATE LOAN ---

    @Test
    @DisplayName("Happy Path: Ajukan Pinjaman Baru Sukses")
    void createLoan_HappyPath_Success() {
        CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
        req.setCustomerId(1L);
        req.setLoanAmount(20000000L);
        req.setTenorMonth(6);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(loanRepository.save(any(LoanApplicationEntity.class))).thenReturn(loanApplication);

        LoanApplicationResponse response = loanApplicationService.createLoan(req);

        assertNotNull(response);
        assertEquals("SUBMITTED", response.getStatus());
        verify(loanRepository, times(1)).save(any(LoanApplicationEntity.class));
    }

    @Test
    @DisplayName("Negative Path: Ajukan Pinjaman Gagal karena Customer Tidak Ada")
    void createLoan_NegativePath_CustomerNotFound() {
        CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
        req.setCustomerId(99L);

        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> loanApplicationService.createLoan(req));
    }

    // --- SECTION 2: UPDATE STATUS & STATE MACHINE ---

    @Test
    @DisplayName("Happy Path: Update Status Sama (Idempotent)")
    void updateStatus_HappyPath_SameStatus() {
        when(loanRepository.findById(10L)).thenReturn(Optional.of(loanApplication));

        LoanApplicationResponse response = loanApplicationService.updateStatus(10L, "SUBMITTED");

        assertNotNull(response);
        assertEquals("SUBMITTED", response.getStatus());
        verify(loanRepository, never()).save(any());
    }

    @Test
    @DisplayName("Happy Path: Transisi Status SUBMITTED ke REJECTED")
    void updateStatus_HappyPath_Reject() {
        when(loanRepository.findById(10L)).thenReturn(Optional.of(loanApplication));

        LoanApplicationResponse response = loanApplicationService.updateStatus(10L, "REJECTED");

        assertEquals("REJECTED", response.getStatus());
    }

    @Test
    @DisplayName("Negative Path: Update Status Gagal karena Loan Aplikasi Tidak Ditemukan")
    void updateStatus_NegativePath_LoanNotFound() {
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LoanApplicationNotFoundException.class, () -> loanApplicationService.updateStatus(99L, "APPROVED"));
    }

    @Test
    @DisplayName("Negative Path: Transisi dari Status Akhir (REJECTED) Tidak Diizinkan")
    void updateStatus_NegativePath_FromFinalState() {
        loanApplication.setStatus("REJECTED");
        when(loanRepository.findById(10L)).thenReturn(Optional.of(loanApplication));

        assertThrows(IllegalArgumentException.class, () -> loanApplicationService.updateStatus(10L, "APPROVED"));
    }

    @Test
    @DisplayName("Negative Path: Transisi Ilegal dari SUBMITTED ke selain APPROVED/REJECTED")
    void updateStatus_NegativePath_InvalidSubmittedTransition() {
        when(loanRepository.findById(10L)).thenReturn(Optional.of(loanApplication));

        assertThrows(IllegalArgumentException.class, () -> loanApplicationService.updateStatus(10L, "DISBURSED"));
    }

    @Test
    @DisplayName("Happy Path: Transisi APPROVED ke DISBURSED (Trigger Generate Schedules)")
    void updateStatus_HappyPath_Disburse() {
        loanApplication.setStatus("APPROVED");
        when(loanRepository.findById(10L)).thenReturn(Optional.of(loanApplication));

        LoanApplicationResponse response = loanApplicationService.updateStatus(10L, "DISBURSED");

        assertEquals("DISBURSED", response.getStatus());
        verify(scheduleRepository, times(6)).save(any(RepaymentScheduleEntity.class));
    }

    @Test
    @DisplayName("Negative Path: Transisi DISBURSED ke CLOSED Gagal karena Cicilan Belum Lunas")
    void updateStatus_NegativePath_DisbursedToClosedNotPaid() {
        loanApplication.setStatus("DISBURSED");
        when(loanRepository.findById(10L)).thenReturn(Optional.of(loanApplication));
        
        RepaymentScheduleEntity unpaidSchedule = RepaymentScheduleEntity.builder().status("UNPAID").build();
        when(scheduleRepository.findByLoanApplicationId(10L)).thenReturn(List.of(unpaidSchedule));

        assertThrows(IllegalArgumentException.class, () -> loanApplicationService.updateStatus(10L, "CLOSED"));
    }

    @Test
    @DisplayName("Happy Path: Transisi DISBURSED ke CLOSED Sukses saat Semua Cicilan PAID")
    void updateStatus_HappyPath_DisbursedToClosedSuccess() {
        loanApplication.setStatus("DISBURSED");
        when(loanRepository.findById(10L)).thenReturn(Optional.of(loanApplication));
        
        RepaymentScheduleEntity paidSchedule = RepaymentScheduleEntity.builder().status("PAID").build();
        when(scheduleRepository.findByLoanApplicationId(10L)).thenReturn(List.of(paidSchedule));

        LoanApplicationResponse response = loanApplicationService.updateStatus(10L, "CLOSED");

        assertEquals("CLOSED", response.getStatus());
    }

    @Test
    @DisplayName("Negative Path: Bad Transition State (Unknown Status)")
    void updateStatus_NegativePath_UnknownStatus() {
        loanApplication.setStatus("UNKNOWN");
        when(loanRepository.findById(10L)).thenReturn(Optional.of(loanApplication));

        assertThrows(IllegalArgumentException.class, () -> loanApplicationService.updateStatus(10L, "CLOSED"));
    }

    // --- SECTION 3: READ OPERATIONS (GETTERS) ---

    @Test
    @DisplayName("Happy Path: Get By ID Sukses")
    void getById_HappyPath_Success() {
        when(loanRepository.findByIdWithCustomer(10L)).thenReturn(Optional.of(loanApplication));

        LoanApplicationResponse response = loanApplicationService.getById(10L);

        assertNotNull(response);
        assertEquals(10L, response.getId());
    }

    @Test
    @DisplayName("Negative Path: Get By ID Tidak Ditemukan")
    void getById_NegativePath_NotFound() {
        when(loanRepository.findByIdWithCustomer(99L)).thenReturn(Optional.empty());

        assertThrows(LoanApplicationNotFoundException.class, () -> loanApplicationService.getById(99L));
    }

    @Test
    @DisplayName("Happy Path: Get All Dengan Filter Status")
    void getAll_WithStatus() {
        when(loanRepository.findByStatus("SUBMITTED")).thenReturn(List.of(loanApplication));

        List<LoanApplicationResponse> result = loanApplicationService.getAll("submitted");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Happy Path: Get All Tanpa Filter Status")
    void getAll_WithoutStatus() {
        when(loanRepository.findAll()).thenReturn(List.of(loanApplication));

        List<LoanApplicationResponse> result = loanApplicationService.getAll(null);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Happy Path: Get By Customer ID")
    void getByCustomerId_Success() {
        when(loanRepository.findLoansByCustomerId(1L)).thenReturn(List.of(loanApplication));

        List<LoanApplicationResponse> result = loanApplicationService.getByCustomerId(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Happy Path: Pagination Dengan Status")
    void getLoansWithPagination_WithStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDateTime now = LocalDateTime.now();
        Page<LoanApplicationEntity> page = new PageImpl<>(List.of(loanApplication));

        when(loanRepository.findByStatusAndCreatedAtBetween("SUBMITTED", now, now, pageable)).thenReturn(page);

        Page<LoanApplicationResponse> result = loanApplicationService.getLoansWithPagination("SUBMITTED", now, now, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Happy Path: Pagination Tanpa Status")
    void getLoansWithPagination_WithoutStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDateTime now = LocalDateTime.now();
        Page<LoanApplicationEntity> page = new PageImpl<>(List.of(loanApplication));

        when(loanRepository.findByCreatedAtBetween(now, now, pageable)).thenReturn(page);

        Page<LoanApplicationResponse> result = loanApplicationService.getLoansWithPagination("", now, now, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Happy Path: Get Status Summary Report Matrix")
    void getStatusSummary_Success() {
        List<LoanStatusSummaryDto> summaryList = new ArrayList<>();
        when(loanRepository.getSummaryByStatus()).thenReturn(summaryList);

        List<LoanStatusSummaryDto> result = loanApplicationService.getStatusSummary();

        assertNotNull(result);
    }

    @Test
    @DisplayName("Behavior 401: Simulasi Aksi Tanpa Token Otentikasi Valid")
    void updateStatus_Behavior401_Unauthorized() {
        when(loanRepository.findById(10L)).thenAnswer(invocation -> {
            throw new SecurityException("Unauthorized: Token expired or invalid signature");
        });

        assertThrows(SecurityException.class, () -> loanApplicationService.updateStatus(10L, "APPROVED"));
    }
}