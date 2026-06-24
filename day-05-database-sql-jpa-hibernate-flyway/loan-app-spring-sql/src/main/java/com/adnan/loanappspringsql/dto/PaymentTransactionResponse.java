package com.adnan.loanappspringsql.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.adnan.loanappspringsql.enums.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentTransactionResponse {

  private Long id;

  @JsonProperty("payment_reference")
  private String paymentReference;

  @JsonProperty("paid_amount")
  private BigDecimal paidAmount;

  @JsonProperty("paid_at")
  private ZonedDateTime paidAt;

  private PaymentStatusEnum status;
}