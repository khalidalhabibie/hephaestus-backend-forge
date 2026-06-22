package com.example.exercise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.exercise.enums.LoanStatus;

@Entity
@Getter
@Setter
@Table(name = "loan_applications")
public class LoanApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;
    
    @Column(name = "tenor_month", nullable = false)
    private Integer tenorMonth;
    
    @Column(name = "purpose", length = 255)
    private String purpose;
    
    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
    
    @OneToMany(mappedBy = "loanApplication", fetch = FetchType.LAZY)
    private List<RepaymentScheduleEntity> repaymentSchedule = new ArrayList<>();
}