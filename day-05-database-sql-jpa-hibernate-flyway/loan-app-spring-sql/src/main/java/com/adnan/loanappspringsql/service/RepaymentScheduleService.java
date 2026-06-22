package com.adnan.loanappspringsql.service;

import java.util.List;

import com.adnan.loanappspringsql.dto.RepaymentScheduleResponse;

public interface RepaymentScheduleService {
  List<RepaymentScheduleResponse> findByLoanApplicationId(Long loanApplicationId);

  RepaymentScheduleResponse findById(Long id);
}
