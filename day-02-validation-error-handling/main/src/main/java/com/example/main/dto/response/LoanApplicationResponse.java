package com.example.main.dto.response;

import com.example.main.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoanApplicationResponse {
    private Long id;
    
    @JsonProperty("customer_id")
    private Long customerId;
    
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;
    
    @JsonProperty("tenor_month")
    private Integer tenorMonth;
    
    private String purpose;
    private LoanStatus status;
}