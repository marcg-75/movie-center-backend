package se.giron.moviecenter.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class MovieGenreId implements Serializable {

    private Movie movie;

    private Genre genre;

    public MovieGenreId() {
        this.setMovie(null);
        this.setGenre(null);
    }

    public MovieGenreId(Movie movie, Genre genre) {
        this.setMovie(movie);
        this.setGenre(genre);
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieGenreId setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public MovieGenreId setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieGenreId that = (MovieGenreId) o;
        return Objects.equals(getMovie().getId(), that.getMovie().getId()) &&
                Objects.equals(getGenre().getCode(), that.getGenre().getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovie().getId(), getGenre().getCode());
    }
}
