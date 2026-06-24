package com.example.exercise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.exercise.enums.ScheduleStatus;

@Entity
@Getter
@Setter
@Table(name = "repayment_schedules")
public class RepaymentScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;
    
    @Column(name = "due_date", nullable = false)
    private ZonedDateTime dueDate;
    
    @Column(name = "principal_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal principalAmount;

    @Column(name = "interest_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal interestAmount;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_id", nullable = false)
    private LoanApplicationEntity loanApplication;

    @OneToMany(mappedBy = "repaymentSchedule", fetch = FetchType.LAZY)
    private List<PaymentTransactionEntity> paymentTransactions = new ArrayList<>();
}