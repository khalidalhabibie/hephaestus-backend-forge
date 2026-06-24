package com.example.demo.Service;

import com.example.training.DTO.CreateLoanApplicationRequest;
import com.example.training.DTO.LoanApplicationResponse;
import com.example.training.DTO.UpdateLoanStatusRequest;
import com.example.training.Entity.CustomerEntity;
import com.example.training.Entity.LoanApplicationEntity;
import com.example.training.Entity.RepaymentScheduleEntity;
import com.example.training.Exception.CustomerNotFoundException;
import com.example.training.Exception.LoanApplicationNotFoundException;
import com.example.training.Repository.CustomerRepository;
import com.example.training.Repository.LoanApplicationRepository;
import com.example.training.Repository.RepaymentScheduleRepository;
import com.example.training.Service.LoanApplicationService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoanApplicationService - 95% Coverage Target")
class LoanApplicationServiceTest {

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    private static final Long CUSTOMER_ID = 1L;
    private static final Long LOAN_ID = 1L;
    private static final Long LOAN_ID_2 = 2L;
    private static final Long NON_EXISTING_ID = 999L;
    private static final BigDecimal LOAN_AMOUNT = new BigDecimal("10000000");
    private static final BigDecimal SMALL_LOAN_AMOUNT = new BigDecimal("1200000");
    private static final Integer TENOR_12 = 12;
    private static final String PURPOSE = "Business Expansion";

