package com.fif.loanapplication.entity;

import com.fif.loanapplication.entity.common.BaseEntity;
import com.fif.loanapplication.entity.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "payment_transactions")
public class PaymentTransactionEntity extends BaseEntity {

    @NotNull(message = "Repayment schedule wajib diisi")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repayment_schedule_uid", referencedColumnName = "uid", nullable = false)
    private RepaymentScheduleEntity repaymentSchedule;

    @NotBlank(message = "Payment reference wajib diisi")
    @Column(name = "payment_reference", nullable = false, length = 100)
    private String paymentReference;

    @NotNull(message = "Paid amount wajib diisi")
    @Column(name = "paid_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal paidAmount;

    @NotNull(message = "Paid at wajib diisi")
    @Column(name = "paid_at", nullable = false)
    private ZonedDateTime paidAt;

    @NotNull(message = "Status wajib diisi")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;
}