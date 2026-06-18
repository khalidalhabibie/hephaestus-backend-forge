package com.fif.training.exercisespringboot.DTO.Loan;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoanApplicationResponse(
                Long id,

                @JsonProperty("customer_id") Long customerId,

                @JsonProperty("loan_amount") Integer loanAmount,

                @JsonProperty("tenor_month") Integer tenorMonth,

                String purpose,

                String status,

                @JsonProperty("created_at") ZonedDateTime createdAt,

                @JsonProperty("updated_at") ZonedDateTime updatedAt,

                @JsonProperty("approved_at") ZonedDateTime approvedAt,

                @JsonProperty("approved_by") String approvedBy) {
}