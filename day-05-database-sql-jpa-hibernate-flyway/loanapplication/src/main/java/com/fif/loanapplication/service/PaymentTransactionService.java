package com.fif.loanapplication.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fif.loanapplication.dto.payment.CreatePaymentTransactionRequest;
import com.fif.loanapplication.dto.payment.PaymentTransactionResponse;
import com.fif.loanapplication.entity.LoanApplicationEntity;
import com.fif.loanapplication.entity.PaymentTransactionEntity;
import com.fif.loanapplication.entity.RepaymentScheduleEntity;
import com.fif.loanapplication.entity.enums.PaymentStatus;
import com.fif.loanapplication.entity.enums.RepaymentStatus;
import com.fif.loanapplication.exception.RepaymentScheduleNotFoundException;
import com.fif.loanapplication.repository.PaymentTransactionRepository;
import com.fif.loanapplication.repository.RepaymentScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    private PaymentTransactionResponse toPaymentResponse(PaymentTransactionEntity paymentTransactionEntity) {
        return PaymentTransactionResponse.builder()
                .uid(paymentTransactionEntity.getUid())
                .repaymentScheduleUid(paymentTransactionEntity.getUid())
                .paymentReference(paymentTransactionEntity.getPaymentReference())
                .paidAmount(paymentTransactionEntity.getPaidAmount())
                .paidAt(paymentTransactionEntity.getPaidAt())
                .status(paymentTransactionEntity.getStatus())
                .createdAt(paymentTransactionEntity.getCreatedAt())
                .updatedAt(paymentTransactionEntity.getUpdatedAt())
                .build();
    }

    @Transactional
    public PaymentTransactionResponse createPayments(CreatePaymentTransactionRequest request) {
        RepaymentScheduleEntity repaymentSchedule = repaymentScheduleRepository
                .findById(request.getRepaymentScheduleUid())
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(request.getRepaymentScheduleUid()));

        if (request.getPaidAmount().compareTo(repaymentSchedule.getTotalAmount()) >= 0) {
            repaymentSchedule.setStatus(RepaymentStatus.PAID);
        } else {
            repaymentSchedule.setStatus(RepaymentStatus.PARTIALLY_PAID);
        }

        PaymentTransactionEntity savedPaymentTransactionEntity = PaymentTransactionEntity.builder()
                .repaymentSchedule(repaymentSchedule)
                .paymentReference(request.getPaymentReference())
                .paidAmount(request.getPaidAmount())
                .paidAt(request.getPaidAt())
                .status(PaymentStatus.SUCCESS)
                .build();

        PaymentTransactionEntity response = paymentTransactionRepository.save(savedPaymentTransactionEntity);

        return toPaymentResponse(response);
    }

    @Transactional
    public List<PaymentTransactionResponse> getPaymentByRepaymentUid(UUID repaymentUid) {
        List<PaymentTransactionEntity> payments = paymentTransactionRepository.findByRepaymentScheduleUid(repaymentUid);
        return payments.stream().map(this::toPaymentResponse).toList();
    }

}
