CREATE TABLE payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    repayment_schedule_id BIGINT NOT NULL,
    payment_reference VARCHAR(100) NOT NULL UNIQUE,
    paid_amount NUMERIC(15, 2) NOT NULL,
    paid_at TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_payment_transactions_schedule
        FOREIGN KEY (repayment_schedule_id)
        REFERENCES repayment_schedules(id)
);