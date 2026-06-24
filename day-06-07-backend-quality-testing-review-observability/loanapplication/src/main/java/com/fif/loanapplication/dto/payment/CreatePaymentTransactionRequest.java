package com.fif.loanapplication.dto.payment;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentTransactionRequest {

    @NotNull(message = "Repayment schedule wajib diisi")
    @JsonProperty("repayment_schedule_uid")
    private UUID repaymentScheduleUid;

    @NotBlank(message = "Payment reference wajib diisi")
    @JsonProperty("payment_reference")
    private String paymentReference;

    @NotNull(message = "Paid amount wajib diisi")
    @DecimalMin(value = "1.00", message = "Paid amount harus lebih besar dari 0")
    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @NotNull(message = "Paid at wajib diisi")
    @JsonProperty("paid_at")
    private ZonedDateTime paidAt;
}