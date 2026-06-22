CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    nik VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

INSERT INTO customers (
    full_name,
    nik,
    email,
    phone_number,
    created_at,
    updated_at
) VALUES
(
    'Budi Santoso',
    '3173010101900001',
    'budi.santoso@example.com',
    '081234567890',
    NOW(),
    NOW()
),
(
    'Siti Rahmawati',
    '3173010202900002',
    'siti.rahmawati@example.com',
    '081234567891',
    NOW(),
    NOW()
),
(
    'Andi Wijaya',
    '3173010303900003',
    'andi.wijaya@example.com',
    '081234567892',
    NOW(),
    NOW()
);
