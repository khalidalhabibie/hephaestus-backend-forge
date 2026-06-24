package com.adnan.loanappspringsql.dto;

import com.adnan.loanappspringsql.enums.LoanStatusEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateLoanStatusRequest {
  @NotNull(message = "status is required")
  private LoanStatusEnum status;
}