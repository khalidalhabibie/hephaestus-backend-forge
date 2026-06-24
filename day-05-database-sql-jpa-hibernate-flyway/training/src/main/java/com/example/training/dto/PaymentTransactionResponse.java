package com.example.training.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentTransactionResponse {

    private UUID id;

    @JsonProperty("repayment_schedule_id")
    private UUID repaymentScheduleId;

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    private OffsetDateTime paidAt;

    private String status;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}
