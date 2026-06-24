package com.fif.training.exercisespringboot.DTO.Loan;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanApplicationRequest(
                @NotNull @JsonProperty("customer_id") Long customerId,

                @NotNull @JsonProperty("loan_amount") Integer loanAmount,

                @NotNull @JsonProperty("tenor_month") Integer tenorMonth,

                @NotBlank String purpose) {
}