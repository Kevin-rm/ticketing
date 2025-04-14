ALTER TABLE reservation
    ADD COLUMN seat_count INTEGER;

UPDATE reservation
SET seat_count = COALESCE(adult_count, 0) + COALESCE(child_count, 0);

ALTER TABLE reservation
    ALTER COLUMN seat_count SET NOT NULL;

ALTER TABLE reservation
    DROP COLUMN IF EXISTS adult_count;

ALTER TABLE reservation
    DROP COLUMN IF EXISTS child_count;

ALTER TABLE _settings DROP COLUMN child_max_age;

ALTER TABLE _settings DROP COLUMN child_discount_percentage;
