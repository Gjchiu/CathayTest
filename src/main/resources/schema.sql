CREATE TABLE IF NOT EXISTS currency
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(3),
    currency_name VARCHAR(255),
    symbol VARCHAR(255),
    rate VARCHAR(255),
    description VARCHAR(255),
    rate_float DECIMAL(20, 4),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    last_sync_date TIMESTAMP
);
