package com.adnan.loanappspringsql.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentTransactionRequest {
  @JsonProperty("repayment_schedule_id")
  @NotNull(message = "repayment_schedule_id is required")
  private Long repaymentScheduleId;

  @JsonProperty("payment_reference")
  @NotNull(message = "payment_reference is required")
  private String paymentReference;

  @JsonProperty("paid_amount")
  @Positive(message = "paid_amount must be greater than 0")
  private BigDecimal paidAmount;

  @JsonProperty("paid_at")
  @NotNull(message = "paid_at is required")
  private ZonedDateTime paidAt;
}