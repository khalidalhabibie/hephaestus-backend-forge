CREATE TABLE IF NOT EXISTS loan_applications (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    loan_amount DECIMAL(15, 2) NOT NULL,
    tenor_month INTEGER NOT NULL,
    purpose VARCHAR(255),
    status VARCHAR(30) NOT NULL DEFAULT 'SUBMITTED',
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_loan_applications_customer 
        FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- CREATE INDEX idx_loan_applications_customer_id ON loan_applications(customer_id);
-- CREATE INDEX idx_loan_applications_status ON loan_applications(status);