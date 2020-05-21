DROP TABLE `movie_format_spoken_languages`;
DROP TABLE `movie_format_subtitle_languages`;

CREATE TABLE `movie_format_spoken_languages` (
  `movie_format_id` bigint NOT NULL,
  `language_id` bigint NOT NULL,
  PRIMARY KEY (`movie_format_id`,`language_id`),
  CONSTRAINT `spokenlang_movieformat_fk` FOREIGN KEY (`movie_format_id`) REFERENCES `movie_format_info`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `spokenlang_lang_fk` FOREIGN KEY (`language_id`) REFERENCES `language`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `movie_format_subtitle_languages` (
  `movie_format_id` bigint NOT NULL,
  `language_id` bigint NOT NULL,
  PRIMARY KEY (`movie_format_id`,`language_id`),
  CONSTRAINT `subtitlelang_movieformat_fk` FOREIGN KEY (`movie_format_id`) REFERENCES `movie_format_info`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `subtitlelang_lang_fk` FOREIGN KEY (`language_id`) REFERENCES `language`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
