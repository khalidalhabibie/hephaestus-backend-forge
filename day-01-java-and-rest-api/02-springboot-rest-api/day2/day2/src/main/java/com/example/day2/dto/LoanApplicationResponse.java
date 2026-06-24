package com.example.day2.dto;

import com.example.day2.enum_auth.LoanStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoanApplicationResponse {
    private Long id;
    private Long customerId;
    private BigDecimal loanAmount;
    private Integer tenorMonth;
    private String purpose;
    private LoanStatus status;
}
