CREATE TABLE loan_applications (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    loan_amount NUMERIC(19,2) NOT NULL,
    tenor_month INTEGER NOT NULL,
    purpose VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_loan_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id)
);