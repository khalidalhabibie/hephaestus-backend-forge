package com.fif.exercise02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fif.exercise02.entity.LoanStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoanApplicationResponse {
    public String id;
    public String customerId;
    public Double amount;
    public Integer tenorMonth;
    public String purpose;
    public LoanStatus status;

}
