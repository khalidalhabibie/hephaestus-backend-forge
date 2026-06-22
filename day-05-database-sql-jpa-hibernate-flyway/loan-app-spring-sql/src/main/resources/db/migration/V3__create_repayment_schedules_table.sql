CREATE TABLE repayment_schedules (
    id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT NOT NULL,
    installment_number INTEGER NOT NULL,
    due_date DATE NOT NULL,
    principal_amount NUMERIC(19,2) NOT NULL,
    interest_amount NUMERIC(19,2) NOT NULL,
    total_amount NUMERIC(19,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'UNPAID',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_schedule_loan
        FOREIGN KEY (loan_application_id)
        REFERENCES loan_applications(id)
);

CREATE INDEX idx_schedule_loan_id
    ON repayment_schedules(loan_application_id);