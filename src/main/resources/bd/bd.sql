INSERT INTO office (id, floor, building)
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
INSERT INTO client (id, first_name, last_name, middle_name, phone, email, room_id, resort_card)
SELECT id,
       first_name,
       last_name,
       middle_name,
       '+7920' || lpad(id::text, 6, '0'),
       'client' || id || '@example.com',
       (id % 100 + 1),
       'RC-' || id
FROM client_names;



INSERT INTO room (id, floor, building, bed_count)
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

INSERT INTO ticket (client_id, check_in_date, check_out_date)
VALUES (3, '2025-03-01', '2025-03-10');

-- Дополнительная информация о клиенте:
INSERT INTO client (id, first_name, last_name, phone, email, room_id, resort_card)
VALUES (3, 'Иван', 'Иванов', '+7 (123) 456-7890', 'ivanov@example.com', 101, 'RC12345');
