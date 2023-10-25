DROP TABLE IF EXISTS `movie_personal_info`;
DROP TABLE IF EXISTS `movie_format_subtitle_languages`;
DROP TABLE IF EXISTS `movie_format_spoken_languages`;
DROP TABLE IF EXISTS `language`;
DROP TABLE IF EXISTS `movie_studio`;
DROP TABLE IF EXISTS `studio`;
DROP TABLE IF EXISTS `cover`;
DROP TABLE IF EXISTS `cast_and_crew`;
DROP TABLE IF EXISTS `movie_person_role`;
DROP TABLE IF EXISTS `person_role`;
DROP TABLE IF EXISTS `person`;
DROP TABLE IF EXISTS `movie_format_info`;
DROP TABLE IF EXISTS `format`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `movie_subgenre`;
DROP TABLE IF EXISTS `movie`;
DROP TABLE IF EXISTS `genre`;

CREATE TABLE `genre` (
  `code` varchar(15) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`code`)
);

CREATE TABLE `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `archive_number` int(6) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `main_genre_cd` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `archive_number_UNIQUE` (`archive_number`),
  KEY `movie_maingenre_fk_idx` (`main_genre_cd`),
  CONSTRAINT `movie_maingenre_fk` FOREIGN KEY (`main_genre_cd`) REFERENCES `genre` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `movie_subgenre` (
  `movie_id` bigint NOT NULL,
  `genre_cd` varchar(15) NOT NULL,
  PRIMARY KEY (`movie_id`,`genre_cd`),
  KEY `movie_id` (movie_id),
  CONSTRAINT `moviegenre_genre_fk` FOREIGN KEY (`genre_cd`) REFERENCES `genre`(`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `moviegenre_movie_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `cover` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `movie_id` bigint NOT NULL,
  `foreground` blob DEFAULT NULL,
  `background` blob DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movie_id_UNIQUE` (`movie_id`),
  CONSTRAINT `cover_movie_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

insert into genre (code, name) values ('ACTION','Action');
insert into genre (code, name) values ('CARTOON','Tecknat');
insert into genre (code, name) values ('COMEDY','Komedi');
insert into genre (code, name) values ('DRAMA','Drama');
insert into genre (code, name) values ('FAMILY','Familj');
insert into genre (code, name) values ('ROMANCE','Romans');
insert into genre (code, name) values ('THRILLER','Thriller');
insert into genre (code, name) values ('WAR','Krig');
insert into genre (code, name) values ('WESTERN','Western');
