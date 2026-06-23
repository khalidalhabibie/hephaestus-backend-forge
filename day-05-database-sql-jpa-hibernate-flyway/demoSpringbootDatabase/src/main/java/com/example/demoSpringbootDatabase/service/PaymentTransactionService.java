package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.CreatePaymentTransactionRequest;
import com.example.demoSpringbootDatabase.dto.PaymentTransactionResponse;
import com.example.demoSpringbootDatabase.entity.PaymentTransactionEntity;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.RepaymentScheduleNotFoundException;
import com.example.demoSpringbootDatabase.repository.PaymentTransactionRepository;
import com.example.demoSpringbootDatabase.repository.RepaymentScheduleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class PaymentTransactionService {
    private final PaymentTransactionRepository transactionRepository;
    private final RepaymentScheduleRepository scheduleRepository;

    public PaymentTransactionService(PaymentTransactionRepository transactionRepository, 
                                    RepaymentScheduleRepository scheduleRepository) {
        this.transactionRepository = transactionRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public PaymentTransactionResponse createTransaction(CreatePaymentTransactionRequest request) {
        RepaymentScheduleEntity schedule = scheduleRepository.findById(request.getRepaymentScheduleId())
                .orElseThrow(() -> {
                    log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"Repayment schedule ID {} not found during payment\"}", request.getRepaymentScheduleId());
                    return new RepaymentScheduleNotFoundException(request.getRepaymentScheduleId());
                });

        PaymentTransactionEntity trx = PaymentTransactionEntity.builder()
                .repaymentSchedule(schedule)
                .paymentReference(request.getPaymentReference())
                .paidAmount(request.getPaidAmount())
                .paidAt(request.getPaidAt())
                .status("SUCCESS")
                .build();

        transactionRepository.save(trx);

        // 1. Ambil total kumulatif yang sudah dibayar untuk schedule ini
        BigDecimal totalPaidSoFar = transactionRepository.sumPaidAmountByScheduleId(schedule.getId());
        if (totalPaidSoFar == null) {
            totalPaidSoFar = BigDecimal.valueOf(request.getPaidAmount());
        }

        // 2. Ubah status menjadi PAID hanya jika total bayar memenuhi target total_amount tagihan
        BigDecimal targetAmount = BigDecimal.valueOf(schedule.getTotalAmount());
        if (totalPaidSoFar.compareTo(targetAmount) >= 0) {
            schedule.setStatus("PAID");
            scheduleRepository.save(schedule);
        }

        // 3. ✅ STRUCTURED LOG & MASKING REFERENSI PEMBAYARAN
        String maskedRef = maskReference(request.getPaymentReference());
        log.info("{\"event\":\"PAYMENT_SUCCESS\", \"schedule_id\":{}, \"loan_id\":{}, \"amount\":{}, \"reference\":\"{}\"}",
                schedule.getId(), schedule.getLoanApplication().getId(), request.getPaidAmount(), maskedRef);

        return mapToResponse(trx);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getByScheduleId(Long scheduleId) {
        log.debug("Fetching payment history for schedule ID: {}", scheduleId);
        return transactionRepository.findByRepaymentScheduleId(scheduleId).stream().map(this::mapToResponse).toList();
    }

    /**
     * Helper untuk menyamarkan token / referensi transaksi bank agar tidak bocor mentah-mentah ke log
     */
    private String maskReference(String reference) {
        if (reference == null || reference.length() <= 4) return "****";
        return reference.substring(0, 4) + "******";
    }

    private PaymentTransactionResponse mapToResponse(PaymentTransactionEntity trx) {
        return PaymentTransactionResponse.builder()
                .id(trx.getId())
                .repaymentScheduleId(trx.getRepaymentSchedule().getId())
                .paymentReference(trx.getPaymentReference())
                .paidAmount(trx.getPaidAmount())
                .paidAt(trx.getPaidAt())
                .status(trx.getStatus())
                .build();
    }
}