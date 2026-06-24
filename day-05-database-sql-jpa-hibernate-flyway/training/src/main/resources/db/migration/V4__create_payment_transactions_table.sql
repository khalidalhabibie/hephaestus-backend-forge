-- -- id
-- repayment_schedule_id
-- payment_reference
-- paid_amount
-- paid_at
-- status
-- created_at
-- updated_at

CREATE TABLE payment_transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    repayment_schedule_id UUID NOT NULL REFERENCES repayment_schedules(id),
    payment_reference VARCHAR(100) NOT NULL UNIQUE,
    paid_amount NUMERIC(19,2) NOT NULL,
    paid_at TIMESTAMPTZ,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);