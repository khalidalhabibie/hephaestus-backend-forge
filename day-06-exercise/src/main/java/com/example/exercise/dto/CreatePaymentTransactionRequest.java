package com.example.exercise.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentTransactionRequest {

    @NotBlank
    @JsonProperty("repayment_schedule_id")
    private Long repaymentScheduleId;
    
    @NotBlank
    @JsonProperty("payment_reference")
    private String paymentReference;

    @NotBlank
    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @NotBlank
    @JsonProperty("paid_at")
    private ZonedDateTime paidAt;
}
