ALTER TABLE _settings
    ADD COLUMN child_max_age INTEGER NOT NULL DEFAULT 12;

ALTER TABLE _settings
    ADD COLUMN child_discount_percentage NUMERIC(5, 2) NOT NULL DEFAULT 50.00;

ALTER TABLE reservation
    ADD COLUMN adult_count INTEGER NOT NULL DEFAULT 0;

ALTER TABLE reservation
    ADD COLUMN child_count INTEGER NOT NULL DEFAULT 0;

UPDATE reservation SET adult_count = seat_count;

ALTER TABLE reservation
    DROP COLUMN IF EXISTS seat_count;
