-- Tambah kolom untuk soft delete di tabel customers
ALTER TABLE customers ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE;

-- Membuat Index untuk optimasi performa query
CREATE INDEX idx_customers_nik ON customers(nik);
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_loan_applications_customer_id ON loan_applications(customer_id);
CREATE INDEX idx_loan_applications_status ON loan_applications(status);