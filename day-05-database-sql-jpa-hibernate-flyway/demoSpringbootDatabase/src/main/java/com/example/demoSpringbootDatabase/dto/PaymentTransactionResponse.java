package com.example.demoSpringbootDatabase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentTransactionResponse {
    private Long id;
    @JsonProperty("repayment_schedule_id")
    private Long repaymentScheduleId;
    @JsonProperty("payment_reference")
    private String paymentReference;
    @JsonProperty("paid_amount")
    private Long paidAmount;
    @JsonProperty("paid_at")
    private LocalDateTime paidAt;
    private String status;
}
