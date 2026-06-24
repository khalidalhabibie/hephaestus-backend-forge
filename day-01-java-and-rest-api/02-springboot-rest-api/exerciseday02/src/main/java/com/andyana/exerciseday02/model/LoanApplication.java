package com.andyana.exerciseday02.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    private Long id;
    private Long customerId;
    private Long loanAmount;
    private Integer tenorMonth;
    private String purpose;
    private String status; 
}
