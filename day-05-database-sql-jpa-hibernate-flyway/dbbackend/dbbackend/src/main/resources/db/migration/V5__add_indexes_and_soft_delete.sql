
ALTER TABLE customers ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE NOT NULL;

CREATE UNIQUE INDEX idx_customers_nik ON customers(nik) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX idx_customers_email ON customers(email) WHERE is_deleted = FALSE;
CREATE INDEX idx_loan_applications_customer_id ON loan_applications(customer_id);
CREATE INDEX idx_loan_applications_status ON loan_applications(status);
CREATE INDEX idx_loan_applications_created_at ON loan_applications(created_at);
CREATE INDEX idx_repayment_schedules_status ON repayment_schedules(status);