package com.fif.loanapplication.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fif.loanapplication.dto.common.ApiResponseDto;
import com.fif.loanapplication.dto.loanapplication.CreateLoanApplicationRequest;
import com.fif.loanapplication.dto.loanapplication.LoanApplicationResponse;
import com.fif.loanapplication.dto.loanapplication.UpdateLoanStatusRequest;
import com.fif.loanapplication.dto.payment.RepaymentScheduleResponse;
import com.fif.loanapplication.entity.enums.LoanStatus;
import com.fif.loanapplication.service.LoanApplicationService;
import com.fif.loanapplication.service.RepaymentScheduleService;
import com.fif.loanapplication.utils.LoanApplicationUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;
    private final LoanApplicationUtils loanApplicationUtils;
    private final RepaymentScheduleService repaymentScheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<LoanApplicationResponse> createLoanApplication(
            @Valid @RequestBody CreateLoanApplicationRequest request) {
        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
        return new ApiResponseDto<>(true, "Loan Application Created!", response);

    }

    @GetMapping("/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<LoanApplicationResponse> getLoanApplicationByUid(@PathVariable UUID uid) {
        LoanApplicationResponse response = loanApplicationService.getLoanByUid(uid);
        return new ApiResponseDto<>(true, "Loan Application Retrieved!", response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<LoanApplicationResponse>> getLoanApplications(
            @RequestParam(required = false) String status) {
        LoanStatus loanStatus = loanApplicationUtils.parseLoanStatus(status);
        List<LoanApplicationResponse> responses = loanApplicationService.getLoans(loanStatus);
        return new ApiResponseDto<>(true, "Loan Applications Retrieved!", responses);
    }

    @GetMapping({ "/{loan_application_uid}/repayment-schedules" })
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<RepaymentScheduleResponse>> getRepaymentSchedulesByLoanApplicationUid(
            @Valid @PathVariable("loan_application_uid") UUID loanApplicationUid) {
        List<RepaymentScheduleResponse> responses = repaymentScheduleService
                .getRepaymentScheduleByLoanApplicationUid(loanApplicationUid);
        return new ApiResponseDto<>(true, "Repayment schedules retrieved!", responses);

    }

    @PatchMapping("/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<LoanApplicationResponse> approveLoanApplication(@Valid @PathVariable UUID uid,
            @RequestBody UpdateLoanStatusRequest request) {
        LoanApplicationResponse response = loanApplicationService.approveLoanApplicationByUid(uid, request);
        return new ApiResponseDto<>(true, "Loan Application Approved!", response);
    }

}
