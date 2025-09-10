CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       full_name VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       is_active BOOLEAN NOT NULL DEFAULT TRUE,
                       deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       role VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP DEFAULT NOW(),
                       updated_at TIMESTAMP DEFAULT NOW()
);


CREATE TABLE card (
                      id BIGSERIAL PRIMARY KEY,
                      card_number VARCHAR(16) NOT NULL UNIQUE,
                      expiry_date DATE NOT NULL,
                      status VARCHAR(50) NOT NULL,
                      amount NUMERIC(19,2) NOT NULL DEFAULT 0.00,
                      owner_id BIGINT NOT NULL,
                      created_at TIMESTAMP DEFAULT NOW(),
                      updated_at TIMESTAMP DEFAULT NOW(),
                      CONSTRAINT fk_card_owner FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE transactions (
                              id BIGSERIAL PRIMARY KEY,
                              from_card_id BIGINT NOT NULL,
                              to_card_id BIGINT NOT NULL,
                              amount NUMERIC(19,2) NOT NULL,
                              date TIMESTAMP NOT NULL DEFAULT NOW(),
                              status VARCHAR(50) NOT NULL,
                              created_at TIMESTAMP DEFAULT NOW(),
                              updated_at TIMESTAMP DEFAULT NOW(),
                              CONSTRAINT fk_transaction_from_card FOREIGN KEY (from_card_id) REFERENCES card (id) ON DELETE CASCADE,
                              CONSTRAINT fk_transaction_to_card FOREIGN KEY (to_card_id) REFERENCES card (id) ON DELETE CASCADE
);