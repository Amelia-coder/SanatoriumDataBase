CREATE TABLE office (
                        number SERIAL PRIMARY KEY,
                        floor INT NOT NULL,
                        building VARCHAR(1) NOT NULL,
                        CONSTRAINT unique_office UNIQUE (floor, building, number)
);

CREATE TABLE procedure (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL UNIQUE,
                           description TEXT
);

CREATE TABLE staff (
                       id SERIAL PRIMARY KEY,
                       firstname VARCHAR(255) NOT NULL,
                       lastname VARCHAR(255) NOT NULL,
                       middlename VARCHAR(255),
                       phone VARCHAR(20) UNIQUE,
                       email VARCHAR(255) UNIQUE,
                       position VARCHAR(255) NOT NULL
);

CREATE TABLE room (
                      number SERIAL PRIMARY KEY,
                      floor INT NOT NULL,
                      building VARCHAR(1) NOT NULL,
                      bed_count INT NOT NULL,
                      CONSTRAINT unique_room UNIQUE (floor, building, number)
);

CREATE TABLE client (
                        id SERIAL PRIMARY KEY,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        middle_name VARCHAR(255),
                        phone VARCHAR(20) UNIQUE,
                        email VARCHAR(255) UNIQUE,
                        roomnumber INT REFERENCES room(number),
                        resortcard VARCHAR(50)
);

CREATE TABLE ticket (
                        id SERIAL PRIMARY KEY,
                        clientid INT REFERENCES client(id) ON DELETE CASCADE,
                        roomnumber INT REFERENCES room(number),
                        doctor VARCHAR(255),
                        direction_id INT REFERENCES direction(id),
                        checkindate DATE NOT NULL,
                        checkoutdate DATE NOT NULL,
                        CONSTRAINT check_dates CHECK (checkoutdate > checkindate)
);

CREATE TABLE assignment (
                            id SERIAL PRIMARY KEY,
                            direction_id INT REFERENCES direction(id), -- Связь с направлением
                            procedure_id INT REFERENCES procedure(id), -- Процедура
                            start_time TIME NOT NULL,                  -- Время начала
                            quantity INT NOT NULL,                     -- Количество повторений
                            staff_id INT REFERENCES staff(id),         -- Врач или медперсонал
                            office_number INT REFERENCES office(number) -- Кабинет
);


CREATE TABLE direction (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL, -- Название направления
                           description TEXT            -- Описание направления
);

CREATE TABLE direction_procedure (
                                     direction_id INT REFERENCES direction(id),
                                     procedure_id INT REFERENCES procedure(id),
                                     PRIMARY KEY (direction_id, procedure_id)
);
