-- id
-- customer_id
-- loan_amount
-- tenor_month
-- purpose
-- status
-- created_at
-- updated_at


CREATE TABLE loan_applications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL REFERENCES customers(id),
    loan_amount NUMERIC(19,2) NOT NULL,
    tenor_month INTEGER NOT NULL,
    purpose VARCHAR(500) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);