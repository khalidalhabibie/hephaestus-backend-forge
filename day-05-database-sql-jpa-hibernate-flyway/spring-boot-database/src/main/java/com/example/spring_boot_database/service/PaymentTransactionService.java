package com.example.spring_boot_database.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.spring_boot_database.dto.CreatePaymentTransactionRequest;
import com.example.spring_boot_database.dto.PaymentTransactionResponse;
import com.example.spring_boot_database.entity.PaymentTransactionEntity;
import com.example.spring_boot_database.entity.RepaymentScheduleEntity;
import com.example.spring_boot_database.entity.StatusRepayment;
import com.example.spring_boot_database.exception.CustomerNotFoundException;
import com.example.spring_boot_database.repository.PaymentTransactionRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    public RepaymentScheduleEntity getByIdRepayment(Long id) {
        return repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

private void fill(PaymentTransactionEntity entity, CreatePaymentTransactionRequest request, RepaymentScheduleEntity schedule) {
    entity.setRepaymentSchedule(schedule);
    entity.setPaymentReference(request.getPaymentReference());
    entity.setPaidAmount(request.getPaidAmount());
    entity.setPaidAt(request.getPaidAt());
    entity.setStatus(StatusRepayment.PAID.name());
}

@Transactional
public PaymentTransactionResponse createPaymentTransaction(CreatePaymentTransactionRequest request) {

    RepaymentScheduleEntity schedule = getByIdRepayment(request.getRepaymentSchedule_id());

    if (schedule.getStatus().equals(StatusRepayment.PAID.name())) {
        throw new RuntimeException("This installment is already paid");
    }

    PaymentTransactionEntity entity = new PaymentTransactionEntity();
    fill(entity, request, schedule);

    PaymentTransactionEntity saved = paymentTransactionRepository.save(entity);

    schedule.setStatus(StatusRepayment.PAID.name());
    repaymentScheduleRepository.save(schedule);

    return toResponse(saved);
}

    public PaymentTransactionResponse toResponse(PaymentTransactionEntity entity) {

        return PaymentTransactionResponse.builder()
            .repaymentScheduleId(entity.getRepaymentSchedule().getId())
            .paidAmount(entity.getPaidAmount())
            .paymentReference(entity.getPaymentReference()) 
            .paidAt(LocalDateTime.now()) 
            .build();
    }
}
