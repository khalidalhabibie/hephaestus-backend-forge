package com.example.demoSpringbootDatabase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreatePaymentTransactionRequest {
    @NotNull(message = "repayment_schedule_id is required")
    @JsonProperty("repayment_schedule_id")
    private Long repaymentScheduleId;

    @NotBlank(message = "payment_reference is required")
    @JsonProperty("payment_reference")
    private String paymentReference;

    @NotNull(message = "paid_amount is required")
    @Min(value = 1, message = "paid_amount must be greater than 0")
    @JsonProperty("paid_amount")
    private Long paidAmount;

    @NotNull(message = "paid_at is required")
    @JsonProperty("paid_at")
    private LocalDateTime paidAt;
}
