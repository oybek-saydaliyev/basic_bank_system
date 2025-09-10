CREATE INDEX idx_users_username ON users(username);

CREATE INDEX idx_card_owner_id ON card(owner_id);
CREATE INDEX idx_card_card_number ON card(card_number);

CREATE INDEX idx_transactions_from_card_id ON transactions(from_card_id);
CREATE INDEX idx_transactions_to_card_id ON transactions(to_card_id);