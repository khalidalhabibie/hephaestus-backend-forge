package com.andyana.exerciseday02.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponse {
    private Long id;
    
    @JsonProperty("customer_id")
    private Long customerId;
    
    @JsonProperty("loan_amount")
    private Long loanAmount;
    
    @JsonProperty("tenor_month")
    private Integer tenorMonth;
    
    private String purpose;
    private String status;
}
