package com.adnan.loanappspringsql.service;

import java.util.List;

import com.adnan.loanappspringsql.dto.RepaymentScheduleResponse;
import com.adnan.loanappspringsql.enums.RepaymentStatusEnum;

public interface RepaymentScheduleService {
  List<RepaymentScheduleResponse> findByLoanApplicationId(Long loanApplicationId);

  RepaymentScheduleResponse findById(Long id);

  List<RepaymentScheduleResponse> findByStatus(
      RepaymentStatusEnum status);

  List<RepaymentScheduleResponse> findByLoanApplicationIdAndStatus(
      Long loanApplicationId,
      RepaymentStatusEnum status);
}
