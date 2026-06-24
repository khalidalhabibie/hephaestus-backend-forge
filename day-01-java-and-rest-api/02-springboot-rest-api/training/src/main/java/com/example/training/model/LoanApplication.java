package com.example.training.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    private UUID loanApplicationId;
    private Long id;
    private BigDecimal loanAmount;
    private int tenorMonth;
    private String purpose;
    private LoanStatus status;
}
