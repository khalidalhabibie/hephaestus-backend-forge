package com.example.main.services;

import com.example.main.dto.request.PaymentTransactionRequest;
import com.example.main.dto.response.PaymentTransactionResponse;
import com.example.main.entity.PaymentTransactionEntity;
import com.example.main.entity.RepaymentScheduleEntity;
import com.example.main.enums.ScheduleStatus;
import com.example.main.exceptions.BadRequestException;
import com.example.main.exceptions.NotFoundException;
import com.example.main.repositories.PaymentTransactionRepository;
import com.example.main.repositories.RepaymentScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    public PaymentTransactionService(PaymentTransactionRepository paymentTransactionRepository,
            RepaymentScheduleRepository repaymentScheduleRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.repaymentScheduleRepository = repaymentScheduleRepository;
    }

    @Transactional
    public PaymentTransactionResponse processPayment(PaymentTransactionRequest request) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findById(request.getRepaymentScheduleId())
                .orElseThrow(() -> new NotFoundException(
                        "Repayment schedule not found"));

        if (ScheduleStatus.PAID == schedule.getStatus()) {
            throw new BadRequestException("This repayment schedule has already been paid");
        }

        if (request.getPaidAmount().compareTo(schedule.getTotalAmount()) != 0) {
            throw new BadRequestException("Invalid paid amount. Expected exactly: " + schedule.getTotalAmount());
        }

        PaymentTransactionEntity transaction = new PaymentTransactionEntity();
        transaction.setRepaymentSchedule(schedule);
        transaction.setPaymentReference(request.getPaymentReference());
        transaction.setPaidAmount(request.getPaidAmount());
        transaction.setPaidAt(request.getPaidAt());
        transaction.setStatus("SUCCESS");
        PaymentTransactionEntity savedTransaction = paymentTransactionRepository.save(transaction);

        schedule.setStatus(ScheduleStatus.PAID);
        repaymentScheduleRepository.save(schedule);

        return mapToResponse(savedTransaction);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getTransactionsByScheduleId(Long scheduleId) {
        if (!repaymentScheduleRepository.existsById(scheduleId)) {
            throw new NotFoundException("Repayment schedule not found");
        }

        List<PaymentTransactionEntity> transactions = paymentTransactionRepository.findByRepaymentScheduleId(scheduleId);

        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentTransactionResponse mapToResponse(PaymentTransactionEntity tx) {
        PaymentTransactionResponse response = new PaymentTransactionResponse();
        response.setId(tx.getId());
        response.setRepaymentScheduleId(tx.getRepaymentSchedule().getId());
        response.setPaymentReference(tx.getPaymentReference());
        response.setPaidAmount(tx.getPaidAmount());
        response.setPaidAt(tx.getPaidAt());
        response.setStatus(tx.getStatus());
        return response;
    }
}