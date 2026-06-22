// Format JSON response data loan lengkap (termasuk customer di dalamnya).

package com.example.training.DTO;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanApplicationResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private String status;

    @JsonProperty("customer")
    private CustomerSummaryResponse customer;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
}