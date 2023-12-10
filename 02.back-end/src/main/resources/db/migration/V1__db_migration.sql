CREATE TABLE IF NOT EXISTS `role`
(
    `role_id` BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`    VARCHAR(20) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `gender`
(
    `gender_id` BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `gender`    VARCHAR(50) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `status`
(
    `status_id` BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `status`    VARCHAR(50) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`         BIGINT       NOT NUll AUTO_INCREMENT PRIMARY KEY,
    `email`           VARCHAR(100) NOT NULL UNIQUE,
    `password`        LONGTEXT     NOT NULL,
    `avatar`          LONGTEXT,
    `first_name`      VARCHAR(20),
    `last_name`       VARCHAR(20),
    `birthdate`       VARCHAR(10),
    `phone_number`    VARCHAR(20),
    `current_address` LONGTEXT,
    `delete_status`   BOOLEAN,
    `gender_id`       BIGINT,
    `status_id`       BIGINT,
    `role_id`         BIGINT       NOT NULL,
    FOREIGN KEY (`role_id`) REFERENCES role (`role_id`),
    FOREIGN KEY (`status_id`) REFERENCES status (`status_id`),
    FOREIGN KEY (`gender_id`) REFERENCES gender (`gender_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `authentication`
(
    `code_id` BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT   NOT NULL UNIQUE,
    `code`    LONGTEXT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES user (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `reset_password`
(
    `reset_id` BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`  BIGINT   NOT NULL UNIQUE,
    `token`    LONGTEXT NOT NULL UNIQUE,
    FOREIGN KEY (`user_id`) REFERENCES user (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

INSERT INTO `role` (`name`)
values ('ROLE_ADMIN'),
       ('ROLE_MODERATOR'),
       ('ROLE_USER');
INSERT INTO `gender` (`gender`)
values ('MALE'),
       ('FEMALE'),
       ('RATHER NOT TO SAY');
INSERT INTO `status` (`status`)
values ('MARRIED'),
       ('SINGLE'),
       ('RATHER NOT TO SAY');


