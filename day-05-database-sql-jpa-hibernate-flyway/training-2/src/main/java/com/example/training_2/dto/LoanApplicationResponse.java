package com.example.training_2.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.training_2.entity.LoanApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanApplicationResponse {
    private Long id;

    private Long customerId;

    private BigDecimal loanAmount;

    private Integer tenorMonth;

    private String purpose;

    private LoanApplicationStatus status;
}