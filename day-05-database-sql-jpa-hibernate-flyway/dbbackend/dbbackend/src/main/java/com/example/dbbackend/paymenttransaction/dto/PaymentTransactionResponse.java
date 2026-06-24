package com.example.dbbackend.paymenttransaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.dbbackend.paymenttransaction.entity.PaymentTransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private PaymentTransactionStatus status;
}
