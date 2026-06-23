package com.example.spring_boot_database.dto;

import java.math.BigDecimal;

import com.example.spring_boot_database.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoanApplicationResponse {
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private int tenorMonth;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("customer")
    private CustomerResponse customer;
}

//    "loan_amount": 10000000,
//     "tenor_month": 12,
//     "purpose": "Working capital",
//     "status": "APPROVED",
//     "customer": {