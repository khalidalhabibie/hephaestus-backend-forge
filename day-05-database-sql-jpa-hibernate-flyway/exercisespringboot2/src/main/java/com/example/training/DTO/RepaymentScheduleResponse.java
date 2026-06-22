// Format JSON response data repayment schedule.

package com.example.training.DTO;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepaymentScheduleResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("installment_number")
    private Integer installmentNumber;

    @JsonProperty("due_date")
    private ZonedDateTime dueDate;

    @JsonProperty("principal_amount")
    private BigDecimal principalAmount;

    @JsonProperty("interest_amount")
    private BigDecimal interestAmount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
}