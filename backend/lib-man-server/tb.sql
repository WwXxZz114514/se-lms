CREATE TABLE tb_user (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL,
    tel CHAR(11) NOT NULL,
    password VARCHAR(16) NOT NULL,
    create_date DATE NOT NULL DEFAULT NOW()
);
CREATE TABLE tb_admin (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL,
    tel CHAR(11) NOT NULL,
    password VARCHAR(16) NOT NULL,
    create_date DATE NOT NULL DEFAULT NOW()
);
INSERT INTO tb_admin(username, tel, password) VALUES ('admin', '12345678901', '123456');
INSERT INTO tb_user(username, tel, password) VALUES ('user', '12345678901', '123456');