    private CustomerEntity buildCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(CUSTOMER_ID);
        customer.setFullName("John Doe");
        customer.setNik("1234567890123456");
        customer.setEmail("john@example.com");
        return customer;
    }

    private LoanApplicationEntity buildLoan(Long id, LoanApplicationEntity.LoanStatus status) {
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setCustomer(buildCustomer());
        loan.setLoanAmount(LOAN_AMOUNT);
        loan.setTenorMonth(TENOR_12);
        loan.setPurpose(PURPOSE);
        loan.setStatus(status);
        loan.setCreatedAt(ZonedDateTime.now());
        loan.setUpdatedAt(ZonedDateTime.now());
        return loan;
    }

    private CreateLoanApplicationRequest buildCreateRequest() {
        CreateLoanApplicationRequest request = new CreateLoanApplicationRequest();
        request.setCustomerId(CUSTOMER_ID);
        request.setLoanAmount(LOAN_AMOUNT);
        request.setTenorMonth(TENOR_12);
        request.setPurpose(PURPOSE);
        return request;
    }

    private UpdateLoanStatusRequest buildUpdateRequest(String status) {
        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
        request.setStatus(status);
        return request;
    }

    @Nested
    @DisplayName("create()")
    class CreateTests {

        @Test
        @DisplayName("Valid request → create with SUBMITTED status")
        void validRequest_shouldCreateWithSubmittedStatus() {
            CreateLoanApplicationRequest request = buildCreateRequest();
            LoanApplicationEntity savedLoan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);

            given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(buildCustomer()));
            given(loanApplicationRepository.save(any(LoanApplicationEntity.class))).willReturn(savedLoan);

            LoanApplicationResponse result = loanApplicationService.create(request);

            assertThat(result.getId()).isEqualTo(LOAN_ID);
            assertThat(result.getStatus()).isEqualTo("SUBMITTED");
            assertThat(result.getLoanAmount()).isEqualTo(LOAN_AMOUNT);
            assertThat(result.getTenorMonth()).isEqualTo(TENOR_12);
            assertThat(result.getPurpose()).isEqualTo(PURPOSE);
            assertThat(result.getCustomer().getFullName()).isEqualTo("John Doe");
            assertThat(result.getCreatedAt()).isNotNull();
            assertThat(result.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Non-existing customer → throw CustomerNotFoundException")
        void nonExistingCustomer_shouldThrowException() {
            CreateLoanApplicationRequest request = buildCreateRequest();
            request.setCustomerId(NON_EXISTING_ID);
            given(customerRepository.findById(NON_EXISTING_ID)).willReturn(Optional.empty());

            assertThatThrownBy(() -> loanApplicationService.create(request))
                    .isInstanceOf(CustomerNotFoundException.class)
                    .hasMessageContaining(String.valueOf(NON_EXISTING_ID));
            then(loanApplicationRepository).should(never()).save(any());
        }
    }

    @Nested
    @DisplayName("getById()")
    class GetByIdTests {

        @Test
        @DisplayName("Existing ID → return loan")
        void existingId_shouldReturnLoan() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            given(loanApplicationRepository.findByIdWithCustomer(LOAN_ID)).willReturn(Optional.of(loan));

            LoanApplicationResponse result = loanApplicationService.getById(LOAN_ID);

            assertThat(result.getId()).isEqualTo(LOAN_ID);
            assertThat(result.getStatus()).isEqualTo("SUBMITTED");
        }

        @Test
        @DisplayName("Non-existing ID → throw LoanApplicationNotFoundException")
        void nonExistingId_shouldThrowException() {
            given(loanApplicationRepository.findByIdWithCustomer(NON_EXISTING_ID)).willReturn(Optional.empty());

            assertThatThrownBy(() -> loanApplicationService.getById(NON_EXISTING_ID))
                    .isInstanceOf(LoanApplicationNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("getAll()")
    class GetAllTests {

        @Test
        @DisplayName("Loans exist → return list")
        void loansExist_shouldReturnList() {
            LoanApplicationEntity loan1 = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            LoanApplicationEntity loan2 = buildLoan(LOAN_ID_2, LoanApplicationEntity.LoanStatus.APPROVED);
            given(loanApplicationRepository.findAll()).willReturn(List.of(loan1, loan2));

            List<LoanApplicationResponse> result = loanApplicationService.getAll();

            assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("Empty database → return empty list")
        void emptyDatabase_shouldReturnEmptyList() {
            given(loanApplicationRepository.findAll()).willReturn(Collections.emptyList());

            List<LoanApplicationResponse> result = loanApplicationService.getAll();

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("getAllPaginated()")
    class GetAllPaginatedTests {

        @Test
        @DisplayName("Null status and dates → use defaults and return page")
        void nullStatusAndDates_shouldUseDefaultsAndReturnPage() {
            Pageable pageable = PageRequest.of(0, 10);
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            Page<LoanApplicationEntity> page = new PageImpl<>(List.of(loan));

            given(loanApplicationRepository.findAllWithFilters(
                    isNull(), any(ZonedDateTime.class), any(ZonedDateTime.class), eq(pageable)))
                    .willReturn(page);

            Page<LoanApplicationResponse> result = loanApplicationService.getAllPaginated(null, null, null, pageable);

            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getStatus()).isEqualTo("SUBMITTED");
        }

        @Test
        @DisplayName("Valid status and explicit dates → return filtered page")
        void validStatusAndDates_shouldReturnFilteredPage() {
            Pageable pageable = PageRequest.of(0, 10);
            ZonedDateTime start = ZonedDateTime.now().minusDays(7);
            ZonedDateTime end = ZonedDateTime.now();
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.APPROVED);
            Page<LoanApplicationEntity> page = new PageImpl<>(List.of(loan));

            given(loanApplicationRepository.findAllWithFilters(
                    eq(LoanApplicationEntity.LoanStatus.APPROVED), eq(start), eq(end), eq(pageable)))
                    .willReturn(page);

            Page<LoanApplicationResponse> result = loanApplicationService.getAllPaginated("APPROVED", start, end, pageable);

            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getStatus()).isEqualTo("APPROVED");
        }

        @Test
        @DisplayName("Blank status → treated as null")
        void blankStatus_shouldTreatAsNull() {
            Pageable pageable = PageRequest.of(0, 10);
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            Page<LoanApplicationEntity> page = new PageImpl<>(List.of(loan));

            given(loanApplicationRepository.findAllWithFilters(
                    isNull(), any(ZonedDateTime.class), any(ZonedDateTime.class), eq(pageable)))
                    .willReturn(page);

            Page<LoanApplicationResponse> result = loanApplicationService.getAllPaginated("   ", null, null, pageable);

            assertThat(result.getContent()).hasSize(1);
        }

        @Test
        @DisplayName("Invalid status → throw IllegalArgumentException")
        void invalidStatus_shouldThrowException() {
            Pageable pageable = PageRequest.of(0, 10);

            assertThatThrownBy(() -> loanApplicationService.getAllPaginated("INVALID", null, null, pageable))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Invalid loan status");
        }
    }

    @Nested
    @DisplayName("getByCustomerId()")
    class GetByCustomerIdTests {

        @Test
        @DisplayName("Existing customer with loans → return loans")
        void existingCustomerWithLoans_shouldReturnLoans() {
            given(customerRepository.existsById(CUSTOMER_ID)).willReturn(true);
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            given(loanApplicationRepository.findLoansByCustomerId(CUSTOMER_ID)).willReturn(List.of(loan));

            List<LoanApplicationResponse> result = loanApplicationService.getByCustomerId(CUSTOMER_ID);

            assertThat(result).hasSize(1);
        }

        @Test
        @DisplayName("Existing customer without loans → return empty list")
        void existingCustomerWithoutLoans_shouldReturnEmptyList() {
            given(customerRepository.existsById(CUSTOMER_ID)).willReturn(true);
            given(loanApplicationRepository.findLoansByCustomerId(CUSTOMER_ID)).willReturn(Collections.emptyList());

            List<LoanApplicationResponse> result = loanApplicationService.getByCustomerId(CUSTOMER_ID);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Non-existing customer → throw CustomerNotFoundException")
        void nonExistingCustomer_shouldThrowException() {
            given(customerRepository.existsById(NON_EXISTING_ID)).willReturn(false);

            assertThatThrownBy(() -> loanApplicationService.getByCustomerId(NON_EXISTING_ID))
                    .isInstanceOf(CustomerNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("getByStatus()")
    class GetByStatusTests {

        @Test
        @DisplayName("Valid status 'SUBMITTED' → return matching loans")
        void validStatus_shouldReturnMatchingLoans() {
            String status = "SUBMITTED";
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            given(loanApplicationRepository.findByStatus(status)).willReturn(List.of(loan));

            List<LoanApplicationResponse> result = loanApplicationService.getByStatus(status);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getStatus()).isEqualTo("SUBMITTED");
        }

        @Test
        @DisplayName("Valid status lowercase 'submitted' → case insensitive")
        void validStatusLowercase_shouldWork() {
            String status = "submitted";
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            given(loanApplicationRepository.findByStatus("SUBMITTED")).willReturn(List.of(loan));

            List<LoanApplicationResponse> result = loanApplicationService.getByStatus(status);

            assertThat(result).hasSize(1);
        }

        @Test
        @DisplayName("Invalid status 'INVALID' → throw IllegalArgumentException")
        void invalidStatus_shouldThrowException() {
            assertThatThrownBy(() -> loanApplicationService.getByStatus("INVALID"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Invalid loan status");
        }

        @Test
        @DisplayName("Null status → throw NullPointerException")
        void nullStatus_shouldThrowException() {
            assertThatThrownBy(() -> loanApplicationService.getByStatus(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("updateStatus()")
    class UpdateStatusTests {

        @Test
        @DisplayName("SUBMITTED → APPROVED → success")
        void submittedToApproved_shouldSucceed() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));
            given(loanApplicationRepository.save(any())).willReturn(loan);

            LoanApplicationResponse result = loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("APPROVED"));

            assertThat(result.getStatus()).isEqualTo("APPROVED");
        }

        @Test
        @DisplayName("SUBMITTED → REJECTED → success")
        void submittedToRejected_shouldSucceed() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));
            given(loanApplicationRepository.save(any())).willReturn(loan);

            LoanApplicationResponse result = loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("REJECTED"));

            assertThat(result.getStatus()).isEqualTo("REJECTED");
        }

        @Test
        @DisplayName("SUBMITTED → CLOSED → throw IllegalArgumentException")
        void submittedToClosed_shouldThrowException() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));

            assertThatThrownBy(() -> loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("CLOSED")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("SUBMITTED can only be changed to APPROVED or REJECTED");
        }

        @Test
        @DisplayName("APPROVED → DISBURSED → generate repayment schedules")
        void approvedToDisbursed_shouldGenerateSchedules() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.APPROVED);
            loan.setLoanAmount(SMALL_LOAN_AMOUNT);
            loan.setTenorMonth(12);
            loan.setRepaymentSchedules(Collections.emptyList());
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));
            given(loanApplicationRepository.save(any())).willReturn(loan);
            given(repaymentScheduleRepository.save(any(RepaymentScheduleEntity.class))).willReturn(new RepaymentScheduleEntity());

            LoanApplicationResponse result = loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("DISBURSED"));

            assertThat(result.getStatus()).isEqualTo("DISBURSED");
            then(repaymentScheduleRepository).should(times(12)).save(any(RepaymentScheduleEntity.class));
        }

        @Test
        @DisplayName("APPROVED → DISBURSED with existing schedules → do not regenerate")
        void approvedToDisbursedWithExistingSchedules_shouldNotRegenerate() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.APPROVED);
            loan.setRepaymentSchedules(List.of(new RepaymentScheduleEntity()));
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));
            given(loanApplicationRepository.save(any())).willReturn(loan);

            LoanApplicationResponse result = loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("DISBURSED"));

            assertThat(result.getStatus()).isEqualTo("DISBURSED");
            then(repaymentScheduleRepository).should(never()).save(any(RepaymentScheduleEntity.class));
        }

        @Test
        @DisplayName("APPROVED → SUBMITTED → throw IllegalArgumentException")
        void approvedToSubmitted_shouldThrowException() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.APPROVED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));

            assertThatThrownBy(() -> loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("SUBMITTED")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("APPROVED can only be changed to DISBURSED");
        }

        @Test
        @DisplayName("DISBURSED → CLOSED with unpaid schedules → throw IllegalArgumentException")
        void disbursedToClosedWithUnpaid_shouldThrowException() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.DISBURSED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));
            given(repaymentScheduleRepository.countByLoanIdAndStatusNotPaid(LOAN_ID)).willReturn(2L);

            assertThatThrownBy(() -> loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("CLOSED")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Cannot close loan. All repayment schedules must be PAID first.");
        }

        @Test
        @DisplayName("DISBURSED → CLOSED with all paid → success")
        void disbursedToClosedAllPaid_shouldSucceed() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.DISBURSED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));
            given(repaymentScheduleRepository.countByLoanIdAndStatusNotPaid(LOAN_ID)).willReturn(0L);
            given(loanApplicationRepository.save(any())).willReturn(loan);

            LoanApplicationResponse result = loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("CLOSED"));

            assertThat(result.getStatus()).isEqualTo("CLOSED");
        }

        @Test
        @DisplayName("DISBURSED → APPROVED → throw IllegalArgumentException")
        void disbursedToApproved_shouldThrowException() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.DISBURSED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));

            assertThatThrownBy(() -> loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("APPROVED")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("DISBURSED can only be changed to CLOSED");
        }

        @Test
        @DisplayName("REJECTED → APPROVED → throw IllegalArgumentException (final status)")
        void rejectedToApproved_shouldThrowException() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.REJECTED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));

            assertThatThrownBy(() -> loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("APPROVED")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("REJECTED is a final status");
        }

        @Test
        @DisplayName("CLOSED → APPROVED → throw IllegalArgumentException (final status)")
        void closedToApproved_shouldThrowException() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.CLOSED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));

            assertThatThrownBy(() -> loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("APPROVED")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("CLOSED is a final status");
        }

        @Test
        @DisplayName("Invalid status string → throw IllegalArgumentException")
        void invalidStatusString_shouldThrowException() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            given(loanApplicationRepository.findById(LOAN_ID)).willReturn(Optional.of(loan));

            assertThatThrownBy(() -> loanApplicationService.updateStatus(LOAN_ID, buildUpdateRequest("UNKNOWN")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Invalid status");
        }

        @Test
        @DisplayName("Non-existing loan ID → throw LoanApplicationNotFoundException")
        void nonExistingLoanId_shouldThrowException() {
            given(loanApplicationRepository.findById(NON_EXISTING_ID)).willReturn(Optional.empty());

            assertThatThrownBy(() -> loanApplicationService.updateStatus(NON_EXISTING_ID, buildUpdateRequest("APPROVED")))
                    .isInstanceOf(LoanApplicationNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("getSummaryByStatus()")
    class SummaryTests {

        @Test
        @DisplayName("With data → return summary list")
        void withData_shouldReturnSummary() {
            Object[] row1 = new Object[]{"SUBMITTED", 5L, new BigDecimal("50000000")};
            Object[] row2 = new Object[]{"APPROVED", 3L, new BigDecimal("30000000")};
            Object[] row3 = new Object[]{"DISBURSED", 2L, new BigDecimal("20000000")};
            given(loanApplicationRepository.getSummaryByStatusRaw()).willReturn(List.of(row1, row2, row3));

            List<Map<String, Object>> result = loanApplicationService.getSummaryByStatus();

            assertThat(result).hasSize(3);
            assertThat(result.get(0).get("status")).isEqualTo("SUBMITTED");
            assertThat(result.get(0).get("total_loan")).isEqualTo(5L);
            assertThat(result.get(0).get("total_amount")).isEqualTo(new BigDecimal("50000000"));
        }

        @Test
        @DisplayName("Empty data → return empty list")
        void emptyData_shouldReturnEmptyList() {
            given(loanApplicationRepository.getSummaryByStatusRaw()).willReturn(Collections.emptyList());

            List<Map<String, Object>> result = loanApplicationService.getSummaryByStatus();

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("getOutstandingPerCustomer()")
    class OutstandingTests {

        @Test
        @DisplayName("With data → return outstanding list")
        void withData_shouldReturnOutstanding() {
            Object[] row1 = new Object[]{1L, "John Doe", new BigDecimal("8000000")};
            Object[] row2 = new Object[]{2L, "Jane Doe", new BigDecimal("5000000")};
            given(loanApplicationRepository.getOutstandingPerCustomerRaw()).willReturn(List.of(row1, row2));

            List<Map<String, Object>> result = loanApplicationService.getOutstandingPerCustomer();

            assertThat(result).hasSize(2);
            assertThat(result.get(0).get("customer_id")).isEqualTo(1L);
            assertThat(result.get(0).get("customer_name")).isEqualTo("John Doe");
            assertThat(result.get(0).get("outstanding_amount")).isEqualTo(new BigDecimal("8000000"));
        }

        @Test
        @DisplayName("Empty data → return empty list")
        void emptyData_shouldReturnEmptyList() {
            given(loanApplicationRepository.getOutstandingPerCustomerRaw()).willReturn(Collections.emptyList());

            List<Map<String, Object>> result = loanApplicationService.getOutstandingPerCustomer();

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("mapToResponse() edge cases")
    class MapToResponseTests {

        @Test
        @DisplayName("Loan with null status → response status is null")
        void nullStatus_shouldReturnNullStatus() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, null);
            given(loanApplicationRepository.findByIdWithCustomer(LOAN_ID)).willReturn(Optional.of(loan));

            LoanApplicationResponse result = loanApplicationService.getById(LOAN_ID);

            assertThat(result.getStatus()).isNull();
        }

        @Test
        @DisplayName("Loan with null customer → customer summary is null")
        void nullCustomer_shouldReturnNullCustomer() {
            LoanApplicationEntity loan = buildLoan(LOAN_ID, LoanApplicationEntity.LoanStatus.SUBMITTED);
            loan.setCustomer(null);
            given(loanApplicationRepository.findByIdWithCustomer(LOAN_ID)).willReturn(Optional.of(loan));

            LoanApplicationResponse result = loanApplicationService.getById(LOAN_ID);

            assertThat(result.getCustomer()).isNull();
        }
    }
}