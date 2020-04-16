ALTER TABLE cover DROP FOREIGN KEY `cover_movie_fk`;
ALTER TABLE cover DROP KEY `movie_id_UNIQUE`;
ALTER TABLE cover DROP COLUMN `movie_id`;

ALTER TABLE cover ADD COLUMN `movie_format_id` bigint NOT NULL;
ALTER TABLE cover ADD UNIQUE KEY `movie_format_id_UNIQUE` (`movie_format_id`);
ALTER TABLE cover ADD CONSTRAINT `cover_movieformat_fk` FOREIGN KEY (`movie_format_id`) REFERENCES `movie_format_info`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
