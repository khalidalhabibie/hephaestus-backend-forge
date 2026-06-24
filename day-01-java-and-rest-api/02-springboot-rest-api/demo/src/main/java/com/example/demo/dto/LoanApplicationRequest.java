package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanApplicationRequest {

    @NotNull
    @JsonProperty("customer_id")
    private Long customerId;

    @NotNull
    @Positive
    @JsonProperty("loan_amount")
    private Long loanAmount;

    @NotNull
    @Positive
    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @NotBlank
    @JsonProperty("purpose")
    private String purpose;
}
