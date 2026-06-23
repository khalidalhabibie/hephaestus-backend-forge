CREATE TABLE payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    repayment_schedule_id BIGINT NOT NULL,
    payment_reference VARCHAR(100),
    paid_amount DECIMAL(19,2),
    paid_at TIMESTAMP,
    status VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_schedule
        FOREIGN KEY (repayment_schedule_id)
        REFERENCES repayment_schedules(id)
);