package com.example.spring_boot_database.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface LoanApplicationReportProjection {

    Long getLoanId();

    Long getCustomerId();

    String getCustomerName();

    String getNik();

    String getStatus();

    BigDecimal getLoanAmount();

    Integer getTenorMonth();

    String getPurpose();

    LocalDateTime getCreatedAt();

    BigDecimal getTotalScheduleAmount();

    BigDecimal getTotalPaidAmount();

    BigDecimal getOutstandingAmount();
}