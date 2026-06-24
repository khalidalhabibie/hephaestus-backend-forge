package com.example.dbbackend.loanapplication.dto;

import java.math.BigDecimal;

import com.example.dbbackend.customer.dto.CustomerSummaryResponse;
import com.example.dbbackend.loanapplication.entity.LoanApplicationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private LoanApplicationStatus status;

    @JsonProperty("customer")
    private CustomerSummaryResponse customer;

}
