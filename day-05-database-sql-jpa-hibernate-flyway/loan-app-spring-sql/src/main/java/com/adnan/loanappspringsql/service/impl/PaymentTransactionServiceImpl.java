package com.adnan.loanappspringsql.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.loanappspringsql.dto.CreatePaymentTransactionRequest;
import com.adnan.loanappspringsql.dto.PaymentTransactionResponse;
import com.adnan.loanappspringsql.enums.PaymentStatusEnum;
import com.adnan.loanappspringsql.enums.RepaymentStatusEnum;
import com.adnan.loanappspringsql.exception.BadRequestException;
import com.adnan.loanappspringsql.exception.NotFoundException;
import com.adnan.loanappspringsql.model.PaymentTransaction;
import com.adnan.loanappspringsql.model.RepaymentSchedule;
import com.adnan.loanappspringsql.repository.PaymentTransactionRepository;
import com.adnan.loanappspringsql.repository.RepaymentScheduleRepository;
import com.adnan.loanappspringsql.service.PaymentTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentTransactionServiceImpl implements PaymentTransactionService {
  private final PaymentTransactionRepository paymentTransactionRepository;
  private final RepaymentScheduleRepository repaymentScheduleRepository;

  @Override
  public PaymentTransactionResponse create(CreatePaymentTransactionRequest request) {
    RepaymentSchedule schedule = repaymentScheduleRepository.findById(
        request.getRepaymentScheduleId())
        .orElseThrow(() -> new NotFoundException(
            "Repayment schedule not found with id: "
                + request.getRepaymentScheduleId()));

    if (schedule.getStatus() == RepaymentStatusEnum.PAID) {
      throw new BadRequestException(
          "Repayment schedule has already been paid");
    }

    PaymentTransaction payment = PaymentTransaction.builder()
        .repaymentSchedule(schedule)
        .paymentReference(request.getPaymentReference())
        .paidAmount(request.getPaidAmount())
        .paidAt(request.getPaidAt())
        .status(PaymentStatusEnum.SUCCESS)
        .build();

    paymentTransactionRepository.save(payment);

    BigDecimal totalPaid = paymentTransactionRepository
        .sumPaidAmountByScheduleId(schedule.getId());

    if (totalPaid.compareTo(schedule.getTotalAmount()) >= 0) {
      schedule.setStatus(RepaymentStatusEnum.PAID);
      repaymentScheduleRepository.save(schedule);
    }

    return mapToResponse(payment);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PaymentTransactionResponse> findByRepaymentScheduleId(Long repaymentScheduleId) {
    return paymentTransactionRepository
        .findByRepaymentScheduleId(repaymentScheduleId)
        .stream()
        .map(this::mapToResponse)
        .toList();
  }

  private PaymentTransactionResponse mapToResponse(PaymentTransaction payment) {
    return PaymentTransactionResponse.builder()
        .id(payment.getId())
        .paymentReference(payment.getPaymentReference())
        .paidAmount(payment.getPaidAmount())
        .paidAt(payment.getPaidAt())
        .status(payment.getStatus())
        .build();
  }
}