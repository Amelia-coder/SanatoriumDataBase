-- Удаление таблиц, если они существуют
DROP TABLE IF EXISTS assignment CASCADE;
DROP TABLE IF EXISTS ticket CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS procedure CASCADE;
DROP TABLE IF EXISTS staff CASCADE;
DROP TABLE IF EXISTS office CASCADE;


-- SQL-скрипт для создания базы данных санатория

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
                        assignmentid INT,
                        checkindate DATE NOT NULL,
                        checkoutdate DATE NOT NULL,
                        CONSTRAINT check_dates CHECK (checkoutdate > checkindate)
);

CREATE TABLE assignment (
                            id SERIAL PRIMARY KEY,
                            ticketid INT REFERENCES ticket(id) ON DELETE CASCADE,
                            procedureid INT REFERENCES procedure(id),
                            starttime TIME NOT NULL,
                            quantity INT NOT NULL,
                            officenumber INT REFERENCES office(number),
                            staffid INT REFERENCES staff(id),
                            CONSTRAINT unique_schedule UNIQUE (starttime, officenumber, staffid)
);

-- Создание таблиц
CREATE TABLE office (
                        number SERIAL PRIMARY KEY,
                        floor INT NOT NULL,
                        building VARCHAR(1) NOT NULL
);

CREATE TABLE procedure (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
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
                      bed_count INT NOT NULL
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
                        clientid INT REFERENCES client(id),
                        roomnumber INT REFERENCES room(number),
                        doctor VARCHAR(255),
                        assignmentid INT,
                        checkindate DATE NOT NULL,
                        checkoutdate DATE NOT NULL
);

CREATE TABLE assignment (
                            id SERIAL PRIMARY KEY,
                            ticketid INT REFERENCES ticket(id),
                            procedureid INT REFERENCES procedure(id),
                            starttime TIME NOT NULL,
                            quantity INT NOT NULL,
                            officenumber INT REFERENCES office(number),
                            staffid INT REFERENCES staff(id)
);

-- Заполнение данных
INSERT INTO office (number, floor, building)
SELECT i,
       (i % 5 + 1),
       CASE
           WHEN i % 5 = 0 THEN 'A'
           WHEN i % 5 = 1 THEN 'B'
           WHEN i % 5 = 2 THEN 'C'
           WHEN i % 5 = 3 THEN 'D'
           ELSE 'E'
           END
FROM generate_series(1, 50) i;

-- Вставка данных в procedure
INSERT INTO procedure (id, name, description)
VALUES
    (1, 'Массаж', 'Ручной лечебный массаж'),
    (2, 'Грязевые ванны', 'Терапия с использованием лечебных грязей'),
    (3, 'Физиотерапия', 'Лечение физическими методами'),
    (4, 'Ингаляции', 'Лечение дыхательных путей паром или аэрозолями'),
    (5, 'Акупунктура', 'Иглоукалывание'),
    (6, 'Лазерная терапия', 'Лечение лазерным излучением'),
    (7, 'Плавание', 'Занятия в бассейне'),
    (8, 'Мануальная терапия', 'Терапия, воздействующая на суставы и позвоночник'),
    (9, 'Ароматерапия', 'Лечение с помощью эфирных масел'),
    (10, 'Обёртывания', 'Процедуры для похудения и восстановления кожи'),
    (11, 'Термальные ванны', 'Лечение с использованием горячей воды'),
    (12, 'УЗИ терапия', 'Терапия с использованием ультразвуковых волн'),
    (13, 'Магнитотерапия', 'Лечение магнитным полем'),
    (14, 'Диетотерапия', 'Питание по лечебным программам'),
    (15, 'Соляная комната', 'Лечение в соляной камере'),
    (16, 'Психотерапия', 'Консультации с психологом'),
    (17, 'ЛФК', 'Лечебная физкультура'),
    (18, 'Витаминные коктейли', 'Приём полезных напитков для здоровья'),
    (19, 'Электротерапия', 'Лечение электрическими импульсами'),
    (20, 'Спа-процедуры', 'Релаксация и восстановление в СПА');

