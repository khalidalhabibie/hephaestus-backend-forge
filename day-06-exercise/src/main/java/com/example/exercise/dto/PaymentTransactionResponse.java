package com.example.exercise.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionResponse {
    @NotBlank
    @JsonProperty("id")
    private Long id;

    // @NotBlank
    // @JsonProperty("repayment_schedule_id")
    // private Long repaymentScheduleId;

    @NotBlank
    @JsonProperty("payment_reference")
    private String paymentReference;

    @NotBlank
    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @NotBlank
    @JsonProperty("paid_at")
    private ZonedDateTime paidAt;

    @NotBlank
    private String status;

    @JsonProperty("created_at")
    @NotBlank
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    @NotBlank
    private ZonedDateTime updatedAt;
}
