CREATE DATABASE `letta`;
GRANT ALL ON `letta`.* TO 'lettauser'@'localhost' IDENTIFIED BY 'lettapass';

USE `letta`;

CREATE TABLE `user`(
	`login` VARCHAR(20) NOT NULL,
	`password` VARCHAR(32) NOT NULL,
	PRIMARY KEY (`login`)
);

CREATE TABLE `event` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(100) NOT NULL,
	`description` VARCHAR(1000) NOT NULL,
	`place` VARCHAR(500) NOT NULL,
	`num_assistants` INT NOT NULL,
	`start` DATETIME NOT NULL,
	`end` DATETIME NOT NULL,
	`user_id` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user`(`login`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `assists` (
	`user_id` VARCHAR(20) NOT NULL,
	`event_id` INT NOT NULL,
	PRIMARY KEY (`user_id`, `event_id`),
	FOREIGN KEY (`user_id`) REFERENCES `user`(`login`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`event_id`) REFERENCES `event`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
