package com.example.demoSpringbootDatabase.dto;

import java.math.BigDecimal;

public interface LoanStatusSummaryDto {
    String getStatus();
    Long getTotalLoan();
    BigDecimal getTotalAmount();
}