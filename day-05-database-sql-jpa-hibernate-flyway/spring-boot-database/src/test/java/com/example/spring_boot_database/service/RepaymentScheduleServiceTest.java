package com.example.spring_boot_database.service;

import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.entity.LoanApplicationEntity;
import com.example.spring_boot_database.entity.PaymentTransactionEntity;
import com.example.spring_boot_database.entity.RepaymentScheduleEntity;
import com.example.spring_boot_database.entity.StatusRepayment;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.exception.RepaymentScheduleNotFoundException;
import com.example.spring_boot_database.repository.PaymentTransactionRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepaymentScheduleServiceTest {

    @Mock
    private PaymentTransactionRepository paymentRepo;

    @Mock
    private RepaymentScheduleRepository scheduleRepo;

    @InjectMocks
    private RepaymentScheduleService service;

    private static final BigDecimal ANNUAL_RATE = new BigDecimal("0.12");

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "annualRate", ANNUAL_RATE);
    }

    private LoanApplicationEntity createLoanApplication(
            Long id,
            BigDecimal loanAmount,
            Integer tenorMonth
    ) {
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(id);
        loan.setLoanAmount(loanAmount);
        loan.setTenorMonth(tenorMonth);
        return loan;
    }

    private RepaymentScheduleEntity createRepaymentSchedule(
            Long id,
            Integer installmentNumber,
            LocalDate dueDate,
            BigDecimal principalAmount,
            BigDecimal interestAmount,
            BigDecimal totalAmount,
            String status
    ) {
        RepaymentScheduleEntity entity = new RepaymentScheduleEntity();
        entity.setId(id);
        entity.setInstallmentNumber(installmentNumber);
        entity.setDueDate(dueDate);
        entity.setPrincipalAmount(principalAmount);
        entity.setInterestAmount(interestAmount);
        entity.setTotalAmount(totalAmount);
        entity.setStatus(status);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setLoanApplication(createLoanApplication(100L, new BigDecimal("12000000.00"), 12));
        return entity;
    }

    private PaymentTransactionEntity createPaymentTransaction(
            Long repaymentScheduleId,
            BigDecimal paidAmount,
            String paymentReference,
            LocalDateTime paidAt
    ) {
        RepaymentScheduleEntity schedule = createRepaymentSchedule(
                repaymentScheduleId,
                1,
                LocalDate.now().plusMonths(1),
                new BigDecimal("1000000.00"),
                new BigDecimal("120000.00"),
                new BigDecimal("1120000.00"),
                StatusRepayment.UNPAID.name()
        );

        PaymentTransactionEntity entity = new PaymentTransactionEntity();
        entity.setRepaymentSchedule(schedule);
        entity.setPaidAmount(paidAmount);
        entity.setPaymentReference(paymentReference);
        entity.setPaidAt(paidAt);
        entity.setStatus(StatusRepayment.PAID.name());
        return entity;
    }

    @Nested
    @DisplayName("generateRepaymentSchedule")
    class GenerateRepaymentScheduleTest {

        @Test
        @DisplayName("Should generate repayment schedules successfully")
        void testGenerateRepaymentSchedule_success() {
            LoanApplicationEntity loan = createLoanApplication(
                    1L,
                    new BigDecimal("12000000.00"),
                    12
            );

            LocalDate beforeExecution = LocalDate.now();

            List<RepaymentScheduleEntity> result = service.generateRepaymentSchedule(loan);

            LocalDate afterExecution = LocalDate.now();

            assertNotNull(result);
            assertEquals(12, result.size());

            for (int i = 0; i < result.size(); i++) {
                RepaymentScheduleEntity schedule = result.get(i);

                assertSame(loan, schedule.getLoanApplication());
                assertEquals(i + 1, schedule.getInstallmentNumber());

                LocalDate expectedMinDate = beforeExecution.plusMonths(i + 1);
                LocalDate expectedMaxDate = afterExecution.plusMonths(i + 1);

                assertFalse(schedule.getDueDate().isBefore(expectedMinDate));
                assertFalse(schedule.getDueDate().isAfter(expectedMaxDate));

                assertEquals(new BigDecimal("1000000.00"), schedule.getPrincipalAmount());
                assertEquals(new BigDecimal("120000.00"), schedule.getInterestAmount());
                assertEquals(new BigDecimal("1120000.00"), schedule.getTotalAmount());
                assertEquals(StatusRepayment.UNPAID.name(), schedule.getStatus());
            }

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should round principal amount correctly")
        void testGenerateRepaymentSchedule_roundPrincipalAmount() {
            LoanApplicationEntity loan = createLoanApplication(
                    1L,
                    new BigDecimal("10000000.00"),
                    3
            );

            List<RepaymentScheduleEntity> result = service.generateRepaymentSchedule(loan);

            assertEquals(3, result.size());

            for (RepaymentScheduleEntity schedule : result) {
                assertEquals(new BigDecimal("3333333.33"), schedule.getPrincipalAmount());
                assertEquals(new BigDecimal("100000.00"), schedule.getInterestAmount());
                assertEquals(new BigDecimal("3433333.33"), schedule.getTotalAmount());
                assertEquals(StatusRepayment.UNPAID.name(), schedule.getStatus());
            }
        }

        @Test
        @DisplayName("Should return empty list when tenor is negative")
        void testGenerateRepaymentSchedule_negativeTenor_returnEmptyList() {
            LoanApplicationEntity loan = createLoanApplication(
                    1L,
                    new BigDecimal("12000000.00"),
                    -1
            );

            List<RepaymentScheduleEntity> result = service.generateRepaymentSchedule(loan);

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw ArithmeticException when tenor is zero")
        void testGenerateRepaymentSchedule_zeroTenor_throwArithmeticException() {
            LoanApplicationEntity loan = createLoanApplication(
                    1L,
                    new BigDecimal("12000000.00"),
                    0
            );

            assertThrows(
                    ArithmeticException.class,
                    () -> service.generateRepaymentSchedule(loan)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw NullPointerException when loan is null")
        void testGenerateRepaymentSchedule_nullLoan_throwNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> service.generateRepaymentSchedule(null)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw NullPointerException when loan amount is null")
        void testGenerateRepaymentSchedule_nullLoanAmount_throwNullPointerException() {
            LoanApplicationEntity loan = createLoanApplication(
                    1L,
                    null,
                    12
            );

            assertThrows(
                    NullPointerException.class,
                    () -> service.generateRepaymentSchedule(loan)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw NullPointerException when annual rate is null")
        void testGenerateRepaymentSchedule_nullAnnualRate_throwNullPointerException() {
            ReflectionTestUtils.setField(service, "annualRate", null);

            LoanApplicationEntity loan = createLoanApplication(
                    1L,
                    new BigDecimal("12000000.00"),
                    12
            );

            assertThrows(
                    NullPointerException.class,
                    () -> service.generateRepaymentSchedule(loan)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTest {

        @Test
        @DisplayName("Should return repayment schedule response when id exists")
        void testFindById_success() {
            Long id = 1L;

            RepaymentScheduleEntity entity = createRepaymentSchedule(
                    id,
                    1,
                    LocalDate.of(2026, 7, 19),
                    new BigDecimal("1000000.00"),
                    new BigDecimal("120000.00"),
                    new BigDecimal("1120000.00"),
                    StatusRepayment.UNPAID.name()
            );

            when(scheduleRepo.findById(id)).thenReturn(Optional.of(entity));

            RepaymentScheduleResponse response = service.findById(id);

            assertNotNull(response);
            assertEquals(id, response.getId());
            assertEquals(1, response.getInstallmentNumber());
            assertEquals(LocalDate.of(2026, 7, 19), response.getDueDate());
            assertEquals(new BigDecimal("1000000.00"), response.getPrincipalAmount());
            assertEquals(new BigDecimal("120000.00"), response.getInterestAmount());
            assertEquals(new BigDecimal("1120000.00"), response.getTotalAmount());
            assertEquals(StatusRepayment.UNPAID, response.getStatus());

            verify(scheduleRepo).findById(id);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw RepaymentScheduleNotFoundException when id does not exist")
        void testFindById_notFound_throwException() {
            Long id = 999L;

            when(scheduleRepo.findById(id)).thenReturn(Optional.empty());

            assertThrows(
                    RepaymentScheduleNotFoundException.class,
                    () -> service.findById(id)
            );

            verify(scheduleRepo).findById(id);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should propagate repository exception")
        void testFindById_repositoryThrowsException() {
            Long id = 1L;

            when(scheduleRepo.findById(id)).thenThrow(new RuntimeException("Database error"));

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> service.findById(id)
            );

            assertEquals("Database error", exception.getMessage());

            verify(scheduleRepo).findById(id);
            verifyNoInteractions(paymentRepo);
        }
    }

    @Nested
    @DisplayName("findAll")
    class FindAllTest {

        @Test
        @DisplayName("Should return all schedules when status is null")
        void testFindAll_statusNull_success() {
            List<RepaymentScheduleEntity> entities = List.of(
                    createRepaymentSchedule(
                            1L,
                            1,
                            LocalDate.of(2026, 7, 19),
                            new BigDecimal("1000000.00"),
                            new BigDecimal("120000.00"),
                            new BigDecimal("1120000.00"),
                            StatusRepayment.UNPAID.name()
                    ),
                    createRepaymentSchedule(
                            2L,
                            2,
                            LocalDate.of(2026, 8, 19),
                            new BigDecimal("1000000.00"),
                            new BigDecimal("120000.00"),
                            new BigDecimal("1120000.00"),
                            StatusRepayment.PAID.name()
                    )
            );

            when(scheduleRepo.findAll()).thenReturn(entities);

            List<RepaymentScheduleResponse> result = service.findAll(null);

            assertNotNull(result);
            assertEquals(2, result.size());

            assertEquals(1L, result.get(0).getId());
            assertEquals(StatusRepayment.UNPAID, result.get(0).getStatus());

            assertEquals(2L, result.get(1).getId());
            assertEquals(StatusRepayment.PAID, result.get(1).getStatus());

            verify(scheduleRepo).findAll();
            verify(scheduleRepo, never()).findByStatusIgnoreCase(anyString());
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should return all schedules when status is blank")
        void testFindAll_statusBlank_success() {
            when(scheduleRepo.findAll()).thenReturn(List.of());

            List<RepaymentScheduleResponse> result = service.findAll("   ");

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(scheduleRepo).findAll();
            verify(scheduleRepo, never()).findByStatusIgnoreCase(anyString());
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should filter schedules by PAID status")
        void testFindAll_statusPaid_success() {
            RepaymentScheduleEntity entity = createRepaymentSchedule(
                    1L,
                    1,
                    LocalDate.of(2026, 7, 19),
                    new BigDecimal("1000000.00"),
                    new BigDecimal("120000.00"),
                    new BigDecimal("1120000.00"),
                    StatusRepayment.PAID.name()
            );

            when(scheduleRepo.findByStatusIgnoreCase(StatusRepayment.PAID.name()))
                    .thenReturn(List.of(entity));

            List<RepaymentScheduleResponse> result = service.findAll("PAID");

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(StatusRepayment.PAID, result.get(0).getStatus());

            verify(scheduleRepo).findByStatusIgnoreCase(StatusRepayment.PAID.name());
            verify(scheduleRepo, never()).findAll();
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should filter schedules by UNPAID status with lowercase input")
        void testFindAll_statusUnpaidLowercase_success() {
            RepaymentScheduleEntity entity = createRepaymentSchedule(
                    1L,
                    1,
                    LocalDate.of(2026, 7, 19),
                    new BigDecimal("1000000.00"),
                    new BigDecimal("120000.00"),
                    new BigDecimal("1120000.00"),
                    StatusRepayment.UNPAID.name()
            );

            when(scheduleRepo.findByStatusIgnoreCase(StatusRepayment.UNPAID.name()))
                    .thenReturn(List.of(entity));

            List<RepaymentScheduleResponse> result = service.findAll("unpaid");

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(StatusRepayment.UNPAID, result.get(0).getStatus());

            verify(scheduleRepo).findByStatusIgnoreCase(StatusRepayment.UNPAID.name());
            verify(scheduleRepo, never()).findAll();
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw BadRequestException when status is invalid")
        void testFindAll_invalidStatus_throwBadRequestException() {
            BadRequestException exception = assertThrows(
                    BadRequestException.class,
                    () -> service.findAll("PENDING")
            );

            assertEquals(
                    "Invalid repayment status. Allowed values: PAID, UNPAID",
                    exception.getMessage()
            );

            verify(scheduleRepo, never()).findAll();
            verify(scheduleRepo, never()).findByStatusIgnoreCase(anyString());
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should propagate repository exception from findAll")
        void testFindAll_repositoryThrowsException() {
            when(scheduleRepo.findAll()).thenThrow(new RuntimeException("Database error"));

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> service.findAll(null)
            );

            assertEquals("Database error", exception.getMessage());

            verify(scheduleRepo).findAll();
            verify(scheduleRepo, never()).findByStatusIgnoreCase(anyString());
            verifyNoInteractions(paymentRepo);
        }
    }

    @Nested
    @DisplayName("findPaymentTransactionByRepaymentId")
    class FindPaymentTransactionByRepaymentIdTest {

        @Test
        @DisplayName("Should return payment transactions by repayment schedule id")
        void testFindPaymentTransactionByRepaymentId_success() {
            Long repaymentId = 1L;
            LocalDateTime paidAt = LocalDateTime.of(2026, 7, 20, 10, 30);

            PaymentTransactionEntity transaction = createPaymentTransaction(
                    repaymentId,
                    new BigDecimal("1120000.00"),
                    "PAY-001",
                    paidAt
            );

            when(paymentRepo.findByRepaymentScheduleId(repaymentId))
                    .thenReturn(List.of(transaction));

            List<PaymentTransactionResponse> result =
                    service.findPaymentTransactionByRepaymentId(repaymentId);

            assertNotNull(result);
            assertEquals(1, result.size());

            PaymentTransactionResponse response = result.get(0);

            assertEquals(repaymentId, response.getRepaymentScheduleId());
            assertEquals(new BigDecimal("1120000.00"), response.getPaidAmount());
            assertEquals("PAY-001", response.getPaymentReference());
            assertEquals(paidAt, response.getPaidAt());

            verify(paymentRepo).findByRepaymentScheduleId(repaymentId);
            verifyNoInteractions(scheduleRepo);
        }

        @Test
        @DisplayName("Should return empty list when no payment transaction exists")
        void testFindPaymentTransactionByRepaymentId_empty_success() {
            Long repaymentId = 1L;

            when(paymentRepo.findByRepaymentScheduleId(repaymentId))
                    .thenReturn(List.of());

            List<PaymentTransactionResponse> result =
                    service.findPaymentTransactionByRepaymentId(repaymentId);

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(paymentRepo).findByRepaymentScheduleId(repaymentId);
            verifyNoInteractions(scheduleRepo);
        }

        @Test
        @DisplayName("Should propagate repository exception")
        void testFindPaymentTransactionByRepaymentId_repositoryThrowsException() {
            Long repaymentId = 1L;

            when(paymentRepo.findByRepaymentScheduleId(repaymentId))
                    .thenThrow(new RuntimeException("Database error"));

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> service.findPaymentTransactionByRepaymentId(repaymentId)
            );

            assertEquals("Database error", exception.getMessage());

            verify(paymentRepo).findByRepaymentScheduleId(repaymentId);
            verifyNoInteractions(scheduleRepo);
        }
    }

    @Nested
    @DisplayName("findByLoanId")
    class FindByLoanIdTest {

        @Test
        @DisplayName("Should return schedules by loan id")
        void testFindByLoanId_success() {
            Long loanId = 100L;

            RepaymentScheduleEntity entity = createRepaymentSchedule(
                    1L,
                    1,
                    LocalDate.of(2026, 7, 19),
                    new BigDecimal("1000000.00"),
                    new BigDecimal("120000.00"),
                    new BigDecimal("1120000.00"),
                    StatusRepayment.UNPAID.name()
            );

            when(scheduleRepo.findByLoanApplicationId(loanId))
                    .thenReturn(List.of(entity));

            List<RepaymentScheduleResponse> result = service.findByLoanId(loanId);

            assertNotNull(result);
            assertEquals(1, result.size());

            RepaymentScheduleResponse response = result.get(0);

            assertEquals(1L, response.getId());
            assertEquals(1, response.getInstallmentNumber());
            assertEquals(LocalDate.of(2026, 7, 19), response.getDueDate());
            assertEquals(new BigDecimal("1000000.00"), response.getPrincipalAmount());
            assertEquals(new BigDecimal("120000.00"), response.getInterestAmount());
            assertEquals(new BigDecimal("1120000.00"), response.getTotalAmount());
            assertEquals(StatusRepayment.UNPAID, response.getStatus());

            verify(scheduleRepo).findByLoanApplicationId(loanId);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should return empty list when loan has no schedules")
        void testFindByLoanId_empty_success() {
            Long loanId = 100L;

            when(scheduleRepo.findByLoanApplicationId(loanId))
                    .thenReturn(List.of());

            List<RepaymentScheduleResponse> result = service.findByLoanId(loanId);

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(scheduleRepo).findByLoanApplicationId(loanId);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should propagate repository exception")
        void testFindByLoanId_repositoryThrowsException() {
            Long loanId = 100L;

            when(scheduleRepo.findByLoanApplicationId(loanId))
                    .thenThrow(new RuntimeException("Database error"));

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> service.findByLoanId(loanId)
            );

            assertEquals("Database error", exception.getMessage());

            verify(scheduleRepo).findByLoanApplicationId(loanId);
            verifyNoInteractions(paymentRepo);
        }
    }

    @Nested
    @DisplayName("toResponse")
    class ToResponseTest {

        @Test
        @DisplayName("Should map RepaymentScheduleEntity to RepaymentScheduleResponse")
        void testToResponse_success() {
            RepaymentScheduleEntity entity = createRepaymentSchedule(
                    1L,
                    2,
                    LocalDate.of(2026, 8, 19),
                    new BigDecimal("1000000.00"),
                    new BigDecimal("120000.00"),
                    new BigDecimal("1120000.00"),
                    StatusRepayment.PAID.name()
            );

            RepaymentScheduleResponse response = service.toResponse(entity);

            assertNotNull(response);
            assertEquals(1L, response.getId());
            assertEquals(2, response.getInstallmentNumber());
            assertEquals(LocalDate.of(2026, 8, 19), response.getDueDate());
            assertEquals(new BigDecimal("1000000.00"), response.getPrincipalAmount());
            assertEquals(new BigDecimal("120000.00"), response.getInterestAmount());
            assertEquals(new BigDecimal("1120000.00"), response.getTotalAmount());
            assertEquals(StatusRepayment.PAID, response.getStatus());

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when status is invalid")
        void testToResponse_invalidStatus_throwIllegalArgumentException() {
            RepaymentScheduleEntity entity = createRepaymentSchedule(
                    1L,
                    1,
                    LocalDate.of(2026, 7, 19),
                    new BigDecimal("1000000.00"),
                    new BigDecimal("120000.00"),
                    new BigDecimal("1120000.00"),
                    "INVALID_STATUS"
            );

            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.toResponse(entity)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw NullPointerException when entity is null")
        void testToResponse_nullEntity_throwNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> service.toResponse(null)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw NullPointerException when status is null")
        void testToResponse_nullStatus_throwNullPointerException() {
            RepaymentScheduleEntity entity = createRepaymentSchedule(
                    1L,
                    1,
                    LocalDate.of(2026, 7, 19),
                    new BigDecimal("1000000.00"),
                    new BigDecimal("120000.00"),
                    new BigDecimal("1120000.00"),
                    null
            );

            assertThrows(
                    NullPointerException.class,
                    () -> service.toResponse(entity)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }
    }

    @Nested
    @DisplayName("toPaymentResponse")
    class ToPaymentResponseTest {

        @Test
        @DisplayName("Should map PaymentTransactionEntity to PaymentTransactionResponse")
        void testToPaymentResponse_success() {
            LocalDateTime paidAt = LocalDateTime.of(2026, 7, 20, 10, 30);

            PaymentTransactionEntity entity = createPaymentTransaction(
                    1L,
                    new BigDecimal("1120000.00"),
                    "PAY-001",
                    paidAt
            );

            PaymentTransactionResponse response = service.toPaymentResponse(entity);

            assertNotNull(response);
            assertEquals(1L, response.getRepaymentScheduleId());
            assertEquals(new BigDecimal("1120000.00"), response.getPaidAmount());
            assertEquals("PAY-001", response.getPaymentReference());
            assertEquals(paidAt, response.getPaidAt());

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw NullPointerException when entity is null")
        void testToPaymentResponse_nullEntity_throwNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> service.toPaymentResponse(null)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }

        @Test
        @DisplayName("Should throw NullPointerException when repayment schedule is null")
        void testToPaymentResponse_nullRepaymentSchedule_throwNullPointerException() {
            PaymentTransactionEntity entity = new PaymentTransactionEntity();
            entity.setRepaymentSchedule(null);
            entity.setPaidAmount(new BigDecimal("1120000.00"));
            entity.setPaymentReference("PAY-001");
            entity.setPaidAt(LocalDateTime.of(2026, 7, 20, 10, 30));

            assertThrows(
                    NullPointerException.class,
                    () -> service.toPaymentResponse(entity)
            );

            verifyNoInteractions(scheduleRepo);
            verifyNoInteractions(paymentRepo);
        }
    }
}