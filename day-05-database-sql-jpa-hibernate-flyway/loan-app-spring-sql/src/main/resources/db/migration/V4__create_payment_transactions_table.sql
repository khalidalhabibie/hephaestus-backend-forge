CREATE TABLE payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    repayment_schedule_id BIGINT NOT NULL,
    payment_reference VARCHAR(100) NOT NULL,
    paid_amount NUMERIC(19,2) NOT NULL,
    paid_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'SUCCESS',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_schedule
        FOREIGN KEY (repayment_schedule_id)
        REFERENCES repayment_schedules(id)
);

CREATE INDEX idx_payment_schedule_id
    ON payment_transactions(repayment_schedule_id);

CREATE UNIQUE INDEX idx_payment_reference
    ON payment_transactions(payment_reference);