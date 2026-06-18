package com.andyana.exerciseday02.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLoanApplicationRequest {
    
    @NotNull(message = "Customer ID is required")
    @JsonProperty("customer_id")
    private Long customerId;
    
    @NotNull(message = "Loan amount is required")
    @Min(value = 1, message = "Loan amount must be greater than 0")
    @JsonProperty("loan_amount")
    private Long loanAmount;
    
    @NotNull(message = "Tenor month is required")
    @Min(value = 1, message = "Tenor month must be greater than 0")
    @JsonProperty("tenor_month")
    private Integer tenorMonth;
    
    @NotBlank(message = "Purpose is required")
    private String purpose;
}
