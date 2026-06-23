package com.adnan.loanappspringsql.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateLoanApplicationRequest {
  @JsonProperty("customer_id")
  @NotNull(message = "customer_id is required")
  private Long customerId;

  @JsonProperty("loan_amount")
  @NotNull(message = "loan_amount is required")
  @Positive(message = "loan_amount must be greater than 0")
  private BigDecimal loanAmount;

  @JsonProperty("tenor_month")
  @NotNull(message = "tenor_month is required")
  @Min(value = 1, message = "tenor_month must be greater than 0")
  private Integer tenorMonth;

  @NotNull(message = "purpose is required")
  private String purpose;
}