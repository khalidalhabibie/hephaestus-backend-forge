package com.example.jpabackend.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.jpabackend.dto.request.CreatePaymentTransactionRequest;
import com.example.jpabackend.dto.response.PaymentTransactionResponse;
import com.example.jpabackend.entity.PaymentTransactionEntity;
import com.example.jpabackend.entity.RepaymentScheduleEntity;
import com.example.jpabackend.enums.PaymentStatus;
import com.example.jpabackend.enums.RepaymentStatus;
import com.example.jpabackend.exception.PaymentTransactionNotFoundException;
import com.example.jpabackend.exception.RepaymentScheduleNotFoundException;
import com.example.jpabackend.repository.PaymentTransactionRepository;
import com.example.jpabackend.repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    private PaymentTransactionResponse mapToResponse(PaymentTransactionEntity payment) {
        return PaymentTransactionResponse.builder()
            .id(payment.getId())
            .paymentReference(payment.getPaymentReference())
            .paidAmount(payment.getPaidAmount())
            .paidAt(payment.getPaidAt())
            .status(payment.getStatus())
            .build();
    }

    @Transactional
    public PaymentTransactionResponse createPaymentTransaction(CreatePaymentTransactionRequest request) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository
            .findById(request.getRepaymentScheduleId())
            .orElseThrow(() -> new RepaymentScheduleNotFoundException(
                "Repayment schedule " + request.getRepaymentScheduleId() + " not found"));
        PaymentTransactionEntity payment = PaymentTransactionEntity.builder()
            .repaymentSchedule(schedule)
            .paymentReference(request.getPaymentReference())
            .paidAmount(request.getPaidAmount())
            .paidAt(request.getPaidAt())
            .status(PaymentStatus.SUCCESS)
            .build(); 
        PaymentTransactionEntity saved = paymentTransactionRepository.save(payment);
        BigDecimal totalPaid = paymentTransactionRepository.sumPaidAmountByScheduleId(schedule.getId());
            if (totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
                schedule.setStatus(RepaymentStatus.PAID);
                repaymentScheduleRepository.save(schedule);
            }
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse>getPaymentsBySchedule(Long repaymentScheduleId) {
        if (!repaymentScheduleRepository.existsById(repaymentScheduleId)) {
            throw new RepaymentScheduleNotFoundException(
                "Repayment schedule " + repaymentScheduleId + " not found");
        }
        return paymentTransactionRepository
            .findByRepaymentScheduleId(repaymentScheduleId)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Transactional
    public void deletePayment(Long id) {
        PaymentTransactionEntity payment = paymentTransactionRepository.findById(id)
            .orElseThrow(() -> new PaymentTransactionNotFoundException(
                "Payment transaction " + id + " not found"));
        payment.setDeleted(true);
        paymentTransactionRepository.save(payment);
    }
}
