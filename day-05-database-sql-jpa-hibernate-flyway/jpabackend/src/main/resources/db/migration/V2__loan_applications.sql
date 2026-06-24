CREATE TABLE loan_applications (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    loan_amount NUMERIC(15,2) NOT NULL,
    tenor_month INTEGER NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_loan_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id)
);