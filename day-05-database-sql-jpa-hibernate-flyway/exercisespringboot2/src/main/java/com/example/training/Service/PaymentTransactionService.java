// Business logic payment: create (update status repayment jadi PAID/PARTIAL), get by schedule.

package com.example.training.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.training.DTO.CreatePaymentTransactionRequest;
import com.example.training.DTO.PaymentTransactionResponse;
import com.example.training.Entity.PaymentTransactionEntity;
import com.example.training.Entity.RepaymentScheduleEntity;
import com.example.training.Exception.RepaymentScheduleNotFoundException;
import com.example.training.Repository.PaymentTransactionRepository;
import com.example.training.Repository.RepaymentScheduleRepository;

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
        log.info("event=payment_creation_requested, schedule_id={}, paid_amount={}, payment_ref_masked={}", 
                request.getRepaymentScheduleId(), 
                request.getPaidAmount(),
                maskRef(request.getPaymentReference()));

        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findById(request.getRepaymentScheduleId())
                .orElseThrow(() -> {
                    log.warn("event=payment_creation_failed, reason=schedule_not_found, schedule_id={}", 
                            request.getRepaymentScheduleId());
                    return new RepaymentScheduleNotFoundException(request.getRepaymentScheduleId());
                });

        PaymentTransactionEntity payment = new PaymentTransactionEntity();
        payment.setRepaymentSchedule(schedule);
        payment.setPaymentReference(request.getPaymentReference());
        payment.setPaidAmount(request.getPaidAmount());
        payment.setStatus(PaymentTransactionEntity.PaymentStatus.SUCCESS);
        payment.setPaidAt(request.getPaidAt() != null ? request.getPaidAt() : ZonedDateTime.now());

        BigDecimal totalPaid = paymentTransactionRepository.sumPaidAmountByScheduleId(schedule.getId())
                .add(request.getPaidAmount());

        if (totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
            schedule.setStatus(RepaymentScheduleEntity.ScheduleStatus.PAID);
            log.info("event=schedule_fully_paid, schedule_id={}, total_paid={}", schedule.getId(), totalPaid);
        } else {
            schedule.setStatus(RepaymentScheduleEntity.ScheduleStatus.PARTIAL);
            log.info("event=schedule_partially_paid, schedule_id={}, total_paid={}, remaining={}", 
                    schedule.getId(), totalPaid, schedule.getTotalAmount().subtract(totalPaid));
        }
        repaymentScheduleRepository.save(schedule);

        PaymentTransactionEntity saved = paymentTransactionRepository.save(payment);
        
        log.info("event=payment_created, payment_id={}, schedule_id={}, status={}", 
                saved.getId(), schedule.getId(), saved.getStatus());

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getByRepaymentScheduleId(Long repaymentScheduleId) {
        log.info("event=payment_list_by_schedule_requested, schedule_id={}", repaymentScheduleId);
        
        if (!repaymentScheduleRepository.existsById(repaymentScheduleId)) {
            log.warn("event=payment_list_by_schedule_failed, reason=schedule_not_found, schedule_id={}", 
                    repaymentScheduleId);  
            throw new RepaymentScheduleNotFoundException(repaymentScheduleId);
        }
        return paymentTransactionRepository.findByRepaymentScheduleId(repaymentScheduleId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentTransactionResponse mapToResponse(PaymentTransactionEntity entity) {
        return PaymentTransactionResponse.builder()
                .id(entity.getId())
                .repaymentScheduleId(entity.getRepaymentSchedule() != null ? entity.getRepaymentSchedule().getId() : null)
                .paymentReference(entity.getPaymentReference())
                .paidAmount(entity.getPaidAmount())
                .paidAt(entity.getPaidAt())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private String maskRef(String ref) {
        if (ref == null || ref.length() < 6) return "****";
        return ref.substring(0, 3) + "***" + ref.substring(ref.length() - 3);
    }
}