-- Вставка данных в staff
WITH staff_names AS (
    SELECT id,
           CASE (id % 20)
               WHEN 0 THEN 'Иван'
               WHEN 1 THEN 'Александр'
               WHEN 2 THEN 'Пётр'
               WHEN 3 THEN 'Дмитрий'
               WHEN 4 THEN 'Сергей'
               WHEN 5 THEN 'Михаил'
               WHEN 6 THEN 'Андрей'
               WHEN 7 THEN 'Анатолий'
               WHEN 8 THEN 'Егор'
               WHEN 9 THEN 'Владимир'
               WHEN 10 THEN 'Анна'
               WHEN 11 THEN 'Мария'
               WHEN 12 THEN 'Елена'
               WHEN 13 THEN 'Светлана'
               WHEN 14 THEN 'Ольга'
               WHEN 15 THEN 'Наталья'
               WHEN 16 THEN 'Татьяна'
               WHEN 17 THEN 'Виктория'
               WHEN 18 THEN 'Ирина'
               ELSE 'Екатерина'
               END AS first_name,
           CASE (id % 20)
               WHEN 0 THEN 'Иванов'
               WHEN 1 THEN 'Петров'
               WHEN 2 THEN 'Сидоров'
               WHEN 3 THEN 'Смирнов'
               WHEN 4 THEN 'Кузнецов'
               WHEN 5 THEN 'Соколов'
               WHEN 6 THEN 'Морозов'
               WHEN 7 THEN 'Попов'
               WHEN 8 THEN 'Лебедев'
               WHEN 9 THEN 'Новиков'
               WHEN 10 THEN 'Васильева'
               WHEN 11 THEN 'Ковалёва'
               WHEN 12 THEN 'Попова'
               WHEN 13 THEN 'Семенова'
               WHEN 14 THEN 'Гордеева'
               WHEN 15 THEN 'Михайлова'
               WHEN 16 THEN 'Николаева'
               WHEN 17 THEN 'Фёдорова'
               WHEN 18 THEN 'Зайцева'
               ELSE 'Воронцова'
               END AS last_name,
           CASE (id % 20)
               WHEN 0 THEN 'Иванович'
               WHEN 1 THEN 'Александрович'
               WHEN 2 THEN 'Петрович'
               WHEN 3 THEN 'Дмитриевич'
               WHEN 4 THEN 'Сергеевич'
               WHEN 5 THEN 'Михайлович'
               WHEN 6 THEN 'Андреевич'
               WHEN 7 THEN 'Анатольевич'
               WHEN 8 THEN 'Егорович'
               WHEN 9 THEN 'Владимирович'
               WHEN 10 THEN 'Ивановна'
               WHEN 11 THEN 'Александровна'
               WHEN 12 THEN 'Петровна'
               WHEN 13 THEN 'Дмитриевна'
               WHEN 14 THEN 'Сергеевна'
               WHEN 15 THEN 'Михайловна'
               WHEN 16 THEN 'Андреевна'
               WHEN 17 THEN 'Анатольевна'
               WHEN 18 THEN 'Егоровна'
               ELSE 'Владимировна'
               END AS middle_name
    FROM generate_series(1, 50) id
)
INSERT INTO staff (id, firstName, lastName, middleName, phone, email, position)
SELECT id,
       first_name,
       last_name,
       middle_name,
       '+7910' || lpad(id::text, 6, '0'),
       'staff' || id || '@sanatorium.ru',
       CASE
           WHEN id % 6 = 0 THEN 'Врач'
           WHEN id % 6 = 1 THEN 'Медсестра'
           WHEN id % 6 = 2 THEN 'Администратор'
           WHEN id % 6 = 3 THEN 'Уборщик'
           WHEN id % 6 = 4 THEN 'Работник столовой'
           ELSE 'Повар'
           END
FROM staff_names;

INSERT INTO room (number, floor, building, bed_count)
SELECT r,
       (r % 5 + 1),
       CASE
           WHEN r % 5 = 0 THEN 'A'
           WHEN r % 5 = 1 THEN 'B'
           WHEN r % 5 = 2 THEN 'C'
           WHEN r % 5 = 3 THEN 'D'
           ELSE 'E'
           END,
       (r % 3 + 1)
FROM generate_series(1, 100) r;

