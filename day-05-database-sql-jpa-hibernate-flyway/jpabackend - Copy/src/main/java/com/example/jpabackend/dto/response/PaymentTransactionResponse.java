package com.example.jpabackend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.jpabackend.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentTransactionResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    private LocalDateTime paidAt;

    @JsonProperty("status")
    private PaymentStatus status;
}