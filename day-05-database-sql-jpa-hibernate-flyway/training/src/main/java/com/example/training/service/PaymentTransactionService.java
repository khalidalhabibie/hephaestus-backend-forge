package com.example.training.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.training.dto.CreatePaymentTransactionRequest;
import com.example.training.dto.PaymentTransactionResponse;
import com.example.training.enums.PaymentStatus;
import com.example.training.entity.PaymentTransactionEntity;
import com.example.training.entity.RepaymentScheduleEntity;
import com.example.training.enums.RepaymentStatus;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.PaymentTransactionRepository;
import com.example.training.repository.RepaymentScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional
    public PaymentTransactionResponse create(CreatePaymentTransactionRequest request) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=payment_create_request schedule_id={} amount={} correlation_id={}",
                request.getRepaymentScheduleId(), request.getPaidAmount(), correlationId);

        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findById(request.getRepaymentScheduleId())
                .orElseThrow(() -> {
                    log.warn("event=payment_create_failed reason=schedule_not_found schedule_id={} correlation_id={}",
                            request.getRepaymentScheduleId(), correlationId);
                    return new NotFoundException("REPAYMENT_SCHEDULE_NOT_FOUND",
                            "Repayment schedule not found with id: " + request.getRepaymentScheduleId());
                });

        if (schedule.getStatus() == RepaymentStatus.PAID) {
            log.warn("event=payment_create_failed reason=already_paid schedule_id={} correlation_id={}",
                    request.getRepaymentScheduleId(), correlationId);
            throw new IllegalStateException("Repayment schedule is already PAID");
        }

        PaymentTransactionEntity payment = new PaymentTransactionEntity();
        payment.setRepaymentSchedule(schedule);
        payment.setPaymentReference(request.getPaymentReference());
        payment.setPaidAmount(request.getPaidAmount());
        payment.setPaidAt(request.getPaidAt());
        payment.setStatus(PaymentStatus.SUCCESS);

        PaymentTransactionEntity saved = paymentTransactionRepository.save(payment);

        paymentTransactionRepository.flush();

        BigDecimal totalPaid = paymentTransactionRepository.sumPaidAmountByScheduleId(request.getRepaymentScheduleId());
        log.debug("event=payment_total_paid_check schedule_id={} total_paid={} total_amount={} correlation_id={}",
                request.getRepaymentScheduleId(), totalPaid, schedule.getTotalAmount(), correlationId);
        if (totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
            schedule.setStatus(RepaymentStatus.PAID);
            repaymentScheduleRepository.save(schedule);

            log.info("event=repayment_schedule_paid schedule_id={} total_paid={} correlation_id={}",
                    request.getRepaymentScheduleId(), totalPaid, correlationId);
        }

        log.info("event=payment_created payment_id={} schedule_id={} amount_paid={} correlation_id={}",
                saved.getId(), request.getRepaymentScheduleId(), request.getPaidAmount(), correlationId);

        log.debug("event=payment_create_complete payment_id={} correlation_id={}", saved.getId(), correlationId);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> findByRepaymentScheduleId(UUID repaymentScheduleId) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=payment_find_by_schedule schedule_id={} correlation_id={}", repaymentScheduleId, correlationId);
        List<PaymentTransactionEntity> payments = paymentTransactionRepository.findByRepaymentSchedule_Id(repaymentScheduleId);
        log.debug("event=payment_find_by_schedule_result count={} correlation_id={}", payments.size(), correlationId);
        return payments.stream()
                .map(this::toResponse)
                .toList();
    }

    private PaymentTransactionResponse toResponse(PaymentTransactionEntity payment) {
        return PaymentTransactionResponse.builder()
                .id(payment.getId())
                .repaymentScheduleId(payment.getRepaymentSchedule().getId())
                .paymentReference(payment.getPaymentReference())
                .paidAmount(payment.getPaidAmount())
                .paidAt(payment.getPaidAt())
                .status(payment.getStatus().name())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
