package com.example.jpabackend.dto.request;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanApplicationRequest {

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("loan_amount")
    @Positive
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    @Positive
    private Integer tenorMonth;

    @JsonProperty("purpose")
    private String purpose;
}