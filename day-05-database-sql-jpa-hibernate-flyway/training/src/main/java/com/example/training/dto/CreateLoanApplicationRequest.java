package com.example.training.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateLoanApplicationRequest {

    @JsonProperty("customer_id")
    @NotNull
    private UUID customerId;

    @JsonProperty("loan_amount")
    @NotNull
    @Positive
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    @NotNull
    @Positive
    private Integer tenorMonth;

    @NotBlank
    private String purpose;
}