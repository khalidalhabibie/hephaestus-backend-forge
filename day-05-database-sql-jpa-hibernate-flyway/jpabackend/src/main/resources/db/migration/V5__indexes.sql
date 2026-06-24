CREATE INDEX idx_customers_nik ON customers(nik);
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_loan_customer_id ON loan_applications(customer_id);
CREATE INDEX idx_loan_status ON loan_applications(status);
CREATE INDEX idx_loan_created_at ON loan_applications(created_at);
CREATE INDEX idx_repayment_status ON repayment_schedules(status);
CREATE INDEX idx_repayment_loan_id ON repayment_schedules(loan_application_id);
CREATE INDEX idx_payment_schedule_id ON payment_transactions(repayment_schedule_id);