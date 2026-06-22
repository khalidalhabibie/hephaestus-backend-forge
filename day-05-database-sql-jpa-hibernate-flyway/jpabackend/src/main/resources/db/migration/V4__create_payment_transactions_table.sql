CREATE TABLE payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    repayment_schedule_id BIGINT NOT NULL,
    payment_reference VARCHAR(100) NOT NULL,
    paid_amount NUMERIC(15,2) NOT NULL,
    paid_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_payment_schedule
        FOREIGN KEY (repayment_schedule_id)
        REFERENCES repayment_schedules(id)
);