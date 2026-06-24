CREATE TABLE repayment_schedules (
    id BIGSERIAL PRIMARY KEY,
    loan_application_id BIGINT NOT NULL,
    installment_number INT,
    due_date DATE,
    principal_amount DECIMAL(19,2),
    interest_amount DECIMAL(19,2),
    total_amount DECIMAL(19,2),
    status VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_loan
        FOREIGN KEY (loan_application_id)
        REFERENCES loan_applications(id)
);