package com.adnan.loanappspringsql.service;

import java.util.List;

import com.adnan.loanappspringsql.dto.CreateLoanApplicationRequest;
import com.adnan.loanappspringsql.dto.LoanApplicationResponse;
import com.adnan.loanappspringsql.dto.UpdateLoanStatusRequest;
import com.adnan.loanappspringsql.enums.LoanStatusEnum;

public interface LoanApplicationService {
  LoanApplicationResponse create(CreateLoanApplicationRequest request);

  LoanApplicationResponse findById(Long id);

  List<LoanApplicationResponse> findAll();

  List<LoanApplicationResponse> findByCustomerId(Long customerId);

  List<LoanApplicationResponse> findByStatus(LoanStatusEnum status);

  LoanApplicationResponse updateStatus(Long id, UpdateLoanStatusRequest request);
}
