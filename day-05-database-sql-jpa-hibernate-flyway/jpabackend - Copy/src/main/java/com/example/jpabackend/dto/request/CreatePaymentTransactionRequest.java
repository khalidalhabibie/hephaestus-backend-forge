package com.example.jpabackend.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreatePaymentTransactionRequest {
    @JsonProperty("repayment_schedule_id")
    private Long repaymentScheduleId;

    @JsonProperty("payment_reference")
    @NotBlank
    private String paymentReference;

    @JsonProperty("paid_amount")
    @Positive
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    private LocalDateTime paidAt;
}