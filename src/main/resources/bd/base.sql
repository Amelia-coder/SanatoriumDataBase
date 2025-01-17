-- Создание таблицы офисов
CREATE TABLE office (
                        id SERIAL PRIMARY KEY,
                        floor INT NOT NULL,
                        building VARCHAR(1) NOT NULL,
                        CONSTRAINT unique_office UNIQUE (floor, building, id)
);

-- Создание таблицы процедур
CREATE TABLE procedure (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL UNIQUE,
                           description TEXT
);

-- Создание таблицы сотрудников
CREATE TABLE staff (
                       id SERIAL PRIMARY KEY,
                       firstname VARCHAR(255) NOT NULL,
                       lastname VARCHAR(255) NOT NULL,
                       middlename VARCHAR(255),
                       phone VARCHAR(20) UNIQUE,
                       email VARCHAR(255) UNIQUE,
                       position VARCHAR(255) NOT NULL
);

-- Создание таблицы комнат
CREATE TABLE room (
                      id SERIAL PRIMARY KEY,
                      floor INT NOT NULL,
                      building VARCHAR(1) NOT NULL,
                      bed_count INT NOT NULL,
                      CONSTRAINT unique_room UNIQUE (floor, building, id)
);

-- Создание таблицы клиентов
CREATE TABLE client (
                        id SERIAL PRIMARY KEY,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        middle_name VARCHAR(255),
                        phone VARCHAR(20) UNIQUE,
                        email VARCHAR(255) UNIQUE,
                        room_id INT REFERENCES room(id) ON DELETE SET NULL,
                        resort_card VARCHAR(50)
);

-- Создание таблицы билетов
CREATE TABLE ticket (
                        id SERIAL PRIMARY KEY,
                        client_id INT REFERENCES client(id) ON DELETE CASCADE,
                        check_in_date DATE NOT NULL,
                        check_out_date DATE NOT NULL,
                        CONSTRAINT check_dates CHECK (check_out_date > check_in_date)
);

-- Создание таблицы назначений
CREATE TABLE assignment (
                            id SERIAL PRIMARY KEY,
                            ticket_id INT REFERENCES ticket(id) ON DELETE CASCADE, -- Связь с билетом (пациентом)
                            procedure_id INT REFERENCES procedure(id) ON DELETE CASCADE, -- Процедура
                            staff_id INT REFERENCES staff(id) ON DELETE SET NULL, -- Врач, выполняющий процедуру
                            office_id INT REFERENCES office(id) ON DELETE SET NULL, -- Кабинет
                            start_time TIMESTAMP NOT NULL, -- Время начала
                            duration INTERVAL NOT NULL    -- Длительность процедуры
);
