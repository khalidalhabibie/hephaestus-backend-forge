package com.example.jpabackend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
@Entity
@Table(name = "payment_transactions")
public class PaymentTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentReference;
    private BigDecimal paidAmount;
    private ZonedDateTime paidAt;

    private String status;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public PaymentTransactionEntity(Long id, String paymentReference, BigDecimal paidAmount, ZonedDateTime paidAt,
            String status, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.paymentReference = paymentReference;
        this.paidAmount = paidAmount;
        this.paidAt = paidAt;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PaymentTransactionEntity() {

    }

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repayment_schedule_id")
    private RepaymentScheduleEntity repaymentSchedule;

    public RepaymentScheduleEntity getRepaymentSchedule() {
        return repaymentSchedule;
    }

    public void setRepaymentSchedule(RepaymentScheduleEntity repaymentSchedule) {
        this.repaymentSchedule = repaymentSchedule;
    }
}