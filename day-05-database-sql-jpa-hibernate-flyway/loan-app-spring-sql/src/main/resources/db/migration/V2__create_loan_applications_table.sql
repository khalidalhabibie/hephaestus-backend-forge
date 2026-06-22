CREATE TABLE loan_applications (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    loan_amount NUMERIC(19,2) NOT NULL,
    tenor_month INTEGER NOT NULL,
    purpose VARCHAR(500) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'SUBMITTED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_loan_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id)
);

CREATE INDEX idx_loan_customer_id
    ON loan_applications(customer_id);

CREATE INDEX idx_loan_status
    ON loan_applications(status);