package com.example.dbbackend.repaymentschedule.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.dbbackend.repaymentschedule.entity.RepaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentScheduleResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("installment_number")
    private Integer installmentNumber;

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("principal_amount")
    private BigDecimal principalAmount;

    @JsonProperty("interest_amount")
    private BigDecimal interestAmount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("status")
    private RepaymentStatus status;

}
