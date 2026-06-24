package com.example.jpabackend.dto.response;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
public class OutstandingCustomerResponse {
    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("outstanding_amount")
    private BigDecimal outstandingAmount;
}