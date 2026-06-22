package com.example.spring_boot_database.dto;

import java.math.BigDecimal;

import com.example.spring_boot_database.entity.Status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanSummaryByStatusResponse {

    private Status status;

    private Long totalLoan;

    private BigDecimal totalLoanAmount;
}