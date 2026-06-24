CREATE TABLE IF NOT EXISTS payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    repayment_schedule_id BIGINT NOT NULL,
    payment_reference VARCHAR(255) NOT NULL UNIQUE,
    paid_amount NUMERIC(19,2) NOT NULL,
    paid_at TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_payment_schedule FOREIGN KEY (repayment_schedule_id) REFERENCES repayment_schedules(id)
);