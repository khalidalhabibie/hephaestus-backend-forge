package com.fif.loanapplication.exception;

import java.util.UUID;

public class RepaymentScheduleNotFoundException extends RuntimeException {
    public RepaymentScheduleNotFoundException(UUID uid) {

        super("Repayment data not found with unique identifier : " + uid);
    }

}
