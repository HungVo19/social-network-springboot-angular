CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`         BIGINT                                                               NOT NUll AUTO_INCREMENT PRIMARY KEY,
    `email`           VARCHAR(100)                                                         NOT NULL UNIQUE,
    `password`        LONGTEXT                                                             NOT NULL,
    `avatar`          LONGTEXT,
    `first_name`      VARCHAR(20),
    `last_name`       VARCHAR(20),
    `birthdate`       DATE,
    `phone_number`    VARCHAR(20),
    `current_address` LONGTEXT,
    `occupation`      LONGTEXT,
    `delete_status`   BOOLEAN                                          DEFAULT (false),
    `gender`          ENUM ('MALE','FEMALE','RATHER_NOT_TO_SAY')       DEFAULT 'RATHER_NOT_TO_SAY',
    `status`          ENUM ('MARRIED','SINGLE','RATHER_NOT_TO_SAY')    DEFAULT 'RATHER_NOT_TO_SAY',
    `role`            ENUM ('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER') DEFAULT 'ROLE_USER' NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `authentication`
(
    `code_id`      BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`      BIGINT   NOT NULL UNIQUE,
    `code`         LONGTEXT NOT NULL,
    `created_time` DATETIME NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES user (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `reset_password`
(
    `reset_id`     BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`      BIGINT       NOT NULL UNIQUE,
    `token`        VARCHAR(255) NOT NULL UNIQUE,
    `created_time` DATETIME     NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES user (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `post`
(
    `post_id`       BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`       BIGINT   NOT NULL,
    `content`       LONGTEXT NOT NULL,
    `delete_status` BOOLEAN  NOT NULL                   DEFAULT (false),
    `privacy`       ENUM ('PRIVATE','FRIENDS','PUBLIC') DEFAULT 'PUBLIC' NOT NULL,
    `created_time`  DATETIME NOT NULL,
    `updated_time`  DATETIME NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES user (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;

CREATE TABLE IF NOT EXISTS `post_image`
(
    `image_id` BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `url`      LONGTEXT NOT NULL,
    `post_id`  BIGINT   NOT NULL,
    FOREIGN KEY (`post_id`) REFERENCES post (`post_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8mb4;
