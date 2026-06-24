package com.example.demo_day2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {

    private Long id;
    private Long customerId;
    private Double loanAmount;
    private Integer tenorMonth;
    private String purpose;
    private LoanStatus status;

}
