package com.example.jpabackend.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class CreatePaymentTransactionRequest {

    @JsonProperty("repayment_schedule_id")
    @NotNull
    private Long repaymentScheduleId;

    @JsonProperty("payment_reference")
    @NotBlank
    private String paymentReference;

    @JsonProperty("paid_amount")
    @NotNull
    @Positive
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    @NotNull
    private ZonedDateTime paidAt;

    public Long getRepaymentScheduleId() {
        return repaymentScheduleId;
    }

    public void setRepaymentScheduleId(Long repaymentScheduleId) {
        this.repaymentScheduleId = repaymentScheduleId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public ZonedDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(ZonedDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public CreatePaymentTransactionRequest(@NotNull Long repaymentScheduleId, @NotBlank String paymentReference,
            @NotNull @Positive BigDecimal paidAmount, @NotNull ZonedDateTime paidAt) {
        this.repaymentScheduleId = repaymentScheduleId;
        this.paymentReference = paymentReference;
        this.paidAmount = paidAmount;
        this.paidAt = paidAt;
    }

}
