package com.example.demoSpringbootDatabase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateLoanApplicationRequest {
    @NotNull(message = "customer_id is required")
    @JsonProperty("customer_id")
    private Long customerId;

    @NotNull(message = "loan_amount is required")
    @Min(value = 1, message = "loan_amount must be greater than 0")
    @JsonProperty("loan_amount")
    private Long loanAmount;

    @NotNull(message = "tenor_month is required")
    @Min(value = 1, message = "tenor_month must be greater than 0")
    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @NotBlank(message = "purpose is required")
    private String purpose;
}