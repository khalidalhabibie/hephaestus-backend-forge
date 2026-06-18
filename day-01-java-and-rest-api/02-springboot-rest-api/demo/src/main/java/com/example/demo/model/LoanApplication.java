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

// {
//   "id": 1,
//   "customer_id": 1,
//   "loan_amount": 5000000,
//   "tenor_month": 12,
//   "purpose": "Modal usaha",
//   "status": "REJECTED"
// }