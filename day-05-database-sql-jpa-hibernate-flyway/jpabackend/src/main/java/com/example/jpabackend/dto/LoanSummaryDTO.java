package com.example.jpabackend.dto;

import java.math.BigDecimal;

public interface LoanSummaryDTO {
    String getStatus();

    BigDecimal getTotal();
}