package com.adnan.loanappspringsql.service;

import java.time.LocalDate;
import java.util.List;

import com.adnan.loanappspringsql.dto.CreateLoanApplicationRequest;
import com.adnan.loanappspringsql.dto.LoanApplicationResponse;
import com.adnan.loanappspringsql.dto.LoanStatusSummaryResponse;
import com.adnan.loanappspringsql.dto.OutstandingAmountResponse;
import com.adnan.loanappspringsql.dto.UpdateLoanStatusRequest;
import com.adnan.loanappspringsql.dto.pagination.PageResponse;
import com.adnan.loanappspringsql.enums.LoanStatusEnum;

public interface LoanApplicationService {
        LoanApplicationResponse create(CreateLoanApplicationRequest request);

        LoanApplicationResponse findById(Long id);

        PageResponse<LoanApplicationResponse> findAll(LoanStatusEnum status, LocalDate startDate, LocalDate endDate,
                        int page,
                        int size);

        List<LoanApplicationResponse> findByCustomerId(Long customerId);

        LoanApplicationResponse updateStatus(Long id, UpdateLoanStatusRequest request);

        List<LoanStatusSummaryResponse> getLoanSummaryByStatus();

        OutstandingAmountResponse getOutstandingAmountCustomer(Long customerId);
}
