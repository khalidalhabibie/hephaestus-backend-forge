package com.example.spring_boot_database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateLoanApplicationRequest {
    @JsonProperty("customer_id")
    @NotNull(message = "customer_id is required")
    private Long customerId;

    @JsonProperty("loan_amount")
    @NotNull(message = "loan_amount is required")
    @Positive(message = "loan_amount must be greater than 0")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    @NotNull(message = "tenor_month is required")
    @Positive(message = "tenor_month must be greater than 0")
    private Integer tenorMonth;

    @JsonProperty("purpose")
    @NotBlank(message = "purpose is required")
    private String purpose;
}


// {
//   "customer_id": 1,
//   "loan_amount": 10000000,
//   "tenor_month": 12,
//   "purpose": "Working capital"
// }