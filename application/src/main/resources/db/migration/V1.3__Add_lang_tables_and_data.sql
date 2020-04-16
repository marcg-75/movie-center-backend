CREATE TABLE `movie_format_spoken_languages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `movie_format_id` bigint NOT NULL,
  `language` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `spokenlang_movieformat_fk` FOREIGN KEY (`movie_format_id`) REFERENCES `movie_format_info`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `movie_format_subtitle_languages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `movie_format_id` bigint NOT NULL,
  `language` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `subtitlelang_movieformat_fk` FOREIGN KEY (`movie_format_id`) REFERENCES `movie_format_info`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `movie_personal_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `movie_id` bigint NOT NULL,
  `archive_number` int(6) DEFAULT NULL,
  `grade` tinyint DEFAULT NULL,
  `obtain_date` date DEFAULT NULL,
  `obtain_price` DECIMAL(5,2) DEFAULT NULL,
  `currency` varchar(5) DEFAULT NULL,
  `obtain_place` varchar(50) DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movie_id_UNIQUE` (`movie_id`),
  UNIQUE KEY `archive_number_UNIQUE` (`archive_number`),
  CONSTRAINT `personal_movie_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

insert into role (code, name) values ('ACTOR','Skådespelare');
insert into role (code, name) values ('DIRECTOR','Regissör');
insert into role (code, name) values ('PRODUCER','Producent');
insert into role (code, name) values ('MUSIC','Musik');
insert into role (code, name) values ('WRITER','Manus');
insert into role (code, name) values ('CASTING','Rollbesättare');
insert into role (code, name) values ('EDITOR','Redigerare');
insert into role (code, name) values ('CINEMATOGRAPHY','Cinematografi');
insert into role (code, name) values ('SOUND','Ljud');
insert into role (code, name) values ('ART','Konst');
insert into role (code, name) values ('MISC','Övrigt');

insert into `format` (code, name) values ('DVD','DVD');
insert into `format` (code, name) values ('BLURAY','BluRay');
insert into `format` (code, name) values ('VHS','VHS');
insert into `format` (code, name) values ('HDDVD','HD DVD');
insert into `format` (code, name) values ('FKUHD','4K Ultra HD');
