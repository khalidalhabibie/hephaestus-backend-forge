package com.example.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class LoanApplicationRequest {

    @NotNull(message = "Customer ID is required")
    @JsonProperty("customer_id")
    private Long customerId;

    @NotNull(message = "Loan amount is required")
    @Min(value = 1, message = "Loan amount must be greater than 0")
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @NotNull(message = "Tenor month is required")
    @Min(value = 1, message = "Tenor must be at least 1 month")
    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @NotBlank(message = "Purpose is required")
    private String purpose;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public BigDecimal getLoanAmount() { return loanAmount; }
    public void setLoanAmount(BigDecimal loanAmount) { this.loanAmount = loanAmount; }

    public Integer getTenorMonth() { return tenorMonth; }
    public void setTenorMonth(Integer tenorMonth) { this.tenorMonth = tenorMonth; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}