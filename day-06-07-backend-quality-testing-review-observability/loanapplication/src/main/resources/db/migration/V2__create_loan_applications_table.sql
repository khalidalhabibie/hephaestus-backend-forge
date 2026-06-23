CREATE TABLE loan_applications (
    uid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_uid UUID NOT NULL,
    loan_amount NUMERIC(18, 2) NOT NULL,
    tenor_month INTEGER NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_loan_application_customer
        FOREIGN KEY (customer_uid)
        REFERENCES customers (uid),

    CONSTRAINT chk_loan_amount_positive CHECK (loan_amount > 0),
    CONSTRAINT chk_tenor_month_positive CHECK (tenor_month > 0),
    CONSTRAINT chk_loan_status CHECK (
    status IN ('SUBMITTED', 'APPROVED', 'REJECTED', 'DISBURSED', 'CLOSED')
)
);

CREATE INDEX idx_loan_applications_customer_uid
    ON loan_applications (customer_uid);