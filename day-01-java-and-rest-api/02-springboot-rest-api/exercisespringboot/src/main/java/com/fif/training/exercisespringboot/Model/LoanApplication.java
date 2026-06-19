package com.fif.training.exercisespringboot.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoanApplication {
    private Long id;
    private Long customerId;
    private Double loanAmount;
    private Integer tenorMonth;
    private String purpose;
    private LoanStatus status; 
}