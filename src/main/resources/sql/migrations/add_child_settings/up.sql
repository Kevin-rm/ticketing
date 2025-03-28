ALTER TABLE _settings
    ADD COLUMN child_max_age INTEGER NOT NULL DEFAULT 12;

ALTER TABLE _settings
    ADD COLUMN child_discount_percentage NUMERIC(5, 2) NOT NULL DEFAULT 50.00;
