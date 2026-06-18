package com.adnan.exercisespring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateLoanApplicationRequest {
  @JsonProperty("customer_id")
  private Long customerId;

  @JsonProperty("loan_amount")
  private Long loanAmount;

  @JsonProperty("tenor_month")
  private Integer tenorMonth;

  private String purpose;
}