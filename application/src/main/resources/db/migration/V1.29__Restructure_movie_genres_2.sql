alter table movie_genre drop primary key;

alter table movie_genre add id bigint first;

alter table movie_genre add constraint movie_genre_pk primary key (id);

alter table movie_genre modify id bigint auto_increment;

alter table movie_genre auto_increment = 1;

alter table movie_genre add constraint movie_genre_pk unique (movie_id, genre_cd);

alter table movie_genre add main_genre TINYINT(1) default '0';
