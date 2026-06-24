package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.CreatePaymentTransactionRequest;
import com.example.demoSpringbootDatabase.dto.PaymentTransactionResponse;
import com.example.demoSpringbootDatabase.entity.PaymentTransactionEntity;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.RepaymentScheduleNotFoundException;
import com.example.demoSpringbootDatabase.repository.PaymentTransactionRepository;
import com.example.demoSpringbootDatabase.repository.RepaymentScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PaymentTransactionService {
    private final PaymentTransactionRepository transactionRepository;
    private final RepaymentScheduleRepository scheduleRepository;

    public PaymentTransactionService(PaymentTransactionRepository transactionRepository, RepaymentScheduleRepository scheduleRepository) {
        this.transactionRepository = transactionRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public PaymentTransactionResponse createTransaction(CreatePaymentTransactionRequest request) {
        RepaymentScheduleEntity schedule = scheduleRepository.findById(request.getRepaymentScheduleId())
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(request.getRepaymentScheduleId()));

        PaymentTransactionEntity trx = PaymentTransactionEntity.builder()
                .repaymentSchedule(schedule).paymentReference(request.getPaymentReference())
                .paidAmount(request.getPaidAmount()).paidAt(request.getPaidAt()).status("SUCCESS").build();

        transactionRepository.save(trx);

        // Ubah status tagihan installment menjadi PAID apabila proses pembayaran sukses
        schedule.setStatus("PAID");
        scheduleRepository.save(schedule);

        return mapToResponse(trx);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getByScheduleId(Long scheduleId) {
        return transactionRepository.findByRepaymentScheduleId(scheduleId).stream().map(this::mapToResponse).toList();
    }

    private PaymentTransactionResponse mapToResponse(PaymentTransactionEntity trx) {
        return PaymentTransactionResponse.builder().id(trx.getId()).repaymentScheduleId(trx.getRepaymentSchedule().getId())
                .paymentReference(trx.getPaymentReference()).paidAmount(trx.getPaidAmount())
                .paidAt(trx.getPaidAt()).status(trx.getStatus()).build();
    }
}
