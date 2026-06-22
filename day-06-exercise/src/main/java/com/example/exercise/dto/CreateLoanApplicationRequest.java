package com.example.exercise.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanApplicationRequest {
    // @NotBlank
    @JsonProperty("customer_id")
    private Long customerId;
    // @NotBlank
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;
    // @NotBlank
    @JsonProperty("tenor_month")
    private int tenorMonth;
    // @NotBlank
    private String purpose;
}