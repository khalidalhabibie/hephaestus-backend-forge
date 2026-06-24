-- id
-- loan_application_id
-- installment_number
-- due_date
-- principal_amount
-- interest_amount
-- total_amount
-- status
-- created_at
-- updated_at

CREATE TABLE repayment_schedules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_application_id UUID NOT NULL REFERENCES loan_applications(id),
    installment_number INTEGER NOT NULL,
    due_date DATE NOT NULL,
    principal_amount NUMERIC(19,2) NOT NULL,
    interest_amount NUMERIC(19,2) NOT NULL,
    total_amount NUMERIC(19,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);