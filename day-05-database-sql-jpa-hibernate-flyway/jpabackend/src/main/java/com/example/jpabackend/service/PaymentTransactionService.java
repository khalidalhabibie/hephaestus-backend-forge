package com.example.jpabackend.service;

import com.example.jpabackend.entity.*;
import com.example.jpabackend.repository.*;

import lombok.Data;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.exception.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PaymentTransactionService {

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
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(req.getRepaymentScheduleId()));

        Long loanId = schedule.getLoanApplication().getId();

        // UNIQUE PER LOAN
        if (paymentRepo.existsByRepaymentSchedule_LoanApplication_IdAndPaymentReference(
                loanId,
                req.getPaymentReference())) {
            throw new RuntimeException("PAYMENT_REFERENCE_ALREADY_EXISTS_FOR_LOAN");
        }

        // ALREADY PAID
        if (schedule.getStatus().equals("PAID")) {
            throw new RuntimeException("SCHEDULE_ALREADY_PAID");
        }

        // EXACT AMOUNT
        if (req.getPaidAmount().compareTo(schedule.getTotalAmount()) != 0) {
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

        schedule.setStatus("PAID");

        return toResponse(payment);
    }

    // GET BY SCHEDULE ID
    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getByScheduleId(Long scheduleId) {

        repaymentRepo.findById(scheduleId)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(scheduleId));

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