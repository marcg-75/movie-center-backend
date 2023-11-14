alter table movie_genre modify id bigint not null;

alter table movie_genre drop primary key;

alter table movie_genre drop column id;

alter table movie_genre add primary key (movie_id, genre_cd);

alter table movie_genre drop key movie_genre_pk;
