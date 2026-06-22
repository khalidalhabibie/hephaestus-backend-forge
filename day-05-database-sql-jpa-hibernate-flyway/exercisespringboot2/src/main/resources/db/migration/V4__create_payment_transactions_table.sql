CREATE TABLE IF NOT EXISTS payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    repayment_schedule_id BIGINT NOT NULL,
    payment_reference VARCHAR(100) NOT NULL UNIQUE,
    paid_amount DECIMAL(15, 2) NOT NULL,
    paid_at TIMESTAMPTZ,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_payment_transactions_schedule 
        FOREIGN KEY (repayment_schedule_id) REFERENCES repayment_schedules(id)
);

-- CREATE INDEX idx_payment_transactions_schedule_id ON payment_transactions(repayment_schedule_id);   
-- CREATE INDEX idx_payment_transactions_reference ON payment_transactions(payment_reference);
-- CREATE INDEX idx_payment_transactions_status ON payment_transactions(status);                      