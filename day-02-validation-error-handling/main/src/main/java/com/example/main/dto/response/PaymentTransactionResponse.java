package com.example.main.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Objek respons data hasil transaksi pembayaran")
public class PaymentTransactionResponse {

    @Schema(description = "ID unik transaksi pembayaran", example = "12")
    private Long id;

    @JsonProperty("repayment_schedule_id")
    @Schema(description = "ID jadwal cicilan terkait", example = "1")
    private Long repaymentScheduleId;

    @JsonProperty("payment_reference")
    @Schema(description = "Nomor referensi pembayaran", example = "PAY-20260619-001")
    private String paymentReference;

    @JsonProperty("paid_amount")
    @Schema(description = "Nominal yang berhasil dibayarkan", example = "933333")
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    @Schema(description = "Waktu bayar berhasil dicatat", example = "2026-06-19T10:00:00")
    private LocalDateTime paidAt;

    @Schema(description = "Status akhir transaksi (e.g. SUCCESS, FAILED)", example = "SUCCESS")
    private String status;
}