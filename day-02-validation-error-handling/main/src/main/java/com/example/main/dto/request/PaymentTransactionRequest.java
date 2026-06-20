package com.example.main.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Request body untuk melakukan transaksi pembayaran cicilan")
public class PaymentTransactionRequest {

    @NotNull(message = "Repayment schedule ID cannot be null")
    @JsonProperty("repayment_schedule_id")
    @Schema(description = "ID unik dari jadwal cicilan yang ingin dibayar", example = "1")
    private Long repaymentScheduleId;

    @NotNull(message = "Payment reference cannot be null")
    @JsonProperty("payment_reference")
    @Schema(description = "Nomor referensi pembayaran unik dari payment gateway / bank", example = "PAY-20260619-001")
    private String paymentReference;

    @NotNull(message = "Paid amount cannot be null")
    @Positive(message = "Paid amount must be greater than zero")
    @JsonProperty("paid_amount")
    @Schema(description = "Nominal uang yang dibayarkan", example = "933333")
    private BigDecimal paidAmount;

    @NotNull(message = "Paid at timestamp cannot be null")
    @JsonProperty("paid_at")
    @Schema(description = "Waktu pelunasan transaksi dilakukan", example = "2026-06-19T10:00:00")
    private LocalDateTime paidAt;
}