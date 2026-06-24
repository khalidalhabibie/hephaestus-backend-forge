package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoanApplication {
    private Long id;

    private Long customerId;

    private Long LoanAmount;

    private int tenorMonth;

    private String purpose;

    private ApplicationStatus status;
}
