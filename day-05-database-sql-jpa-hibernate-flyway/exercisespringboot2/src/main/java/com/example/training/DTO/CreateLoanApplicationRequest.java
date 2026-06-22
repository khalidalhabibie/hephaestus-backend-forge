// Terima input JSON create loan + validasi.

package com.example.training.DTO;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateLoanApplicationRequest {

    @NotNull(message = "customer_id is required")
    @JsonProperty("customer_id")
    private Long customerId;

    @NotNull(message = "loan_amount is required")
    @Positive(message = "loan_amount must be greater than 0")
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @NotNull(message = "tenor_month is required")
    @Min(value = 1, message = "tenor_month minimum 1")
    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @NotBlank(message = "purpose is required")
    @JsonProperty("purpose")
    private String purpose;
}