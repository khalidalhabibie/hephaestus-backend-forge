package com.example.spring_boot_database.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.spring_boot_database.dto.CreatePaymentTransactionRequest;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.entity.PaymentTransactionEntity;
import com.example.spring_boot_database.entity.RepaymentScheduleEntity;
import com.example.spring_boot_database.entity.StatusRepayment;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.exception.CustomerNotFoundException;
import com.example.spring_boot_database.exception.RepaymentScheduleNotFoundException;
import com.example.spring_boot_database.repository.PaymentTransactionRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final RepaymentScheduleRepository scheduleRepo;
    private final PaymentTransactionRepository paymentRepo;

    private RepaymentScheduleEntity getSchedule(Long id) {
        return scheduleRepo.findById(id)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(id));
    }

    @Transactional
    public PaymentTransactionResponse create(CreatePaymentTransactionRequest req) {

        RepaymentScheduleEntity schedule = getSchedule(req.getRepaymentSchedule_id());

        if (schedule.getStatus().equals(StatusRepayment.PAID.name())) {
            throw new BadRequestException("Schedule already paid");
        }

        PaymentTransactionEntity entity = new PaymentTransactionEntity();
        entity.setRepaymentSchedule(schedule);
        entity.setPaidAmount(req.getPaidAmount());
        entity.setPaymentReference(req.getPaymentReference());
        entity.setPaidAt(req.getPaidAt());
        entity.setStatus(StatusRepayment.PAID.name());

        paymentRepo.save(entity);

        // ✅ CHECK TOTAL PAYMENT
        var totalPaid = paymentRepo.sumPaidAmountByScheduleId(schedule.getId());

        if (totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
            schedule.setStatus(StatusRepayment.PAID.name());
            scheduleRepo.save(schedule);
        }

        return toResponse(entity);
    }

    private PaymentTransactionResponse toResponse(PaymentTransactionEntity e) {
        return PaymentTransactionResponse.builder()
                .repaymentScheduleId(e.getRepaymentSchedule().getId())
                .paidAmount(e.getPaidAmount())
                .paymentReference(e.getPaymentReference())
                .paidAt(e.getPaidAt())
                .build();
    }
}