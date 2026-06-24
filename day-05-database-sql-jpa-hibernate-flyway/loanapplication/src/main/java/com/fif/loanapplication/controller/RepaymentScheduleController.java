package com.fif.loanapplication.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fif.loanapplication.dto.common.ApiResponseDto;
import com.fif.loanapplication.dto.payment.PaymentTransactionResponse;
import com.fif.loanapplication.dto.payment.RepaymentScheduleResponse;
import com.fif.loanapplication.service.PaymentTransactionService;
import com.fif.loanapplication.service.RepaymentScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/repayment-schedules")
@RequiredArgsConstructor
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;
    private final PaymentTransactionService paymentTransactionService;

    @GetMapping({ "/{uid}" })
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<RepaymentScheduleResponse> getRepaymentScheduleByUid(@Valid @PathVariable("uid") UUID uid) {
        RepaymentScheduleResponse response = repaymentScheduleService.getRepaymentScheduleByUid(uid);
        return new ApiResponseDto<>(true, "Repayment Schedule Retrieved!", response);
    }

    @GetMapping({ "/{repayment_schedule_uid}/payment-transactions" })
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<PaymentTransactionResponse>> getPaymentTransactionByRepaymentUid(
            @Valid @PathVariable("repayment_schedule_uid") UUID repaymentUid) {
        List<PaymentTransactionResponse> responses = paymentTransactionService.getPaymentByRepaymentUid(repaymentUid);
        return new ApiResponseDto<>(true, "Payments Retrieved!", responses);

    }

}
