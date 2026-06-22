package com.example.jpabackend.dto.response;

import java.math.BigDecimal;
import com.example.jpabackend.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
public class LoanApplicationResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private LoanStatus status;

    @JsonProperty("customer")
    private CustomerSummaryResponse customer;
}