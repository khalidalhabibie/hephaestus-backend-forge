CREATE TABLE payment_transactions (
    uid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    repayment_schedule_uid UUID NOT NULL,
    payment_reference VARCHAR(100) NOT NULL,
    paid_amount NUMERIC(18, 2) NOT NULL,
    paid_at TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_payment_transaction_repayment_schedule
        FOREIGN KEY (repayment_schedule_uid)
        REFERENCES repayment_schedules (uid),

    CONSTRAINT uk_payment_reference UNIQUE (payment_reference),
    CONSTRAINT chk_paid_amount_positive CHECK (paid_amount > 0),
    CONSTRAINT chk_payment_transaction_status CHECK (
        status IN ('PENDING', 'SUCCESS', 'FAILED', 'CANCELLED')
    )
);

CREATE INDEX idx_payment_transactions_repayment_schedule_uid
    ON payment_transactions (repayment_schedule_uid);