-- Вставка данных в client
WITH client_names AS (
    SELECT id,
           CASE (id % 20)
               WHEN 0 THEN 'Анна'
               WHEN 1 THEN 'Мария'
               WHEN 2 THEN 'Елена'
               WHEN 3 THEN 'Светлана'
               WHEN 4 THEN 'Ольга'
               WHEN 5 THEN 'Наталья'
               WHEN 6 THEN 'Татьяна'
               WHEN 7 THEN 'Виктория'
               WHEN 8 THEN 'Ирина'
               WHEN 9 THEN 'Екатерина'
               WHEN 10 THEN 'Иван'
               WHEN 11 THEN 'Александр'
               WHEN 12 THEN 'Пётр'
               WHEN 13 THEN 'Дмитрий'
               WHEN 14 THEN 'Сергей'
               WHEN 15 THEN 'Михаил'
               WHEN 16 THEN 'Андрей'
               WHEN 17 THEN 'Анатолий'
               WHEN 18 THEN 'Егор'
               ELSE 'Владимир'
               END AS first_name,
           CASE
               WHEN id % 20 < 10 THEN
                   CASE (id % 10)
                       WHEN 0 THEN 'Васильева'
                       WHEN 1 THEN 'Ковалёва'
                       WHEN 2 THEN 'Попова'
                       WHEN 3 THEN 'Семенова'
                       WHEN 4 THEN 'Гордеева'
                       WHEN 5 THEN 'Михайлова'
                       WHEN 6 THEN 'Николаева'
                       WHEN 7 THEN 'Фёдорова'
                       WHEN 8 THEN 'Зайцева'
                       ELSE 'Воронцова'
                       END
               ELSE
                   CASE (id % 10)
                       WHEN 0 THEN 'Иванов'
                       WHEN 1 THEN 'Петров'
                       WHEN 2 THEN 'Сидоров'
                       WHEN 3 THEN 'Смирнов'
                       WHEN 4 THEN 'Кузнецов'
                       WHEN 5 THEN 'Соколов'
                       WHEN 6 THEN 'Морозов'
                       WHEN 7 THEN 'Попов'
                       WHEN 8 THEN 'Лебедев'
                       ELSE 'Новиков'
                       END
               END AS last_name,
           CASE
               WHEN id % 20 < 10 THEN
                   CASE (id % 10)
                       WHEN 0 THEN 'Ивановна'
                       WHEN 1 THEN 'Александровна'
                       WHEN 2 THEN 'Петровна'
                       WHEN 3 THEN 'Дмитриевна'
                       WHEN 4 THEN 'Сергеевна'
                       WHEN 5 THEN 'Михайловна'
                       WHEN 6 THEN 'Андреевна'
                       WHEN 7 THEN 'Анатольевна'
                       WHEN 8 THEN 'Егоровна'
                       ELSE 'Владимировна'
                       END
               ELSE
                   CASE (id % 10)
                       WHEN 0 THEN 'Иванович'
                       WHEN 1 THEN 'Александрович'
                       WHEN 2 THEN 'Петрович'
                       WHEN 3 THEN 'Дмитриевич'
                       WHEN 4 THEN 'Сергеевич'
                       WHEN 5 THEN 'Михайлович'
                       WHEN 6 THEN 'Андреевич'
                       WHEN 7 THEN 'Анатольевич'
                       WHEN 8 THEN 'Егорович'
                       ELSE 'Владимирович'
                       END
               END AS middle_name
    FROM generate_series(1, 1000) id
)
INSERT INTO client (id, first_name, last_name, middle_name, phone, email, roomNumber, resortCard)
SELECT id,
       first_name,
       last_name,
       middle_name,
       '+7920' || lpad(id::text, 6, '0'),
       'client' || id || '@example.com',
       (id % 100 + 1),
       'RC-' || id
FROM client_names;


-- Заполнение таблицы ticket
INSERT INTO ticket (id, clientId, roomNumber, doctor, checkInDate, checkOutDate)
SELECT t,
       c.id,
       c.roomNumber,
       CONCAT('Доктор ', s.lastName),
       CURRENT_DATE - (t % 365),
       CURRENT_DATE + (t % 15 + 1)
FROM generate_series(1, 1000) t
         JOIN client c ON c.id = t
         JOIN staff s ON s.position = 'Врач' AND s.id = (t % 50 + 1);

-- Заполнение таблицы assignment
INSERT INTO assignment (id, ticketId, procedureId, startTime, quantity, officeNumber, staffId)
SELECT a,
       t.id, -- Используем id из ticket
       (a % 10 + 1),
       TIME '08:00:00' + (a % 10 || ' hours')::interval,
        (a % 5 + 1),
       (a % 50 + 1),
       s.id
FROM generate_series(1, 1000) a
         JOIN ticket t ON t.id = a -- Связь с ticket
         JOIN staff s ON s.position IN ('Врач', 'Медсестра') AND s.id = (a % 50 + 1);


-- Обновление таблицы ticket с добавлением assignmentId
WITH assignment_data AS (
    SELECT a.id AS assignment_id, a.ticketId AS ticket_id
    FROM assignment a
)
UPDATE ticket t
SET assignmentId = ad.assignment_id
    FROM assignment_data ad
WHERE t.id = ad.ticket_id;
