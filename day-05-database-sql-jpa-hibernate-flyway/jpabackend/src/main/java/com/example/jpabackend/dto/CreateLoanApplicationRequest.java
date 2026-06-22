package com.example.jpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateLoanApplicationRequest {

    @JsonProperty("customer_id")
    @NotNull
    private Long customerId;

    @JsonProperty("loan_amount")
    @NotNull
    @Positive
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    @Min(1)
    @NotNull
    private Integer tenorMonth;

    @NotBlank
    private String purpose;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public CreateLoanApplicationRequest(@NotNull Long customerId, @NotNull @Positive BigDecimal loanAmount,
            @NotNull Integer tenorMonth, @NotBlank String purpose) {
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.tenorMonth = tenorMonth;
        this.purpose = purpose;
    }

}
