package com.fif.exercise2.service;

import com.fif.exercise2.dto.CreateLoanApplicationRequest;
import com.fif.exercise2.dto.LoanApplicationResponse;
import com.fif.exercise2.dto.LoanSummaryResponse;
import com.fif.exercise2.dto.UpdateLoanStatusRequest;
import com.fif.exercise2.entity.CustomerEntity;
import com.fif.exercise2.entity.LoanApplicationEntity;
import com.fif.exercise2.exception.CustomerNotFoundException;
import com.fif.exercise2.exception.InvalidLoanStatusException;
import com.fif.exercise2.exception.LoanApplicationNotFoundException;
import com.fif.exercise2.repository.CustomerRepository;
import com.fif.exercise2.repository.LoanApplicationRepository;
import com.fif.exercise2.repository.RepaymentScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {
        @Mock
        private LoanApplicationRepository loanApplicationRepository;

        @Mock
        private CustomerRepository customerRepository;

        @Mock
        private RepaymentScheduleRepository repaymentScheduleRepository;

        @InjectMocks
        private LoanApplicationService loanApplicationService;

        // ==========================================================================
        // createLoanApplication
        // ==========================================================================

        @Test
        void should_create_loan_application_successfully() {
                // given
                ReflectionTestUtils.setField(loanApplicationService, "annualInterestRate", 0.12);

                CustomerEntity customer = buildCustomer(1L);
                when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

                LoanApplicationEntity saved = buildLoanEntity(10L, customer, "SUBMITTED",
                        new BigDecimal("10000000"), 12);
                when(loanApplicationRepository.save(any(LoanApplicationEntity.class))).thenReturn(saved);

                CreateLoanApplicationRequest request = buildCreateRequest(1L,
                        new BigDecimal("10000000"), 12, "Modal usaha");

                // when
                LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);

                // then
                assertNotNull(response);
                assertEquals(10L, response.getId());
                assertEquals("SUBMITTED", response.getStatus());
                verify(loanApplicationRepository, times(1)).save(any());
        }

        @Test
        void should_throw_not_found_when_customer_does_not_exist_on_create() {
                // given
                when(customerRepository.findById(99L)).thenReturn(Optional.empty());

                CreateLoanApplicationRequest request = buildCreateRequest(99L,
                        new BigDecimal("5000000"), 6, "Renovasi rumah");

                // when & then
                assertThrows(CustomerNotFoundException.class,
                        () -> loanApplicationService.createLoanApplication(request));

                verify(loanApplicationRepository, never()).save(any());
        }

        // ==========================================================================
        // getLoanApplicationById
        // ==========================================================================

        @Test
        void should_get_loan_application_by_id_successfully() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(5L, customer, "SUBMITTED",
                        new BigDecimal("5000000"), 6);
                when(loanApplicationRepository.findByIdWithCustomer(5L)).thenReturn(Optional.of(entity));

                // when
                LoanApplicationResponse response = loanApplicationService.getLoanApplicationById(5L);

                // then
                assertNotNull(response);
                assertEquals(5L, response.getId());
                assertEquals("SUBMITTED", response.getStatus());
                // customer tidak null → CustomerSummaryResponse ter-build (cover branch customer != null)
                assertNotNull(response.getCustomer());
                assertEquals(1L, response.getCustomer().getId());
        }

        @Test
        void should_throw_not_found_when_loan_application_does_not_exist() {
                // given
                when(loanApplicationRepository.findByIdWithCustomer(99L)).thenReturn(Optional.empty());

                // when & then
                assertThrows(LoanApplicationNotFoundException.class,
                        () -> loanApplicationService.getLoanApplicationById(99L));
        }

        @Test
        void should_build_response_without_customer_when_customer_is_null() {
                // given — cover branch "customer == null" di buildResponse (line 218)
                // entity tanpa customer — customer field null
                LoanApplicationEntity entity = buildLoanEntity(1L, null, "SUBMITTED",
                        new BigDecimal("5000000"), 6);
                when(loanApplicationRepository.findByIdWithCustomer(1L)).thenReturn(Optional.of(entity));

                // when
                LoanApplicationResponse response = loanApplicationService.getLoanApplicationById(1L);

                // then — tidak NPE, customer di response null
                assertNotNull(response);
                assertNull(response.getCustomer());
        }

        // ==========================================================================
        // getAllLoanApplications
        // ==========================================================================

        @Test
        void should_return_all_loan_applications() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                List<LoanApplicationEntity> entities = List.of(
                        buildLoanEntity(1L, customer, "SUBMITTED", new BigDecimal("5000000"), 6),
                        buildLoanEntity(2L, customer, "APPROVED", new BigDecimal("10000000"), 12)
                );
                when(loanApplicationRepository.findAll()).thenReturn(entities);

                // when
                List<LoanApplicationResponse> responses = loanApplicationService.getAllLoanApplications();

                // then
                assertEquals(2, responses.size());
                assertEquals("SUBMITTED", responses.get(0).getStatus());
                assertEquals("APPROVED", responses.get(1).getStatus());
        }

        // ==========================================================================
        // getLoansByCustomerId
        // ==========================================================================

        @Test
        void should_return_loans_by_customer_id() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                List<LoanApplicationEntity> entities = List.of(
                        buildLoanEntity(1L, customer, "SUBMITTED", new BigDecimal("5000000"), 6)
                );
                when(loanApplicationRepository.findLoansByCustomerId(1L)).thenReturn(entities);

                // when
                List<LoanApplicationResponse> responses = loanApplicationService.getLoansByCustomerId(1L);

                // then
                assertEquals(1, responses.size());
        }

        // ==========================================================================
        // getLoansByStatus
        // ==========================================================================

        @Test
        void should_return_loans_by_status() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                List<LoanApplicationEntity> entities = List.of(
                        buildLoanEntity(1L, customer, "APPROVED", new BigDecimal("5000000"), 6)
                );
                when(loanApplicationRepository.findByStatus("APPROVED")).thenReturn(entities);

                // when
                List<LoanApplicationResponse> responses = loanApplicationService.getLoansByStatus("APPROVED");

                // then
                assertEquals(1, responses.size());
                assertEquals("APPROVED", responses.get(0).getStatus());
        }

        // ==========================================================================
        // getAllLoanApplicationsPaged
        // ==========================================================================

        @Test
        void should_return_paged_loans_without_status_filter() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                Page<LoanApplicationEntity> page = new PageImpl<>(List.of(
                        buildLoanEntity(1L, customer, "SUBMITTED", new BigDecimal("5000000"), 6)
                ));
                when(loanApplicationRepository.findAll(any(Pageable.class))).thenReturn(page);

                // when — status null → branch findAll(pageable)
                Page<LoanApplicationResponse> result = loanApplicationService
                        .getAllLoanApplicationsPaged(0, 10, null);

                // then
                assertEquals(1, result.getTotalElements());
        }

        @Test
        void should_return_paged_loans_with_status_filter() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                Page<LoanApplicationEntity> page = new PageImpl<>(List.of(
                        buildLoanEntity(1L, customer, "APPROVED", new BigDecimal("5000000"), 6)
                ));
                when(loanApplicationRepository.findByStatus(eq("APPROVED"), any(Pageable.class))).thenReturn(page);

                // when — status "APPROVED" → branch findByStatus(status, pageable)
                Page<LoanApplicationResponse> result = loanApplicationService
                        .getAllLoanApplicationsPaged(0, 10, "APPROVED");

                // then
                assertEquals(1, result.getTotalElements());
                assertEquals("APPROVED", result.getContent().get(0).getStatus());
        }

        // ==========================================================================
        // updateLoanStatus — semua branch logStatusChangeEvent + validateStatusTransition
        // ==========================================================================

        @Test
        void should_approve_loan_successfully() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(1L, customer, "SUBMITTED",
                        new BigDecimal("10000000"), 12);
                when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(entity));

                // PERBAIKAN: pakai thenAnswer agar entity yang dikembalikan
                // adalah entity yang sudah di-mutasi service (statusnya sudah APPROVED)
                // thenReturn(entity) tidak cukup karena entity.status masih "SUBMITTED" saat di-return
                when(loanApplicationRepository.save(any()))
                        .thenAnswer(invocation -> invocation.getArgument(0));

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("APPROVED");

                // when
                LoanApplicationResponse response = loanApplicationService.updateLoanStatus(1L, request);

                // then — cover case "APPROVED" di logStatusChangeEvent (line 144)
                assertEquals("APPROVED", response.getStatus());
                verify(loanApplicationRepository, times(1)).save(entity);
        }

        @Test
        void should_reject_loan_successfully() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(1L, customer, "SUBMITTED",
                        new BigDecimal("10000000"), 12);
                when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(entity));
                when(loanApplicationRepository.save(any()))
                        .thenAnswer(invocation -> invocation.getArgument(0));

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("REJECTED");

                // when
                LoanApplicationResponse response = loanApplicationService.updateLoanStatus(1L, request);

                // then — cover case "REJECTED" di logStatusChangeEvent
                assertEquals("REJECTED", response.getStatus());
        }

        @Test
        void should_disburse_loan_and_generate_repayment_schedules() {
                // given
                ReflectionTestUtils.setField(loanApplicationService, "annualInterestRate", 0.12);

                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(1L, customer, "APPROVED",
                        new BigDecimal("12000000"), 12);
                when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(entity));
                when(loanApplicationRepository.save(any()))
                        .thenAnswer(invocation -> invocation.getArgument(0));

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("DISBURSED");

                // when
                LoanApplicationResponse response = loanApplicationService.updateLoanStatus(1L, request);

                // then — cover case "DISBURSED" di logStatusChangeEvent + generateRepaymentSchedules
                assertEquals("DISBURSED", response.getStatus());
                verify(repaymentScheduleRepository, times(1)).saveAll(any());
        }

        @Test
        void should_close_loan_successfully() {
                // given — DISBURSED → CLOSED
                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(1L, customer, "DISBURSED",
                        new BigDecimal("12000000"), 12);
                when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(entity));
                when(loanApplicationRepository.save(any()))
                        .thenAnswer(invocation -> invocation.getArgument(0));

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("CLOSED");

                // when
                LoanApplicationResponse response = loanApplicationService.updateLoanStatus(1L, request);

                // then — cover case "CLOSED" di logStatusChangeEvent (line 157)
                assertEquals("CLOSED", response.getStatus());
        }

        @Test
        void should_throw_invalid_status_when_transitioning_from_rejected() {
                // given — cover baris pertama di validateStatusTransition:
                // "REJECTED" || "CLOSED" → langsung throw tanpa cek valid
                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(1L, customer, "REJECTED",
                        new BigDecimal("5000000"), 6);
                when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(entity));

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("APPROVED");

                // when & then
                assertThrows(InvalidLoanStatusException.class,
                        () -> loanApplicationService.updateLoanStatus(1L, request));

                verify(loanApplicationRepository, never()).save(any());
        }

        @Test
        void should_throw_invalid_status_when_transitioning_from_closed() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(1L, customer, "CLOSED",
                        new BigDecimal("5000000"), 6);
                when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(entity));

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("APPROVED");

                // when & then
                assertThrows(InvalidLoanStatusException.class,
                        () -> loanApplicationService.updateLoanStatus(1L, request));
        }

        @Test
        void should_throw_invalid_status_when_transition_order_is_wrong() {
                // given — SUBMITTED → DISBURSED tidak valid (harus lewat APPROVED dulu)
                // cover: valid=false → throw InvalidLoanStatusException (line 173)
                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(1L, customer, "SUBMITTED",
                        new BigDecimal("5000000"), 6);
                when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(entity));

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("DISBURSED");

                // when & then
                assertThrows(InvalidLoanStatusException.class,
                        () -> loanApplicationService.updateLoanStatus(1L, request));
        }

        @Test
        void should_throw_invalid_status_when_current_status_is_unknown() {
                // given — cover "default -> false" di validateStatusTransition (line 169)
                // status tidak dikenal → valid = false → throw InvalidLoanStatusException
                CustomerEntity customer = buildCustomer(1L);
                LoanApplicationEntity entity = buildLoanEntity(1L, customer, "UNKNOWN_STATUS",
                        new BigDecimal("5000000"), 6);
                when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(entity));

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("APPROVED");

                // when & then
                assertThrows(InvalidLoanStatusException.class,
                        () -> loanApplicationService.updateLoanStatus(1L, request));

                verify(loanApplicationRepository, never()).save(any());
        }

        @Test
        void should_throw_not_found_when_updating_nonexistent_loan() {
                // given
                when(loanApplicationRepository.findById(99L)).thenReturn(Optional.empty());

                UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
                request.setStatus("APPROVED");

                // when & then
                assertThrows(LoanApplicationNotFoundException.class,
                        () -> loanApplicationService.updateLoanStatus(99L, request));
        }

        // ==========================================================================
        // getLoansByDate
        // ==========================================================================

        @Test
        void should_return_loans_by_date() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                LocalDate today = LocalDate.now();
                List<LoanApplicationEntity> entities = List.of(
                        buildLoanEntity(1L, customer, "SUBMITTED", new BigDecimal("5000000"), 6)
                );
                when(loanApplicationRepository.findByCreatedAtDate(today)).thenReturn(entities);

                // when
                List<LoanApplicationResponse> responses = loanApplicationService.getLoansByDate(today);

                // then
                assertEquals(1, responses.size());
                verify(loanApplicationRepository).findByCreatedAtDate(today);
        }

        // ==========================================================================
        // getLoansByDateRange
        // ==========================================================================

        @Test
        void should_return_loans_by_date_range() {
                // given
                CustomerEntity customer = buildCustomer(1L);
                ZonedDateTime start = ZonedDateTime.now().minusDays(7);
                ZonedDateTime end = ZonedDateTime.now();
                List<LoanApplicationEntity> entities = List.of(
                        buildLoanEntity(1L, customer, "APPROVED", new BigDecimal("10000000"), 12)
                );
                when(loanApplicationRepository.findByCreatedAtBetween(start, end)).thenReturn(entities);

                // when
                List<LoanApplicationResponse> responses =
                        loanApplicationService.getLoansByDateRange(start, end);

                // then
                assertEquals(1, responses.size());
                assertEquals("APPROVED", responses.get(0).getStatus());
        }

        // ==========================================================================
        // getLoanSummaryByStatus
        // ==========================================================================

        @Test
        void should_return_loan_summary_by_status() {
                // given — simulasi native query yang return raw Object[]
                Object[] row1 = new Object[]{"SUBMITTED", 3L, new BigDecimal("15000000")};
                Object[] row2 = new Object[]{"APPROVED", 1L, new BigDecimal("10000000")};
                when(loanApplicationRepository.getSummaryByStatus()).thenReturn(List.of(row1, row2));

                // when
                List<LoanSummaryResponse> summaries = loanApplicationService.getLoanSummaryByStatus();

                // then
                assertEquals(2, summaries.size());
                assertEquals("SUBMITTED", summaries.get(0).getStatus());
                assertEquals(3L, summaries.get(0).getTotalLoan());
                assertEquals("APPROVED", summaries.get(1).getStatus());
        }

        // ==========================================================================
        // Helpers
        // ==========================================================================

        private CustomerEntity buildCustomer(Long id) {
                CustomerEntity customer = new CustomerEntity();
                customer.setId(id);
                customer.setFullName("Budi Santoso");
                customer.setNik("3273012345678901");
                customer.setEmail("budi@example.com");
                customer.setPhoneNumber("081234567890");
                return customer;
        }

        private LoanApplicationEntity buildLoanEntity(Long id, CustomerEntity customer, String status, BigDecimal amount, int tenor) {
                LoanApplicationEntity entity = new LoanApplicationEntity();
                entity.setId(id);
                entity.setCustomer(customer);
                entity.setStatus(status);
                entity.setLoanAmount(amount);
                entity.setTenorMonth(tenor);
                entity.setPurpose("Modal usaha");
                entity.setCreatedAt(ZonedDateTime.now());
                entity.setUpdatedAt(ZonedDateTime.now());
                return entity;
        }

        private CreateLoanApplicationRequest buildCreateRequest(Long customerId, BigDecimal amount, int tenor, String purpose) {
                CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
                req.setCustomerId(customerId);
                req.setLoanAmount(amount);
                req.setTenorMonth(tenor);
                req.setPurpose(purpose);
                return req;
        }
        }
