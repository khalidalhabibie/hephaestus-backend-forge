package com.fif.loanapplication.dto.payment;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fif.loanapplication.dto.common.BaseDto;
import com.fif.loanapplication.entity.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionResponse extends BaseDto {
    @JsonProperty("repayment_schedule_uid")
    private UUID repaymentScheduleUid;

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    private ZonedDateTime paidAt;

    private PaymentStatus status;

}
