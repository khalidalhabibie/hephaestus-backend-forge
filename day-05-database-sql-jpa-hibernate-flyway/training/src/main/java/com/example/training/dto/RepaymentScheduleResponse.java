package com.example.training.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepaymentScheduleResponse {

    private UUID id;

    @JsonProperty("loan_application_id")
    private UUID loanApplicationId;

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

    private String status;
}
