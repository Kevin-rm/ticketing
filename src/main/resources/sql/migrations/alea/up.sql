CREATE TABLE seat_pricing_v2
(
    id                   SERIAL PRIMARY KEY,
    unit_price           NUMERIC(10, 2) NOT NULL,
    available_seat_count INTEGER        NOT NULL CHECK ( available_seat_count >= 0 ),
    seat_count           INTEGER        NOT NULL,
    seat_id              INTEGER        NOT NULL,
    flight_id            INTEGER        NOT NULL,
    deadline             TIMESTAMP      NOT NULL,
    FOREIGN KEY (seat_id) REFERENCES seat (id),
    FOREIGN KEY (flight_id) REFERENCES flight (id)
);

ALTER TABLE reservation
    ADD COLUMN is_paid            BOOLEAN,
    ADD COLUMN seat_pricing_v2_id INTEGER;
