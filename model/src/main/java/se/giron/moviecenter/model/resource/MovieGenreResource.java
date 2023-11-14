package se.giron.moviecenter.model.resource;

import io.swagger.annotations.ApiModelProperty;
import se.giron.moviecenter.model.entity.Genre;

public class MovieGenreResource {

    private String movieTitle;

    private Genre genre;

    private boolean mainGenre = false;

    public String getMovieTitle() {
        return movieTitle;
    }

    public MovieGenreResource setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public MovieGenreResource setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public boolean isMainGenre() {
        return mainGenre;
    }

    public MovieGenreResource setMainGenre(boolean mainGenre) {
        this.mainGenre = mainGenre;
        return this;
    }
}
