ALTER TABLE reservation
    ADD COLUMN seat_count INTEGER;

UPDATE reservation
SET seat_count = adult_count + child_count
WHERE TRUE;

ALTER TABLE reservation
    ALTER COLUMN seat_count SET NOT NULL;

ALTER TABLE reservation
    DROP COLUMN IF EXISTS adult_count;

ALTER TABLE reservation
    DROP COLUMN IF EXISTS child_count;

ALTER TABLE _settings DROP COLUMN child_max_age;

ALTER TABLE _settings DROP COLUMN child_discount_percentage;
