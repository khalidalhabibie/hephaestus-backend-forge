package com.example.jpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoanApplicationResponse {

    private Long id;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    private String purpose;
    private String status;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;

    private CustomerSummaryResponse customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CustomerSummaryResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerSummaryResponse customer) {
        this.customer = customer;
    }

    public LoanApplicationResponse(Long id, BigDecimal loanAmount, Integer tenorMonth, String purpose, String status,
            ZonedDateTime createdAt, ZonedDateTime updatedAt, CustomerSummaryResponse customer) {
        this.id = id;
        this.loanAmount = loanAmount;
        this.tenorMonth = tenorMonth;
        this.purpose = purpose;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customer = customer;
    }

}
