package com.fif.training.exercisespringboot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateLoanApplicationRequest {

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("loan_amount")
    private Double loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    private String purpose;
}