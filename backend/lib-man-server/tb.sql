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
    area_name VARCHAR(32) NOT NULL
);
CREATE TABLE tb_order (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    seat_id INT NOT NULL,
    order_time TIMESTAMP NOT NULL DEFAULT NOW(),
    appointment_time TIMESTAMP NOT NULL
);
INSERT INTO tb_admin(username, tel, password) VALUES ('admin', '12345678901', '123456');
INSERT INTO tb_user(username, tel, password) VALUES ('user', '12345678901', '123456');
INSERT INTO tb_area(area_name) VALUES ('Lib1'), ('Lib2');
INSERT INTO tb_seat(seat_row, seat_col, area_id) VALUES (1, 1, 1), (1, 2, 1), (2, 1, 1), (2, 2, 1);
INSERT INTO tb_order(user_id, seat_id, appointment_time) VALUES (1, 1, TIMESTAMP '2024-01-01 09:00');
