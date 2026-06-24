package com.adnan.loanappspringsql.dto;

import com.adnan.loanappspringsql.enums.LoanStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanStatusSummaryResponse {
  private LoanStatusEnum status;
  private Long total;
}
