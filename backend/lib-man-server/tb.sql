CREATE TABLE tb_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    tel CHAR(11) NOT NULL UNIQUE,
    password VARCHAR(16) NOT NULL
);
CREATE TABLE tb_admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    tel CHAR(11) NOT NULL UNIQUE,
    password VARCHAR(16) NOT NULL
);
CREATE TABLE tb_seat (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    seat_row INT NOT NULL,
    seat_col INT NOT NULL,
    area_id INT NOT NULL
);
CREATE TABLE tb_area (
    area_id INT PRIMARY KEY AUTO_INCREMENT,
    area_name VARCHAR(32) NOT NULL,
    area_rows INT NOT NULL DEFAULT 2,
    area_cols INT NOT NULL DEFAULT 2
);
CREATE TABLE tb_order (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    seat_id INT NOT NULL,
    order_time TIMESTAMP NOT NULL DEFAULT NOW(),
    appointment_time TIMESTAMP NOT NULL,
    order_status INT NOT NULL DEFAULT 0,
    UNIQUE (seat_id, appointment_time)
);
INSERT INTO tb_admin(username, tel, password) VALUES ('admin', '12345678901', '123456');
INSERT INTO tb_user(username, tel, password) VALUES ('user', '12345678901', '123456');
INSERT INTO tb_area(area_name, area_cols, area_rows) VALUES ('Lib1', 5, 5), ('Lib2', 2, 2);
INSERT INTO tb_seat(seat_row, seat_col, area_id) VALUES (1, 1, 1), (1, 2, 1), (2, 1, 1), (2, 2, 1);
INSERT INTO tb_order(user_id, seat_id, appointment_time) VALUES (1, 1, TIMESTAMP '2023-01-01 09:00');
UPDATE tb_order SET order_status = 1 WHERE order_id = 1;
INSERT INTO tb_order(user_id, seat_id, appointment_time) VALUES (1, 1, TIMESTAMP '2024-01-01 09:00');
