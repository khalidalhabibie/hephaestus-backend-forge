package com.example.jpabackend.entity;

import java.math.BigDecimal;

import com.example.jpabackend.enums.LoanStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "loan_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplicationEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(nullable = false)
    @Positive
    private BigDecimal loanAmount;

    @Column(nullable = false)
    @Positive
    private Integer tenorMonth;

    @Column(nullable = false)
    private String purpose;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
}