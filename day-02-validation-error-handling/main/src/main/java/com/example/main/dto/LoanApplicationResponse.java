package com.example.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class LoanApplicationResponse {
    private Long id;
    
    @JsonProperty("customer_id")
    private Long customerId;
    
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;
    
    @JsonProperty("tenor_month")
    private Integer tenorMonth;
    
    private String purpose;
    private String status;

    public LoanApplicationResponse(Long id, Long customerId, BigDecimal loanAmount, Integer tenorMonth, String purpose, String status) {
        this.id = id;
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.tenorMonth = tenorMonth;
        this.purpose = purpose;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public BigDecimal getLoanAmount() { return loanAmount; }
    public void setLoanAmount(BigDecimal loanAmount) { this.loanAmount = loanAmount; }

    public Integer getTenorMonth() { return tenorMonth; }
    public void setTenorMonth(Integer tenorMonth) { this.tenorMonth = tenorMonth; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}