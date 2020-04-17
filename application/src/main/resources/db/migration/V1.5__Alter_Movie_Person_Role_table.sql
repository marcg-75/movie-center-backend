DROP TABLE `movie_person_role`;

CREATE TABLE `movie_person_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `movie_id` bigint NOT NULL,
  `person_id` bigint NOT NULL,
  `role_cd` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `movie_id` (movie_id),
  CONSTRAINT `moviepersonrole_movie_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `moviepersonrole_person_fk` FOREIGN KEY (`person_id`) REFERENCES `person`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `moviepersonrole_role_fk` FOREIGN KEY (`role_cd`) REFERENCES `role`(`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
