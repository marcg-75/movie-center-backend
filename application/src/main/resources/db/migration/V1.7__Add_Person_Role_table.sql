DROP TABLE `movie_person_role`;

CREATE TABLE `person_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `person_id` bigint NOT NULL,
  `role_cd` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `person_id` (person_id),
  CONSTRAINT `personrole_person_fk` FOREIGN KEY (`person_id`) REFERENCES `person`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `personrole_role_fk` FOREIGN KEY (`role_cd`) REFERENCES `role`(`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `cast_and_crew` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `movie_id` bigint NOT NULL,
  `person_role_id` bigint NOT NULL,
  `character_name` varchar(150),
  PRIMARY KEY (`id`),
  KEY `movie_id` (movie_id),
  CONSTRAINT `castandcrew_movie_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `castandcrew_personrole_fk` FOREIGN KEY (`person_role_id`) REFERENCES `person_role`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
