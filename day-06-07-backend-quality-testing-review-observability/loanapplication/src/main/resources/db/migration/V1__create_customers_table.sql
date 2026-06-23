CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE customers (
    uid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(100) NOT NULL,
    nik VARCHAR(16) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT uk_customer_nik UNIQUE (nik),
    CONSTRAINT uk_customer_email UNIQUE (email),
    CONSTRAINT chk_customer_nik_length CHECK (char_length(nik) = 16),
    CONSTRAINT chk_customer_phone_length CHECK (char_length(phone_number) BETWEEN 10 AND 15),
    CONSTRAINT uk_customer_phone_number UNIQUE (phone_number)
);