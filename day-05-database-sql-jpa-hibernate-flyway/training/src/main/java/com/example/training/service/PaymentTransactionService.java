package com.example.training.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional
    public PaymentTransactionResponse create(CreatePaymentTransactionRequest request) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findById(request.getRepaymentScheduleId())
                .orElseThrow(() -> new NotFoundException("REPAYMENT_SCHEDULE_NOT_FOUND", "Repayment schedule not found with id: " + request.getRepaymentScheduleId()));

        if (schedule.getStatus() == RepaymentStatus.PAID) {
            throw new IllegalStateException("Repayment schedule is already PAID");
        }

        PaymentTransactionEntity payment = new PaymentTransactionEntity();
        payment.setRepaymentSchedule(schedule);
        payment.setPaymentReference(request.getPaymentReference());
        payment.setPaidAmount(request.getPaidAmount());
        payment.setPaidAt(request.getPaidAt());
        payment.setStatus(PaymentStatus.SUCCESS);

        PaymentTransactionEntity saved = paymentTransactionRepository.save(payment);

        // flush agar native query di bawah melihat data terbaru
        paymentTransactionRepository.flush();

        // Check if total paid meets total_amount and auto-update schedule status
        BigDecimal totalPaid = paymentTransactionRepository.sumPaidAmountByScheduleId(request.getRepaymentScheduleId());
        if (totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
            schedule.setStatus(RepaymentStatus.PAID);
            repaymentScheduleRepository.save(schedule);
        }

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> findByRepaymentScheduleId(UUID repaymentScheduleId) {
        return paymentTransactionRepository.findByRepaymentSchedule_Id(repaymentScheduleId).stream()
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
