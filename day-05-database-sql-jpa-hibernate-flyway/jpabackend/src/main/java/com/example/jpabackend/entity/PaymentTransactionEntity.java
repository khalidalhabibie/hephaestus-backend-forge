package com.example.jpabackend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.jpabackend.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_transactions")
public class PaymentTransactionEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repayment_schedule_id", nullable = false)
    private RepaymentScheduleEntity repaymentSchedule;

    @Column(unique = true, nullable = false)
    private String paymentReference;

    @Column(nullable = false)
    private BigDecimal paidAmount;

    private LocalDateTime paidAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}