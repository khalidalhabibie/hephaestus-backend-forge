package com.example.training.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.training.model.LoanStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonPropertyOrder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponse {
    @JsonProperty("loan_application_id")
    private UUID loanApplicationId;

    @JsonProperty("customer_id")
    private Long id;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private int tenorMonth;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private LoanStatus status; 
}
