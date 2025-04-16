INSERT INTO plane (model, manufacture_date)
VALUES ('Boeing 737', '2018-05-15'),
       ('Airbus A320', '2019-08-22'),
       ('Boeing 787 Dreamliner', '2020-11-10'),
       ('Airbus A380', '2017-03-25');

INSERT INTO city (name)
VALUES ('Paris'),
       ('New York'),
       ('Tokyo'),
       ('Londres'),
       ('Dubaï');

INSERT INTO seat_type (designation)
VALUES ('Économique'),
       ('Affaires'),
       ('Première Classe');

INSERT INTO seat (seat_count, plane_id, seat_type_id)
VALUES
    -- Seats for Boeing 737
    (120, 1, 1), -- 120 Economy seats
    (20, 1, 2),  -- 20 Business seats
    (10, 1, 3),  -- 10 First Class seats

    -- Seats for Airbus A320
    (150, 2, 1), -- 150 Economy seats
    (25, 2, 2),  -- 25 Business seats
    (8, 2, 3),   -- 8 First Class seats

    -- Seats for Boeing 787 Dreamliner
    (200, 3, 1), -- 200 Economy seats
    (35, 3, 2),  -- 35 Business seats
    (12, 3, 3),  -- 12 First Class seats

    -- Seats for Airbus A380
    (350, 4, 1), -- 350 Economy seats
    (60, 4, 2),  -- 60 Business seats
    (15, 4, 3);  -- 15 First Class seats
