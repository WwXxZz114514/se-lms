DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    tel CHAR(11) NOT NULL UNIQUE,
    password VARCHAR(16) NOT NULL
);

DROP TABLE IF EXISTS tb_admin;
CREATE TABLE tb_admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    tel CHAR(11) NOT NULL UNIQUE,
    password VARCHAR(16) NOT NULL
);

DROP TABLE IF EXISTS tb_seat;
CREATE TABLE tb_seat (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    seat_row INT NOT NULL,
    seat_col INT NOT NULL,
    area_id INT NOT NULL
);

DROP TABLE IF EXISTS tb_area;
CREATE TABLE tb_area (
    area_id INT PRIMARY KEY AUTO_INCREMENT,
    area_name VARCHAR(32) NOT NULL UNIQUE,
    area_rows INT NOT NULL DEFAULT 2,
    area_cols INT NOT NULL DEFAULT 2
);

DROP TABLE IF EXISTS tb_order;
CREATE TABLE tb_order (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    seat_id INT NOT NULL,
    order_time TIMESTAMP NOT NULL DEFAULT NOW(),
    appointment_time TIMESTAMP NOT NULL,
    order_status INT NOT NULL DEFAULT 0,
    UNIQUE (seat_id, appointment_time),
    UNIQUE (user_id, appointment_time)
);

INSERT INTO tb_admin(username, tel, password) VALUES ('admin', '12345678901', '123456');
INSERT INTO tb_user(username, tel, password) VALUES ('user', '12345678901', '123456');
