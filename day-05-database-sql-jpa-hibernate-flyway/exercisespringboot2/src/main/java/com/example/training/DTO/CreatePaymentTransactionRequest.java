// Terima input JSON create payment + validasi.

package com.example.training.DTO;

import java.math.BigDecimal;
import java.time.ZonedDateTime;



import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreatePaymentTransactionRequest {

    @NotNull(message = "repayment_schedule_id is required")
    @JsonProperty("repayment_schedule_id")
    private Long repaymentScheduleId;

    @NotBlank(message = "payment_reference is required")
    @JsonProperty("payment_reference")
    private String paymentReference;

    @NotNull(message = "paid_amount is required")
    @Positive(message = "paid_amount must be greater than 0")
    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    private ZonedDateTime paidAt;
}