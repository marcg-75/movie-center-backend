CREATE TABLE `language` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `movie_format_spoken_languages` DROP COLUMN `language`;
ALTER TABLE `movie_format_spoken_languages` ADD COLUMN `language_id` bigint NOT NULL;
ALTER TABLE `movie_format_spoken_languages` ADD CONSTRAINT `spokenlanguages_lang_fk` FOREIGN KEY (`language_id`) REFERENCES `language`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `movie_format_subtitle_languages` DROP COLUMN `language`;
ALTER TABLE `movie_format_subtitle_languages` ADD COLUMN `language_id` bigint NOT NULL;
ALTER TABLE `movie_format_subtitle_languages` ADD CONSTRAINT `subtitlelanguages_lang_fk` FOREIGN KEY (`language_id`) REFERENCES `language`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
