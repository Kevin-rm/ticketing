CREATE TABLE plane
(
    id               SERIAL PRIMARY KEY,
    model            VARCHAR(75) NOT NULL,
    manufacture_date DATE        NOT NULL
);

CREATE TABLE city
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(75) NOT NULL
);

CREATE TABLE flight
(
    id                  SERIAL PRIMARY KEY,
    departure_timestamp TIMESTAMP NOT NULL,
    departure_city_id   INTEGER   NOT NULL,
    arrival_city_id     INTEGER   NOT NULL,
    plane_id            INTEGER   NOT NULL,
    FOREIGN KEY (departure_city_id) REFERENCES city (id),
    FOREIGN KEY (arrival_city_id) REFERENCES city (id),
    FOREIGN KEY (plane_id) REFERENCES plane (id)
);

CREATE TABLE seat_type
(
    id          SERIAL PRIMARY KEY,
    designation VARCHAR(75) NOT NULL
);

CREATE TABLE _user
(
    id        SERIAL PRIMARY KEY,
    firstname VARCHAR(75)  NOT NULL,
    lastname  VARCHAR(75)  NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL
);

CREATE TABLE _role
(
    id    SERIAL PRIMARY KEY,
    value VARCHAR(75) NOT NULL
);

CREATE TABLE user_role
(
    id      SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    UNIQUE (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES _user (id),
    FOREIGN KEY (role_id) REFERENCES _role (id)
);

CREATE TABLE seat
(
    id           SERIAL PRIMARY KEY,
    seat_count   INTEGER NOT NULL DEFAULT 0,
    plane_id     INTEGER NOT NULL,
    seat_type_id INTEGER NOT NULL,
    UNIQUE (plane_id, seat_type_id),
    FOREIGN KEY (plane_id) REFERENCES plane (id),
    FOREIGN KEY (seat_type_id) REFERENCES seat_type (id)
);

CREATE TABLE seat_pricing
(
    id         SERIAL PRIMARY KEY,
    unit_price NUMERIC(10, 2) NOT NULL,
    seat_id    INTEGER        NOT NULL,
    flight_id  INTEGER        NOT NULL,
    UNIQUE (seat_id, flight_id),
    FOREIGN KEY (seat_id) REFERENCES seat (id),
    FOREIGN KEY (flight_id) REFERENCES flight (id)
);

CREATE TABLE reservation
(
    id              SERIAL PRIMARY KEY,
    status          VARCHAR(9) NOT NULL,
    timestamp       TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    seat_count      INTEGER    NOT NULL,
    seat_pricing_id INTEGER    NOT NULL,
    user_id         INTEGER    NOT NULL,
    FOREIGN KEY (seat_pricing_id) REFERENCES seat_pricing (id),
    FOREIGN KEY (user_id) REFERENCES _user (id)
);

CREATE TABLE discount
(
    id              SERIAL PRIMARY KEY,
    percentage      NUMERIC(5, 2) NOT NULL,
    seat_count      INTEGER       NOT NULL,
    seat_pricing_id INTEGER       NOT NULL UNIQUE,
    FOREIGN KEY (seat_pricing_id) REFERENCES seat_pricing (id)
);

CREATE TABLE _settings
(
    id                     SERIAL PRIMARY KEY,
    min_reservation_hours  INTEGER   NOT NULL,
    min_cancellation_hours INTEGER   NOT NULL,
    modified_at            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by            INTEGER REFERENCES _user (id)
);
