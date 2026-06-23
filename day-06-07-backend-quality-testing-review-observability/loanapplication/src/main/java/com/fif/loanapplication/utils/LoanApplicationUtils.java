package com.fif.loanapplication.utils;

import org.springframework.stereotype.Service;

import com.fif.loanapplication.entity.enums.LoanStatus;
import com.fif.loanapplication.exception.BadRequestException;

@Service
public class LoanApplicationUtils {
    // Helper to Parser Loan Status
    public LoanStatus parseLoanStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        try {
            return LoanStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException("Status loan tidak valid: " + status);
        }
    }

    public void validateStatusTransition(LoanStatus currentStatus, LoanStatus newStatus) {
        if (newStatus == null) {
            throw new BadRequestException("Status wajib diisi");
        }

        boolean isValidTransition = (currentStatus.equals(LoanStatus.SUBMITTED)
                && (newStatus.equals(LoanStatus.APPROVED)
                        || newStatus.equals(LoanStatus.REJECTED)))

                || (currentStatus.equals(LoanStatus.APPROVED)
                        && newStatus.equals(LoanStatus.DISBURSED))

                || (currentStatus.equals(LoanStatus.DISBURSED)
                        && newStatus.equals(LoanStatus.CLOSED));

        if (!isValidTransition) {
            throw new BadRequestException(
                    "Tidak boleh mengubah status dari " + currentStatus + " ke " + newStatus);
        }
    }
}
