package se.giron.moviecenter.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.giron.moviecenter.model.entity.Genre;

@ApiModel(value = "MovieInfoResource")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieInfoResource {

    @ApiModelProperty(notes = "Movie entity ID", dataType = "java.lang.Long")
    private Long id;

    private String title;

    private Genre mainGenre;

    private MoviePersonalInfoResource moviePersonalInfo;

    public Long getId() {
        return id;
    }

    public MovieInfoResource setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieInfoResource setTitle(String title) {
        this.title = title;
        return this;
    }

    public Genre getMainGenre() {
        return mainGenre;
    }

    public MovieInfoResource setMainGenre(Genre mainGenre) {
        this.mainGenre = mainGenre;
        return this;
    }

    public MoviePersonalInfoResource getMoviePersonalInfo() {
        return moviePersonalInfo;
    }

    public MovieInfoResource setMoviePersonalInfo(MoviePersonalInfoResource moviePersonalInfo) {
        this.moviePersonalInfo = moviePersonalInfo;
        return this;
    }
}
