package com.example.demo_day2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateLoanApplicationRequest {

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("loan_amount")
    private Double loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    private String purpose;
}
