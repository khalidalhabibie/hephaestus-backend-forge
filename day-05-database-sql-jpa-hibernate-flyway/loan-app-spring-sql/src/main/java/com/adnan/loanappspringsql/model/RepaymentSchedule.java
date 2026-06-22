package com.adnan.loanappspringsql.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.adnan.loanappspringsql.enums.RepaymentStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repayment_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepaymentSchedule {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "installment_number", nullable = false)
        private Integer installmentNumber;

        @Column(name = "due_date", nullable = false)
        private LocalDate dueDate;

        @Column(name = "principal_amount", nullable = false)
        private BigDecimal principalAmount;

        @Column(name = "interest_amount", nullable = false)
        private BigDecimal interestAmount;

        @Column(name = "total_amount", nullable = false)
        private BigDecimal totalAmount;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private RepaymentStatusEnum status;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private ZonedDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private ZonedDateTime updatedAt;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "loan_application_id", nullable = false)
        private LoanApplication loanApplication;

        @OneToMany(mappedBy = "repaymentSchedule", fetch = FetchType.LAZY)
        private List<PaymentTransaction> paymentTransactions = new ArrayList<>();
}
