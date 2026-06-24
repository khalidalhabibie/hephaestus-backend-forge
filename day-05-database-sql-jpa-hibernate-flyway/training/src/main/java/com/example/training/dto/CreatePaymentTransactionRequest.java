package com.example.training.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreatePaymentTransactionRequest {

    @JsonProperty("repayment_schedule_id")
    @NotNull
    private UUID repaymentScheduleId;

    @JsonProperty("payment_reference")
    @NotBlank
    private String paymentReference;

    @JsonProperty("paid_amount")
    @NotNull
    @Positive
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    @NotNull
    private OffsetDateTime paidAt;
}
