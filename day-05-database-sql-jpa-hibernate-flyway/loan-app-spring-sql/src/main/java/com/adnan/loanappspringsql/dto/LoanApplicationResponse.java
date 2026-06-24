package com.adnan.loanappspringsql.dto;

import java.math.BigDecimal;

import com.adnan.loanappspringsql.enums.LoanStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanApplicationResponse {
  private Long id;

  @JsonProperty("loan_amount")
  private BigDecimal loanAmount;

  @JsonProperty("tenor_month")
  private Integer tenorMonth;

  private String purpose;

  private LoanStatusEnum status;

  private CustomerSummaryResponse customer;
}