package com.fif.loanapplication.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fif.loanapplication.entity.common.BaseEntity;
import com.fif.loanapplication.entity.enums.LoanStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "loan_applications")
public class LoanApplicationEntity extends BaseEntity {

    @NotNull(message = "Customer wajib diisi")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_uid", referencedColumnName = "uid", nullable = false)
    private CustomerEntity customer;

    @NotNull(message = "Loan amount wajib diisi")
    @DecimalMin(value = "1.00", message = "Loan Amount harus lebih besar dari 0!")
    @Column(name = "loan_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal loanAmount;

    @NotNull(message = "Tenor month wajib diisi")
    @Min(value = 1, message = "Tenor month minimal 1 bulan")
    @Column(name = "tenor_month", nullable = false)
    private Integer tenorMonth;

    @NotBlank(message = "Purpose wajib diisi")
    @Column(name = "purpose", nullable = false, length = 255)
    private String purpose;

    @NotNull(message = "Status wajib diisi")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private LoanStatus status = LoanStatus.SUBMITTED;

    // Relationship
    @OneToMany(mappedBy = "loanApplication")
    @Builder.Default
    private List<RepaymentScheduleEntity> repaymentSchedules = new ArrayList<>();
}
