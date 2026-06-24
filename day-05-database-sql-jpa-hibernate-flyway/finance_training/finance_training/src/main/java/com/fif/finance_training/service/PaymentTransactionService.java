package com.fif.finance_training.service;

import com.fif.finance_training.dto.CreatePaymentTransactionRequest;
import com.fif.finance_training.dto.PaymentTransactionResponse;
import com.fif.finance_training.entity.PaymentTransactionEntity;
import com.fif.finance_training.entity.RepaymentScheduleEntity;
import com.fif.finance_training.entity.enums.PaymentStatus;
import com.fif.finance_training.entity.enums.RepaymentStatus;
import com.fif.finance_training.exception.RepaymentScheduleNotFoundException;
import com.fif.finance_training.repository.PaymentTransactionRepository;
import com.fif.finance_training.repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional
    public PaymentTransactionResponse createPayment(CreatePaymentTransactionRequest request) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findById(request.getRepaymentScheduleId())
                .orElseThrow(() -> new RepaymentScheduleNotFoundException("Repayment schedule not found with id: " + request.getRepaymentScheduleId()));

        PaymentTransactionEntity transaction = PaymentTransactionEntity.builder()
                .repaymentSchedule(schedule)
                .paymentReference(request.getPaymentReference())
                .paidAmount(request.getPaidAmount())
                .paidAt(request.getPaidAt() != null ? request.getPaidAt() : LocalDateTime.now())
                .status(PaymentStatus.SUCCESS)
                .build();

        PaymentTransactionEntity saved = paymentTransactionRepository.save(transaction);

        BigDecimal totalPaid = paymentTransactionRepository.sumPaidAmountByScheduleId(schedule.getId());
        
        if (totalPaid != null && totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
            schedule.setStatus(RepaymentStatus.PAID);
            repaymentScheduleRepository.save(schedule);
        }

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getPaymentsByScheduleId(Long scheduleId) {
        return paymentTransactionRepository.findByRepaymentScheduleId(scheduleId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentTransactionResponse mapToResponse(PaymentTransactionEntity entity) {
        return PaymentTransactionResponse.builder()
                .id(entity.getId())
                .repaymentScheduleId(entity.getRepaymentSchedule().getId())
                .paymentReference(entity.getPaymentReference())
                .paidAmount(entity.getPaidAmount())
                .paidAt(entity.getPaidAt())
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}