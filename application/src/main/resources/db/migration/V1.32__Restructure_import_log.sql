alter table movie_import_log change count_movies count_total int default 0 null;

alter table movie_import_log change count_new_movies count_successful int default 0 null;

alter table movie_import_log change count_updated_movies count_ignored int default 0 null;

alter table movie_import_log change count_failed_movies count_failed int default 0 null;
