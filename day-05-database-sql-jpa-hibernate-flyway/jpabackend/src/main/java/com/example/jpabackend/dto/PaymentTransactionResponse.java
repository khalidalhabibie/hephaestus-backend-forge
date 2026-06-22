package com.example.jpabackend.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
public class PaymentTransactionResponse {

    private Long id;

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("paid_at")
    private ZonedDateTime paidAt;

    private String status;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PaymentTransactionResponse(Long id, String paymentReference, BigDecimal paidAmount, ZonedDateTime paidAt,
            String status, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.paymentReference = paymentReference;
        this.paidAmount = paidAmount;
        this.paidAt = paidAt;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
