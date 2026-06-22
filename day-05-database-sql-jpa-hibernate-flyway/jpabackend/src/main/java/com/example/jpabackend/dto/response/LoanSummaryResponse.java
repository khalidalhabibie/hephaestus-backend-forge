package com.example.jpabackend.dto.response;

import java.math.BigDecimal;
import com.example.jpabackend.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanSummaryResponse {
    @JsonProperty("status")
    private LoanStatus status;
    @JsonProperty("total_loan")
    private Long totalLoan;
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
}