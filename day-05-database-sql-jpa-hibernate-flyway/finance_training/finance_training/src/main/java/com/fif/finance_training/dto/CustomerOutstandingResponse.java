package com.fif.finance_training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOutstandingResponse {
    private Long id;

    @JsonProperty("full_name")
    private String fullName;

    private String nik;

    @JsonProperty("total_loan_amount")
    private BigDecimal totalLoanAmount;

    @JsonProperty("total_paid_amount")
    private BigDecimal totalPaidAmount;

    @JsonProperty("outstanding_amount")
    private BigDecimal outstandingAmount;
}