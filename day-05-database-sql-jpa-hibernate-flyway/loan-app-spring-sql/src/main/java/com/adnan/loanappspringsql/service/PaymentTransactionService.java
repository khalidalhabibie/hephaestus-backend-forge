package com.adnan.loanappspringsql.service;

import java.util.List;

import com.adnan.loanappspringsql.dto.CreatePaymentTransactionRequest;
import com.adnan.loanappspringsql.dto.PaymentTransactionResponse;

public interface PaymentTransactionService {
  PaymentTransactionResponse create(CreatePaymentTransactionRequest request);

  List<PaymentTransactionResponse> findByRepaymentScheduleId(Long repaymentScheduleId);
}