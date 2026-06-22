package com.example.spring_boot_database.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreatePaymentTransactionRequest {
    @JsonProperty("repayment_schedule_id")
    @NotNull(message = "repayment_schedule_id is required")
    private Long repaymentSchedule_id;
    
    @JsonProperty("payment_reference")
    @NotBlank(message = "payment_reference is required")
    private String paymentReference;

    @JsonProperty("paid_amount")
    @NotNull(message = "paid_amount is required")
    @Positive(message = "paid_amount must be greater than 0")
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotBlank(message = "paid_at is required")
    private LocalDateTime paidAt;
}


// {
//   "repayment_schedule_id": 1,
//   "payment_reference": "PAY-20260619-001",
//   "paid_amount": 950000,
//   "paid_at": "2026-06-19T10:00:00"
// }

