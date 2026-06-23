package com.example.spring_boot_database.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import com.example.spring_boot_database.dto.CreatePaymentTransactionRequest;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.entity.PaymentTransactionEntity;
import com.example.spring_boot_database.entity.RepaymentScheduleEntity;
import com.example.spring_boot_database.entity.StatusRepayment;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.exception.RepaymentScheduleNotFoundException;
import com.example.spring_boot_database.repository.PaymentTransactionRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

    @Mock
    private RepaymentScheduleRepository scheduleRepo;

    @Mock
    private PaymentTransactionRepository paymentRepo;

    @InjectMocks
    private PaymentTransactionService service;

    private CreatePaymentTransactionRequest request;
    private RepaymentScheduleEntity schedule;

    private final Long scheduleId = 1L;
    private final BigDecimal paidAmount = new BigDecimal("50000");
    private final BigDecimal totalAmount = new BigDecimal("100000");
    private final String paymentReference = "PAY-REF-001";
    private final LocalDateTime paidAt = LocalDateTime.of(2026, 1, 10, 10, 30);

    @BeforeEach
    void setUp() {
        request = new CreatePaymentTransactionRequest();
        request.setRepaymentSchedule_id(scheduleId);
        request.setPaidAmount(paidAmount);
        request.setPaymentReference(paymentReference);
        request.setPaidAt(paidAt);

        schedule = new RepaymentScheduleEntity();
        schedule.setId(scheduleId);
        schedule.setTotalAmount(totalAmount);
        schedule.setStatus("UNPAID");
    }

    @Test
    void create_shouldSavePaymentAndReturnResponse_whenScheduleExistsAndNotFullyPaid() {
        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentRepo.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(new BigDecimal("50000"));

        PaymentTransactionResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(scheduleId, response.getRepaymentScheduleId());
        assertEquals(paidAmount, response.getPaidAmount());
        assertEquals(paymentReference, response.getPaymentReference());
        assertEquals(paidAt, response.getPaidAt());

        ArgumentCaptor<PaymentTransactionEntity> paymentCaptor =
                ArgumentCaptor.forClass(PaymentTransactionEntity.class);

        verify(paymentRepo).save(paymentCaptor.capture());

        PaymentTransactionEntity savedPayment = paymentCaptor.getValue();

        assertNotNull(savedPayment);
        assertEquals(schedule, savedPayment.getRepaymentSchedule());
        assertEquals(paidAmount, savedPayment.getPaidAmount());
        assertEquals(paymentReference, savedPayment.getPaymentReference());
        assertEquals(paidAt, savedPayment.getPaidAt());
        assertEquals(StatusRepayment.PAID.name(), savedPayment.getStatus());

        assertEquals("UNPAID", schedule.getStatus());
        verify(scheduleRepo, never()).save(any(RepaymentScheduleEntity.class));

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).sumPaidAmountByScheduleId(scheduleId);
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldMarkScheduleAsPaid_whenTotalPaidGreaterThanTotalAmount() {
        // Arrange
        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentRepo.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(new BigDecimal("150000"));
        when(scheduleRepo.save(any(RepaymentScheduleEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentTransactionResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(scheduleId, response.getRepaymentScheduleId());
        assertEquals(paidAmount, response.getPaidAmount());
        assertEquals(paymentReference, response.getPaymentReference());
        assertEquals(paidAt, response.getPaidAt());

        assertEquals(StatusRepayment.PAID.name(), schedule.getStatus());

        ArgumentCaptor<RepaymentScheduleEntity> scheduleCaptor =
                ArgumentCaptor.forClass(RepaymentScheduleEntity.class);

        verify(scheduleRepo).save(scheduleCaptor.capture());

        RepaymentScheduleEntity savedSchedule = scheduleCaptor.getValue();

        assertEquals(scheduleId, savedSchedule.getId());
        assertEquals(StatusRepayment.PAID.name(), savedSchedule.getStatus());

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).save(any(PaymentTransactionEntity.class));
        verify(paymentRepo).sumPaidAmountByScheduleId(scheduleId);
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldMarkScheduleAsPaid_whenTotalPaidEqualsTotalAmount() {
        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentRepo.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(new BigDecimal("100000"));
        when(scheduleRepo.save(any(RepaymentScheduleEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentTransactionResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(StatusRepayment.PAID.name(), schedule.getStatus());

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).save(any(PaymentTransactionEntity.class));
        verify(paymentRepo).sumPaidAmountByScheduleId(scheduleId);
        verify(scheduleRepo).save(schedule);
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldThrowRepaymentScheduleNotFoundException_whenScheduleDoesNotExist() {
        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.empty());

        RepaymentScheduleNotFoundException exception =
                assertThrows(
                        RepaymentScheduleNotFoundException.class,
                        () -> service.create(request)
                );

        assertNotNull(exception);

        verify(scheduleRepo).findById(scheduleId);
        verifyNoInteractions(paymentRepo);
        verifyNoMoreInteractions(scheduleRepo);
    }

    @Test
    void create_shouldThrowBadRequestException_whenScheduleAlreadyPaid() {
        schedule.setStatus(StatusRepayment.PAID.name());

        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> service.create(request)
                );

        assertEquals("Schedule already paid", exception.getMessage());

        verify(scheduleRepo).findById(scheduleId);
        verifyNoInteractions(paymentRepo);
        verifyNoMoreInteractions(scheduleRepo);
    }

    @Test
    void create_shouldPropagateException_whenPaymentSaveFails() {
        RuntimeException repositoryException =
                new RuntimeException("Database error while saving payment");

        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenThrow(repositoryException);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.create(request)
                );

        assertSame(repositoryException, exception);
        assertEquals("Database error while saving payment", exception.getMessage());

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).save(any(PaymentTransactionEntity.class));
        verify(paymentRepo, never()).sumPaidAmountByScheduleId(any());
        verify(scheduleRepo, never()).save(any());
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldPropagateException_whenSumPaidAmountFails() {
        RuntimeException repositoryException =
                new RuntimeException("Database error while calculating total paid");

        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentRepo.sumPaidAmountByScheduleId(scheduleId))
                .thenThrow(repositoryException);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.create(request)
                );

        assertSame(repositoryException, exception);
        assertEquals("Database error while calculating total paid", exception.getMessage());

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).save(any(PaymentTransactionEntity.class));
        verify(paymentRepo).sumPaidAmountByScheduleId(scheduleId);
        verify(scheduleRepo, never()).save(any());
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldPropagateException_whenScheduleSaveFailsAfterFullyPaid() {
        RuntimeException repositoryException =
                new RuntimeException("Database error while saving schedule");

        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentRepo.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(new BigDecimal("100000"));
        when(scheduleRepo.save(any(RepaymentScheduleEntity.class)))
                .thenThrow(repositoryException);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.create(request)
                );

        assertSame(repositoryException, exception);
        assertEquals("Database error while saving schedule", exception.getMessage());

        assertEquals(StatusRepayment.PAID.name(), schedule.getStatus());

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).save(any(PaymentTransactionEntity.class));
        verify(paymentRepo).sumPaidAmountByScheduleId(scheduleId);
        verify(scheduleRepo).save(schedule);
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldThrowNullPointerException_whenRequestIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> service.create(null)
        );

        verifyNoInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldThrowRepaymentScheduleNotFoundException_whenRepaymentScheduleIdIsNullAndRepositoryReturnsEmpty() {
        request.setRepaymentSchedule_id(null);

        when(scheduleRepo.findById(null)).thenReturn(Optional.empty());

        RepaymentScheduleNotFoundException exception =
                assertThrows(
                        RepaymentScheduleNotFoundException.class,
                        () -> service.create(request)
                );

        assertNotNull(exception);

        verify(scheduleRepo).findById(null);
        verifyNoInteractions(paymentRepo);
        verifyNoMoreInteractions(scheduleRepo);
    }

    @Test
    void create_shouldThrowNullPointerException_whenScheduleStatusIsNull() {
        schedule.setStatus(null);

        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));

        assertThrows(
                NullPointerException.class,
                () -> service.create(request)
        );

        verify(scheduleRepo).findById(scheduleId);
        verifyNoInteractions(paymentRepo);
        verifyNoMoreInteractions(scheduleRepo);
    }

    @Test
    void create_shouldThrowNullPointerException_whenTotalPaidIsNull() {
        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentRepo.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> service.create(request)
        );

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).save(any(PaymentTransactionEntity.class));
        verify(paymentRepo).sumPaidAmountByScheduleId(scheduleId);
        verify(scheduleRepo, never()).save(any());
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldThrowNullPointerException_whenScheduleTotalAmountIsNull() {
        schedule.setTotalAmount(null);

        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentRepo.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(new BigDecimal("100000"));

        assertThrows(
                NullPointerException.class,
                () -> service.create(request)
        );

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).save(any(PaymentTransactionEntity.class));
        verify(paymentRepo).sumPaidAmountByScheduleId(scheduleId);
        verify(scheduleRepo, never()).save(any());
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }

    @Test
    void create_shouldAllowNullPaymentFieldsBecauseServiceDoesNotValidateThem() {
        request.setPaidAmount(null);
        request.setPaymentReference(null);
        request.setPaidAt(null);

        when(scheduleRepo.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(paymentRepo.save(any(PaymentTransactionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentRepo.sumPaidAmountByScheduleId(scheduleId))
                .thenReturn(BigDecimal.ZERO);

        PaymentTransactionResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(scheduleId, response.getRepaymentScheduleId());
        assertNull(response.getPaidAmount());
        assertNull(response.getPaymentReference());
        assertNull(response.getPaidAt());

        ArgumentCaptor<PaymentTransactionEntity> paymentCaptor =
                ArgumentCaptor.forClass(PaymentTransactionEntity.class);

        verify(paymentRepo).save(paymentCaptor.capture());

        PaymentTransactionEntity savedPayment = paymentCaptor.getValue();

        assertNull(savedPayment.getPaidAmount());
        assertNull(savedPayment.getPaymentReference());
        assertNull(savedPayment.getPaidAt());
        assertEquals(StatusRepayment.PAID.name(), savedPayment.getStatus());

        verify(scheduleRepo).findById(scheduleId);
        verify(paymentRepo).sumPaidAmountByScheduleId(scheduleId);
        verify(scheduleRepo, never()).save(any());
        verifyNoMoreInteractions(scheduleRepo, paymentRepo);
    }
}