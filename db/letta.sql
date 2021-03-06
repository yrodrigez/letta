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
	`start` TIMESTAMP NOT NULL,
	`end` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`user_id` VARCHAR(20) NOT NULL,
	`img` MEDIUMBLOB NULL,
	`img_ext` VARCHAR(5) NULL,
	`category` ENUM('others', 'literature', 'films', 'sports', 'tvshows', 'videogames', 'programming') DEFAULT 'others' NOT NULL,
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
