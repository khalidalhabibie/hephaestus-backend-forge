package com.example.exercise.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.exercise.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonPropertyOrder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponse {
    @JsonProperty("loan_application_id")
    private Long id;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private int tenorMonth;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private LoanStatus status; 

    @JsonProperty("created_at")
    @NotBlank
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    @NotBlank
    private ZonedDateTime updatedAt;
}