DROP TABLE IF EXISTS seat_pricing_v2;

ALTER TABLE reservation
    DROP COLUMN IF EXISTS is_paid,
    DROP COLUMN IF EXISTS seat_pricing_v2_id;
