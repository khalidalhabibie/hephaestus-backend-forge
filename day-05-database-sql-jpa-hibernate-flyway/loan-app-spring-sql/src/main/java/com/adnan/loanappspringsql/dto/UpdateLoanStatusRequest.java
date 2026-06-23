package com.adnan.loanappspringsql.dto;

import com.adnan.loanappspringsql.enums.LoanStatusEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateLoanStatusRequest {
  @NotNull(message = "status is required")
  private LoanStatusEnum status;
}