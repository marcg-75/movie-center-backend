# Remove main genre from main movie table.
alter table movie drop foreign key movie_maingenre_fk;

drop index movie_maingenre_fk_idx on movie;

alter table movie drop column main_genre_cd;

# Restructure movie genre table.
rename table movie_subgenre to movie_genre;
