package com.example.main.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data masukan untuk memproses transaksi pembayaran cicilan")
public class RepaymentScheduleRequest {

    @NotNull(message = "repayment_schedule_id tidak boleh null")
    @JsonProperty("repayment_schedule_id")
    @Schema(description = "ID unik dari target lembar cicilan yang ingin dibayar", example = "1")
    private Long repaymentScheduleId;

    @NotBlank(message = "payment_reference tidak boleh kosong")
    @JsonProperty("payment_reference")
    @Schema(description = "Nomor bukti/referensi pembayaran unik dari mutasi bank atau PG", example = "PAY-20260621-001")
    private String paymentReference;

    @NotNull(message = "paid_amount tidak boleh null")
    @Positive(message = "paid_amount harus bernilai lebih besar dari 0")
    @JsonProperty("paid_amount")
    @Schema(description = "Total nominal uang tunai yang disetorkan untuk bayar cicilan", example = "933333")
    private BigDecimal paidAmount;

    @NotNull(message = "paid_at tidak boleh null")
    @JsonProperty("paid_at")
    @Schema(description = "Stempel waktu kapan pembayaran berhasil diselesaikan", example = "2026-06-21T10:00:00")
    private LocalDateTime paidAt;
}