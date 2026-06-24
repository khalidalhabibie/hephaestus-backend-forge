package com.example.jpabackend.service;

import com.example.jpabackend.entity.*;
import com.example.jpabackend.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.exception.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PaymentTransactionService {
    private static final Logger log = LoggerFactory.getLogger(PaymentTransactionService.class);

    private final PaymentTransactionRepository paymentRepo;
    private final RepaymentScheduleRepository repaymentRepo;

    public PaymentTransactionService(
            PaymentTransactionRepository paymentRepo,
            RepaymentScheduleRepository repaymentRepo) {
        this.paymentRepo = paymentRepo;
        this.repaymentRepo = repaymentRepo;
    }

    // CREATE PAYMENT
    @Transactional
    public PaymentTransactionResponse createPayment(CreatePaymentTransactionRequest req) {

        RepaymentScheduleEntity schedule = repaymentRepo.findById(req.getRepaymentScheduleId())
        .orElseThrow(() -> {
                    log.warn(
                            "event=repayment_schedule_not_found schedule_id={}",
                            req.getRepaymentScheduleId());
                    return new RepaymentScheduleNotFoundException(req.getRepaymentScheduleId());
                });

        Long loanId = schedule.getLoanApplication().getId();

        // UNIQUE PER LOAN
        if (paymentRepo.existsByRepaymentSchedule_LoanApplication_IdAndPaymentReference(
                loanId,
                req.getPaymentReference())) {

            log.warn(
                    "event=validation_error error=duplicate_payment_reference loan_id={} reference={}",
                    loanId,
                    req.getPaymentReference());

            throw new RuntimeException("PAYMENT_REFERENCE_ALREADY_EXISTS_FOR_LOAN");
        }

        // ALREADY PAID
        if (schedule.getStatus().equals("PAID")) {

            log.warn(
                    "event=validation_error error=schedule_already_paid schedule_id={}",
                    schedule.getId());

            throw new RuntimeException("SCHEDULE_ALREADY_PAID");
        }

        // EXACT AMOUNT
        if (req.getPaidAmount().compareTo(schedule.getTotalAmount()) != 0) {

            log.warn(
                    "event=validation_error error=invalid_payment_amount expected_amount={} actual_amount={}",
                    schedule.getTotalAmount(),
                    req.getPaidAmount());

            throw new RuntimeException("INVALID_PAYMENT_AMOUNT");
        }

        PaymentTransactionEntity payment = new PaymentTransactionEntity();
        payment.setRepaymentSchedule(schedule);
        payment.setPaymentReference(req.getPaymentReference());
        payment.setPaidAmount(req.getPaidAmount());
        payment.setPaidAt(req.getPaidAt());

        payment.setStatus("SUCCESS");

        payment.setCreatedAt(ZonedDateTime.now());
        payment.setUpdatedAt(ZonedDateTime.now());

        paymentRepo.save(payment);
        log.info(
                "event=payment_success payment_id={} schedule_id={} loan_id={} amount={}",
                payment.getId(),
                schedule.getId(),
                loanId,
                payment.getPaidAmount()      );

        schedule.setStatus("PAID");

        return toResponse(payment);
    }

    // GET BY SCHEDULE ID
    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getByScheduleId(Long scheduleId) {

        repaymentRepo.findById(scheduleId)
                .orElseThrow(() -> {
                    log.warn(
                            "event=repayment_schedule_not_found schedule_id={}",
                            scheduleId);
                    return new RepaymentScheduleNotFoundException(scheduleId);
                });

        return paymentRepo.findByRepaymentScheduleId(scheduleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // TOTAL PAID (optional)
    @Transactional(readOnly = true)
    public java.math.BigDecimal getTotalPaid(Long scheduleId) {

        repaymentRepo.findById(scheduleId)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(scheduleId));

        return paymentRepo.sumPaidAmount(scheduleId);
    }

    // MAPPING
    private PaymentTransactionResponse toResponse(PaymentTransactionEntity p) {

        return new PaymentTransactionResponse(
                p.getId(),
                p.getPaymentReference(),
                p.getPaidAmount(),
                p.getPaidAt(),
                p.getStatus(),
                p.getCreatedAt(),
                p.getUpdatedAt());
    }
}