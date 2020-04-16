CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `role` (
  `code` varchar(30) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`code`)
);

CREATE TABLE `movie_person_role` (
  `movie_id` bigint NOT NULL,
  `person_id` bigint NOT NULL,
  `role_cd` varchar(30) NOT NULL,
  PRIMARY KEY (`movie_id`,`person_id`,`role_cd`),
  KEY `movie_id` (movie_id),
  CONSTRAINT `moviepersonrole_movie_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `moviepersonrole_person_fk` FOREIGN KEY (`person_id`) REFERENCES `person`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `moviepersonrole_role_fk` FOREIGN KEY (`role_cd`) REFERENCES `role`(`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `studio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `movie_studio` (
  `movie_id` bigint NOT NULL,
  `studio_id` bigint NOT NULL,
  PRIMARY KEY (`movie_id`,`studio_id`),
  CONSTRAINT `moviestudio_movie_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `moviestudio_studio_fk` FOREIGN KEY (`studio_id`) REFERENCES `studio`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `format` (
  `code` varchar(15) NOT NULL,
  `name` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`code`)
);

ALTER TABLE movie DROP KEY `archive_number_UNIQUE`;
ALTER TABLE movie DROP COLUMN `archive_number`;
ALTER TABLE movie DROP COLUMN `name`;
ALTER TABLE movie ADD COLUMN `title` varchar(100) NOT NULL;
ALTER TABLE movie ADD COLUMN `description` varchar(2000) NOT NULL;
ALTER TABLE movie ADD COLUMN `runtime` time DEFAULT NULL;
ALTER TABLE movie ADD COLUMN `release_date` date DEFAULT NULL;
ALTER TABLE movie ADD COLUMN `country` varchar(30) DEFAULT NULL;
ALTER TABLE movie ADD COLUMN `age_restriction` varchar(30) DEFAULT NULL;

CREATE TABLE `movie_format_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `movie_id` bigint NOT NULL,
  `format_cd` varchar(15) DEFAULT NULL,
  `region_cd` tinyint DEFAULT NULL,
  `upc_id` varchar(20) DEFAULT NULL,
  `discs` tinyint DEFAULT NULL,
  `picture_format` varchar(20) DEFAULT NULL,
  `system` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movie_id_UNIQUE` (`movie_id`),
  CONSTRAINT `movieformat_movie_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `movieformat_format_fk` FOREIGN KEY (`format_cd`) REFERENCES `format` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
