package com.example.day2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoanApplicationResponse {
    private String id;
    @JsonProperty("customer_id")
    private String customerId;
    private Long amount;
    private String status;
}
