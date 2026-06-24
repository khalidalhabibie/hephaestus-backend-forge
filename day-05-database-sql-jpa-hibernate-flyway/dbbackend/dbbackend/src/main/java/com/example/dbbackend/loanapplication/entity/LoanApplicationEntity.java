package com.example.dbbackend.loanapplication.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.example.dbbackend.customer.entity.CustomerEntity;
import com.example.dbbackend.repaymentschedule.entity.RepaymentScheduleEntity;
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
@Table(name = "loan_applications")
public class LoanApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

    @Column(name = "tenor_month", nullable = false)
    private Integer tenorMonth;

    private String purpose;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // relation ke customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    // relation ke repayment schedule
    @OneToMany(mappedBy = "loanApplication", fetch = FetchType.LAZY)
    private List<RepaymentScheduleEntity> repaymentSchedules;
}
