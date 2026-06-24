package com.example.day2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoanApplication {
    private Long id;
    private Long customerId;
    private Long loanAmount;
    private Integer tenorMonth;
    private String purpose;
    private String status;
}
