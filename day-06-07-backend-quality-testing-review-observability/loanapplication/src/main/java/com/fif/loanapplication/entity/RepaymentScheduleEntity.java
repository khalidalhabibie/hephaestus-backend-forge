package com.fif.loanapplication.entity;

import com.fif.loanapplication.entity.common.BaseEntity;
import com.fif.loanapplication.entity.enums.RepaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "repayment_schedules")
public class RepaymentScheduleEntity extends BaseEntity {

    @NotNull(message = "Loan application wajib diisi")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_uid", referencedColumnName = "uid", nullable = false)
    private LoanApplicationEntity loanApplication;

    @NotNull(message = "Installment number wajib diisi")
    @Min(value = 1, message = "Installment number minimal 1")
    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;

    @NotNull(message = "Due date wajib diisi")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @NotNull(message = "Principal amount wajib diisi")
    @Column(name = "principal_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal principalAmount;

    @NotNull(message = "Interest amount wajib diisi")
    @Column(name = "interest_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal interestAmount;

    @NotNull(message = "Total amount wajib diisi")
    @Column(name = "total_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalAmount;

    @NotNull(message = "Status wajib diisi")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private RepaymentStatus status = RepaymentStatus.UNPAID;

    @OneToMany(mappedBy = "repaymentSchedule")
    @Builder.Default
    private List<PaymentTransactionEntity> paymentTransactions = new ArrayList<>();
}