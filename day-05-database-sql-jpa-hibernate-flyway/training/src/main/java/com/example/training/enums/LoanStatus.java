package com.example.training.enums;

public enum LoanStatus {

    SUBMITTED,
    APPROVED,
    REJECTED,
    DISBURSED,
    CLOSED;

    public boolean canTransitionTo(LoanStatus next) {
        return switch (this) {
            case SUBMITTED -> next == APPROVED || next == REJECTED;
            case APPROVED  -> next == DISBURSED;
            case DISBURSED -> next == CLOSED;
            case REJECTED, CLOSED -> false;
        };
    }

    public boolean isFinal() {
        return this == REJECTED || this == CLOSED;
    }
}
