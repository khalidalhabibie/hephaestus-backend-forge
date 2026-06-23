package com.example.dbbackend.repaymentschedule.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.example.dbbackend.loanapplication.entity.LoanApplicationEntity;
import com.example.dbbackend.paymenttransaction.entity.PaymentTransactionEntity;
import jakarta.persistence.Column;
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
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "repayment_schedules")
public class RepaymentScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "installment_number")
    private Integer installmentNumber;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "principal_amount")
    private java.math.BigDecimal principalAmount;

    @Column(name = "interest_amount")
    private BigDecimal interestAmount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // relation ke loan application
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_id")
    private LoanApplicationEntity loanApplication;

    // relation ke payment transaction
    @OneToMany(mappedBy = "repaymentSchedule", fetch = FetchType.LAZY)
    private List<PaymentTransactionEntity> paymentTransactions;
}
