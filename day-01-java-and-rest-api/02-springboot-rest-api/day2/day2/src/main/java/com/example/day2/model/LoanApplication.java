package com.example.day2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoanApplication {
    private String id;
    private String customerId;
    private Long amount;
    private String status;
}
