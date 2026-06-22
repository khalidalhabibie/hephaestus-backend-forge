package com.example.spring_boot_database.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.spring_boot_database.entity.StatusRepayment;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RepaymentScheduleResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("installment_number")
    private int installmentNumber;

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("principal_amount")
    private BigDecimal principalAmount;

    @JsonProperty("interest_amount")
    private BigDecimal interestAmount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("status")
    private StatusRepayment status;
    
}

    //  "id": 1,
    //   "installment_number": 1,
    //   "due_date": "2026-07-19",
    //   "principal_amount": 833333,
    //   "interest_amount": 100000,
    //   "total_amount": 933333,
    //   "status": "UNPAID"