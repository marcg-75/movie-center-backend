package se.giron.moviecenter.model.entity;

import javax.persistence.*;

@Entity
@IdClass(MovieGenreId.class)
public class MovieGenre {

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Id
    @ManyToOne
    @JoinColumn(name = "genre_cd", nullable = false)
    private Genre genre;

    private boolean mainGenre = false;

    public Movie getMovie() {
        return movie;
    }

    public MovieGenre setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public MovieGenre setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public boolean isMainGenre() {
        return mainGenre;
    }

    public MovieGenre setMainGenre(boolean mainGenre) {
        this.mainGenre = mainGenre;
        return this;
    }
}
