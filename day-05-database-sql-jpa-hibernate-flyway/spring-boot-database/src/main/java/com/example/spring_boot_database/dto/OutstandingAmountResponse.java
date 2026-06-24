package com.example.spring_boot_database.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutstandingAmountResponse {

    private Long customerId;

    private BigDecimal outstandingAmount;
}