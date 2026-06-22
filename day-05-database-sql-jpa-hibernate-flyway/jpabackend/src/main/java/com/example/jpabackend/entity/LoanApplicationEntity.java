package com.example.jpabackend.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
@Entity
@Table(name = "loan_applications", indexes = {
        @Index(name = "idx_customer_id", columnList = "customer_id"),
        @Index(name = "idx_status", columnList = "status")
})
public class LoanApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal loanAmount;
    private Integer tenorMonth;
    private String purpose;

    private String status; // nanti bisa enum

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public LoanApplicationEntity(Long id, BigDecimal loanAmount, Integer tenorMonth, String purpose, String status,
            ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.loanAmount = loanAmount;
        this.tenorMonth = tenorMonth;
        this.purpose = purpose;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LoanApplicationEntity() {

    }

    // relasi ke customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    // relasi ke repayment
    @OneToMany(mappedBy = "loanApplication", fetch = FetchType.LAZY)
    private List<RepaymentScheduleEntity> schedules;

    public List<RepaymentScheduleEntity> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<RepaymentScheduleEntity> schedules) {
        this.schedules = schedules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTenorMonth() {
        return tenorMonth;
    }

    public void setTenorMonth(Integer tenorMonth) {
        this.tenorMonth = tenorMonth;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}