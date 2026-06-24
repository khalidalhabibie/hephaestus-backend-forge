package com.adnan.loanappspringsql.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.adnan.loanappspringsql.enums.LoanStatusEnum;

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
@Table(name = "loan_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplication {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "loan_amount", nullable = false)
        private BigDecimal loanAmount;

        @Column(name = "tenor_month", nullable = false)
        private Integer tenorMonth;

        @Column(nullable = false)
        private String purpose;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private LoanStatusEnum status;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private ZonedDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private ZonedDateTime updatedAt;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @OneToMany(mappedBy = "loanApplication", fetch = FetchType.LAZY)
        private List<RepaymentSchedule> repaymentSchedules = new ArrayList<>();
}
