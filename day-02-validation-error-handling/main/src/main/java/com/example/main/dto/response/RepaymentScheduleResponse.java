package com.example.main.dto.response;

import com.example.main.enums.ScheduleStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objek struktur internal data jadwal cicilan")
public class RepaymentScheduleResponse {

    @Schema(description = "ID unik jadwal pembayaran", example = "1")
    private Long id;

    @JsonProperty("installment_number")
    @Schema(description = "Nomor urut cicilan", example = "1")
    private Integer installmentNumber;

    @JsonProperty("due_date")
    @Schema(description = "Tanggal jatuh tempo pembayaran cicilan", example = "2026-07-19")
    private LocalDate dueDate;

    @JsonProperty("principal_amount")
    @Schema(description = "Nominal angsuran pokok", example = "833333")
    private BigDecimal principalAmount;

    @JsonProperty("interest_amount")
    @Schema(description = "Nominal bunga cicilan", example = "100000")
    private BigDecimal interestAmount;

    @JsonProperty("total_amount")
    @Schema(description = "Total nominal angsuran (Pokok + Bunga)", example = "933333")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status pembayaran cicilan saat ini", example = "UNPAID")
    private ScheduleStatus status;
}