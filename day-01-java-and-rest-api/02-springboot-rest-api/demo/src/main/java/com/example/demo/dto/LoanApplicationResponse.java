package com.example.demo.dto;

import com.example.demo.model.ApplicationStatus;
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
    @JsonProperty("id")
    private Long id;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("loan_amount")
    private Long LoanAmount;

    @JsonProperty("tenor_month")
    private int tenorMonth;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private ApplicationStatus status;
}
