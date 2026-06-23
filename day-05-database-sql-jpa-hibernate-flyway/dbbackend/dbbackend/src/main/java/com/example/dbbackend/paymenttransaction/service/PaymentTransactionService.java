package com.example.dbbackend.paymenttransaction.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dbbackend.common.exception.RepaymentScheduleNotFoundException;
import com.example.dbbackend.paymenttransaction.dto.CreatePaymentTransactionRequest;
import com.example.dbbackend.paymenttransaction.dto.PaymentTransactionResponse;
import com.example.dbbackend.paymenttransaction.entity.PaymentTransactionEntity;
import com.example.dbbackend.paymenttransaction.entity.PaymentTransactionStatus;
import com.example.dbbackend.paymenttransaction.repository.PaymentTransactionRepository;
import com.example.dbbackend.repaymentschedule.entity.RepaymentScheduleEntity;
import com.example.dbbackend.repaymentschedule.repository.RepaymentScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentTransactionService {

        private final PaymentTransactionRepository repository;
        private final RepaymentScheduleRepository scheduleRepository;

        public PaymentTransactionService(PaymentTransactionRepository repository,
                        RepaymentScheduleRepository scheduleRepository) {
                this.repository = repository;
                this.scheduleRepository = scheduleRepository;
        }

        private PaymentTransactionResponse mapToResponse(PaymentTransactionEntity entity) {

                PaymentTransactionResponse response = new PaymentTransactionResponse();

                response.setId(entity.getId());
                response.setPaymentReference(entity.getPaymentReference());
                response.setPaidAmount(entity.getPaidAmount());
                response.setPaidAt(entity.getPaidAt());
                response.setStatus(PaymentTransactionStatus.valueOf(entity.getStatus()));

                return response;
        }

        @Transactional
        public PaymentTransactionResponse createPayment(CreatePaymentTransactionRequest request) {

                RepaymentScheduleEntity schedule = scheduleRepository.findById(request.getRepaymentScheduleId())
                                .orElseThrow(() -> {
                                        log.warn("event=PaymentFailed reason=ScheduleNotFound scheduleId={}",
                                                        request.getRepaymentScheduleId());
                                        return new RepaymentScheduleNotFoundException(
                                                        "Schedule not found with id: "
                                                                        + request.getRepaymentScheduleId());
                                });

                PaymentTransactionEntity entity = new PaymentTransactionEntity();
                entity.setRepaymentSchedule(schedule);
                entity.setPaymentReference(request.getPaymentReference());
                entity.setPaidAmount(request.getPaidAmount());
                entity.setPaidAt(request.getPaidAt());
                entity.setStatus(PaymentTransactionStatus.SUCCESS.name());
                entity.setCreatedAt(java.time.LocalDateTime.now());
                entity.setUpdatedAt(java.time.LocalDateTime.now());

                PaymentTransactionEntity saved = repository.save(entity);

                log.info("event=PaymentCreated paymentId={} scheduleId={} amount={}",
                                saved.getId(),
                                request.getRepaymentScheduleId(),
                                request.getPaidAmount());

                BigDecimal totalPaid = repository
                                .sumPaidAmountByScheduleId(schedule.getId());

                if (totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
                        schedule.setStatus("PAID");
                        schedule.setUpdatedAt(java.time.LocalDateTime.now());

                        scheduleRepository.save(schedule);

                        log.info("event=RepaymentMarkedPaid scheduleId={} totalPaid={}",
                                        schedule.getId(),
                                        totalPaid);

                }

                return mapToResponse(saved);
        }

        @Transactional(readOnly = true)
        public List<PaymentTransactionResponse> getBySchedule(Long scheduleId) {

                return repository.findByRepaymentScheduleId(scheduleId)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

}