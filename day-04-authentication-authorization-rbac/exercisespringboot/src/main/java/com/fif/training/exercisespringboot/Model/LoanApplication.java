package com.fif.training.exercisespringboot.Model;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {
    Long id;
    Long customerId;
    Integer loanAmount;
    Integer tenorMonth;
    String purpose;
    LoanStatus status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime approvedAt;
    private String approvedBy;

}
