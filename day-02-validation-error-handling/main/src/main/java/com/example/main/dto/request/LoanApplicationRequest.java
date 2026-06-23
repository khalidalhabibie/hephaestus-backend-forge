package com.example.main.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoanApplicationRequest {

    @NotNull(message = "Customer ID is required")
    @JsonProperty("customer_id")
    private Long customerId;

    @NotNull(message = "Loan amount is required")
    @Min(value = 1, message = "Loan amount must be greater than 0")
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @NotNull(message = "Tenor month is required")
    @Min(value = 1, message = "Tenor must be at least 1 month")
    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @NotBlank(message = "Purpose is required")
    private String purpose;
}