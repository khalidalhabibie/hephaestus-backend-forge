package com.example.training.dto;

import com.example.training.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("repayment_schedule_id")
    private Long repaymentScheduleId;

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    private ZonedDateTime paidAt;

    @JsonProperty("status")
    private PaymentStatus status;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
}
