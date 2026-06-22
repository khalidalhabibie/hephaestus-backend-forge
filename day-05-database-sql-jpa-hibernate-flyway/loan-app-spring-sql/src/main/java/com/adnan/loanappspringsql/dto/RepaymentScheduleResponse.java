package com.adnan.loanappspringsql.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.adnan.loanappspringsql.enums.RepaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepaymentScheduleResponse {
  private Long id;

  @JsonProperty("installment_number")
  private Integer installmentNumber;

  @JsonProperty("due_date")
  private LocalDate dueDate;

  @JsonProperty("principal_amount")
  private BigDecimal principalAmount;

  @JsonProperty("interest_amount")
  private BigDecimal interestAmount;

  @JsonProperty("total_amount")
  private BigDecimal totalAmount;

  private RepaymentStatusEnum status;
}