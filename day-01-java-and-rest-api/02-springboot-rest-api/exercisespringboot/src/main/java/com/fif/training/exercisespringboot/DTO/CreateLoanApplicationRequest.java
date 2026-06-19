package com.fif.training.exercisespringboot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLoanApplicationRequest {
    @JsonProperty("customer_id")
    @NotNull(message = "Customer ID harus diisi")
    @Positive(message = "Customer ID harus positif")
    private Long customerId;

    @JsonProperty("loan_amount")
    @NotNull(message = "Loan amount harus diisi")
    @Positive(message = "Loan amount harus positif")
    private Double loanAmount;

    @JsonProperty("tenor_month")
    @NotNull(message = "Tenor month harus diisi")
    @Positive(message = "Tenor month harus positif")
    private Integer tenorMonth;

    @JsonProperty("purpose")
    @NotBlank(message = "Purpose harus diisi")
    private String purpose;
}