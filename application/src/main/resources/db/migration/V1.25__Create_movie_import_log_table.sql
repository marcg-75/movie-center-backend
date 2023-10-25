DROP TABLE IF EXISTS `movie_import_log`;
CREATE TABLE `movie_import_log` (
    `id`                     bigint NOT NULL AUTO_INCREMENT,
    `movie_title`            varchar(100)  DEFAULT NULL,
    `import_start_timestamp` datetime      DEFAULT NULL,
    `import_end_timestamp`   datetime      DEFAULT NULL,
    `import_duration`        varchar(255)  DEFAULT NULL,
    `status`                 varchar(64)   DEFAULT NULL,
    `status_description`     varchar(1024) DEFAULT NULL,
    `filename`               varchar(255)  DEFAULT NULL,
    `count_movies`           integer       DEFAULT 0,
    `count_new_movies`       integer       DEFAULT 0,
    `count_updated_movies`   integer       DEFAULT 0,
    `count_failed_movies`    integer       DEFAULT 0,
    PRIMARY KEY (`id`)
);
