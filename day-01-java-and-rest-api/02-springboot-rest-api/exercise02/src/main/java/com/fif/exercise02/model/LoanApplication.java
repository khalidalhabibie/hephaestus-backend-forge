package com.fif.exercise02.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {
    public String id;
    public String customerId;
    public Double amount;
    
    public Integer tenorMonth;
    public String purpose;
    public String status;

}
