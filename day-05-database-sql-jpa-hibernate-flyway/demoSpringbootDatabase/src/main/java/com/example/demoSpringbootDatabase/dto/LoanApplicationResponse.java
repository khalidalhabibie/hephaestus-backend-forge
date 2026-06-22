package com.example.demoSpringbootDatabase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class LoanApplicationResponse {
    private Long id;
    @JsonProperty("loan_amount")
    private Long loanAmount;
    @JsonProperty("tenor_month")
    private Integer tenorMonth;
    private String purpose;
    private String status;
    private CustomerSummaryResponse customer;
}
