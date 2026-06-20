package com.example.training.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "loan_applications")
@Data
public class LoanApplicationEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal loan_amount;

    @Column(nullable = false)
    private Integer tenor_month;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private OffsetDateTime created_at;

    @Column(nullable = false)
    private OffsetDateTime updated_at;

    @OneToMany(mappedBy = "loanApplication")
    private List<RepaymentScheduleEntity> repaymentSchedules;
}

// id
// customer_id
// loan_amount
// tenor_month
// purpose
// status
// created_at
// updated_at