package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLoanApplicationRequest {
	@JsonProperty("customer_id")
	@NotNull(message = "customer_id is required")
    private Long customerId;

    @JsonProperty("loan_amount")
	@NotNull(message = "loan_amount is required")
    private Long loanAmount;

    @JsonProperty("tenor_month")
	@NotNull(message = "tenor_month is required")
    private Integer tenorMonth;

    @JsonProperty("purpose")
	@NotBlank(message = "purpose is required")
    private String purpose;
}
