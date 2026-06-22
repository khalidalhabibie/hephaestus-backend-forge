CREATE TABLE IF NOT EXISTS repayment_schedules (
    id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT NOT NULL,
    installment_number INTEGER NOT NULL,
    due_date TIMESTAMPTZ NOT NULL,
    principal_amount DECIMAL(15, 2) NOT NULL,
    interest_amount DECIMAL(15, 2) NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'UNPAID',
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_repayment_schedules_loan 
        FOREIGN KEY (loan_application_id) REFERENCES loan_applications(id),
    CONSTRAINT uq_repayment_schedule_installment 
        UNIQUE (loan_application_id, installment_number)
);

-- CREATE INDEX idx_repayment_schedules_loan_id ON repayment_schedules(loan_application_id);
-- CREATE INDEX idx_repayment_schedules_status ON repayment_schedules(status);