package com.adnan.exercisespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanApplication {
    private Long id;
    private Long customerId;
    private Long loanAmount;
    private Integer tenorMonth;
    private String purpose;
    private String status; // SUBMITTED, APPROVED, REJECTED
}
