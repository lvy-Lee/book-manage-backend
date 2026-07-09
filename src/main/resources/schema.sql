CREATE DATABASE IF NOT EXISTS `bookmanage` DEFAULT CHARSET utf8mb4;

USE `bookmanage`;

CREATE TABLE IF NOT EXISTS `user` (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    `role`      VARCHAR(20)  NOT NULL DEFAULT 'user',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `book` (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255)  NOT NULL,
    author      VARCHAR(100),
    publisher   VARCHAR(100),
    isbn        VARCHAR(50),
    category    VARCHAR(50),
    description TEXT,
    `status`    VARCHAR(20)  NOT NULL DEFAULT 'available',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `borrow_record` (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    book_id     BIGINT NOT NULL,
    borrow_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    return_time DATETIME,
    `status`    VARCHAR(20) NOT NULL DEFAULT 'borrowed',
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (book_id) REFERENCES `book`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
