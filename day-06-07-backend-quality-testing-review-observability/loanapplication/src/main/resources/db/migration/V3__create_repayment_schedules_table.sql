CREATE TABLE repayment_schedules (
    uid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_application_uid UUID NOT NULL,
    installment_number INTEGER NOT NULL,
    due_date DATE NOT NULL,
    principal_amount NUMERIC(18, 2) NOT NULL,
    interest_amount NUMERIC(18, 2) NOT NULL,
    total_amount NUMERIC(18, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_repayment_schedule_loan_application
        FOREIGN KEY (loan_application_uid)
        REFERENCES loan_applications (uid),

    CONSTRAINT chk_installment_number_positive CHECK (installment_number > 0),
    CONSTRAINT chk_principal_amount_non_negative CHECK (principal_amount >= 0),
    CONSTRAINT chk_interest_amount_non_negative CHECK (interest_amount >= 0),
    CONSTRAINT chk_total_amount_non_negative CHECK (total_amount >= 0),
    CONSTRAINT chk_repayment_schedule_status CHECK (
    status IN ('UNPAID', 'PARTIALLY_PAID', 'PAID', 'OVERDUE', 'CANCELLED')
)
);

CREATE INDEX idx_repayment_schedules_loan_application_uid
    ON repayment_schedules (loan_application_uid);