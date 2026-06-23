package com.fif.exercise2.service;

import com.fif.exercise2.dto.CreatePaymentTransactionRequest;
import com.fif.exercise2.dto.PaymentTransactionResponse;
import com.fif.exercise2.entity.PaymentTransactionEntity;
import com.fif.exercise2.entity.RepaymentScheduleEntity;
import com.fif.exercise2.exception.RepaymentScheduleNotFoundException;
import com.fif.exercise2.repository.PaymentTransactionRepository;
import com.fif.exercise2.repository.RepaymentScheduleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentTransactionService {

    private static final Logger log = LoggerFactory.getLogger(PaymentTransactionService.class);

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional
    public PaymentTransactionResponse createPaymentTransaction(CreatePaymentTransactionRequest request) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository
            .findById(request.getRepaymentScheduleId())
            .orElseThrow(() -> new RepaymentScheduleNotFoundException(request.getRepaymentScheduleId()));

        PaymentTransactionEntity entity = new PaymentTransactionEntity();
        entity.setRepaymentSchedule(schedule);
        entity.setPaymentReference(request.getPaymentReference());
        entity.setPaidAmount(request.getPaidAmount());
        entity.setPaidAt(request.getPaidAt());
        entity.setStatus("SUCCESS");
        entity.setCreatedAt(ZonedDateTime.now());
        entity.setUpdatedAt(ZonedDateTime.now());

        paymentTransactionRepository.save(entity);

        // Cek total pembayaran dan update status schedule
        BigDecimal totalPaid = paymentTransactionRepository
            .sumPaidAmountByScheduleId(schedule.getId());

        if (totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
            schedule.setStatus("PAID");
        } else if (totalPaid.compareTo(BigDecimal.ZERO) > 0) {
            schedule.setStatus("PARTIAL");
        }

        schedule.setUpdatedAt(ZonedDateTime.now());
        repaymentScheduleRepository.save(schedule);

        // INFO: pembayaran berhasil — event bisnis penting untuk audit trail
        // Log payment_reference aman (bukan PII), amount aman untuk audit
        // schedule_status dicatat agar mudah trace perubahan status angsuran
        log.info("event=payment_transaction_created transaction_id={} schedule_id={} paid_amount={} schedule_status={}",
                entity.getId(),
                schedule.getId(),
                entity.getPaidAmount(),
                schedule.getStatus());

        return buildResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getByRepaymentScheduleId(Long repaymentScheduleId) {
        return paymentTransactionRepository.findByRepaymentScheduleId(repaymentScheduleId)
            .stream()
            .map(this::buildResponse)
            .collect(Collectors.toList());
    }

    private PaymentTransactionResponse buildResponse(PaymentTransactionEntity entity) {
        PaymentTransactionResponse response = new PaymentTransactionResponse();
        response.setId(entity.getId());
        response.setRepaymentScheduleId(entity.getRepaymentSchedule().getId());
        response.setPaymentReference(entity.getPaymentReference());
        response.setPaidAmount(entity.getPaidAmount());
        response.setPaidAt(entity.getPaidAt());
        response.setStatus(entity.getStatus());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }
}