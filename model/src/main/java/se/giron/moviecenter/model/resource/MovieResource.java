package se.giron.moviecenter.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.entity.Studio;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.*;

@ApiModel(value = "MovieResource")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResource {

    @ApiModelProperty(notes = "Movie entity ID", dataType = "java.lang.Long")
    private Long id;

    @NotBlank(message = "{movie.title.notnull}")
    private String title;

    private String originalTitle;

    @NotBlank(message = "{movie.description.notnull}")
    private String description;

    @NotNull(message = "{movie.mainGenre.notnull}")
    @Valid
    private Genre mainGenre;

    private List<Genre> additionalGenres = new ArrayList<>();

    private Time runtime;

    private Date releaseDate;

    private String country;

    private String ageRestriction;

    private Set<Studio> studios = new HashSet<>();

    private List<CastAndCrewResource> actors = new ArrayList<>();

    private List<CastAndCrewResource> directors = new ArrayList<>();

    private List<CastAndCrewResource> producers = new ArrayList<>();

    private List<CastAndCrewResource> music = new ArrayList<>();

    private List<CastAndCrewResource> writers = new ArrayList<>();

    private List<CastAndCrewResource> casters = new ArrayList<>();

    private List<CastAndCrewResource> editors = new ArrayList<>();

    private List<CastAndCrewResource> cinematography = new ArrayList<>();

    private List<CastAndCrewResource> sound = new ArrayList<>();

    private List<CastAndCrewResource> art = new ArrayList<>();

    private List<CastAndCrewResource> otherRoles = new ArrayList<>();

    private MovieFormatInfoResource movieFormatInfo;

    private MoviePersonalInfoResource moviePersonalInfo;

    public Long getId() {
        return id;
    }

    public MovieResource setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieResource setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public MovieResource setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MovieResource setDescription(String description) {
        this.description = description;
        return this;
    }

    public Genre getMainGenre() {
        return mainGenre;
    }

    public MovieResource setMainGenre(Genre mainGenre) {
        this.mainGenre = mainGenre;
        return this;
    }

    public List<Genre> getAdditionalGenres() {
        return additionalGenres;
    }

    public MovieResource setAdditionalGenres(List<Genre> additionalGenres) {
        this.additionalGenres = additionalGenres;
        return this;
    }

    public Time getRuntime() {
        return runtime;
    }

    public MovieResource setRuntime(Time runtime) {
        this.runtime = runtime;
        return this;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public MovieResource setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public MovieResource setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public MovieResource setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
        return this;
    }

    public Set<Studio> getStudios() {
        return studios;
    }

    public MovieResource setStudios(Set<Studio> studios) {
        this.studios = studios;
        return this;
    }

    public List<CastAndCrewResource> getActors() {
        if (actors == null) {
            actors = new ArrayList<>();
        }
        return actors;
    }

    public MovieResource setActors(List<CastAndCrewResource> actors) {
        this.actors = actors;
        return this;
    }

    public List<CastAndCrewResource> getDirectors() {
        if (directors == null) {
            directors = new ArrayList<>();
        }
        return directors;
    }

    public MovieResource setDirectors(List<CastAndCrewResource> directors) {
        this.directors = directors;
        return this;
    }

    public List<CastAndCrewResource> getProducers() {
        if (producers == null) {
            producers = new ArrayList<>();
        }
        return producers;
    }

    public MovieResource setProducers(List<CastAndCrewResource> producers) {
        this.producers = producers;
        return this;
    }

    public List<CastAndCrewResource> getMusic() {
        if (music == null) {
            music = new ArrayList<>();
        }
        return music;
    }

    public MovieResource setMusic(List<CastAndCrewResource> music) {
        this.music = music;
        return this;
    }

    public List<CastAndCrewResource> getWriters() {
        if (writers == null) {
            writers = new ArrayList<>();
        }
        return writers;
    }

    public MovieResource setWriters(List<CastAndCrewResource> writers) {
        this.writers = writers;
        return this;
    }

    public List<CastAndCrewResource> getCasters() {
        if (casters == null) {
            casters = new ArrayList<>();
        }
        return casters;
    }

    public MovieResource setCasters(List<CastAndCrewResource> casters) {
        this.casters = casters;
        return this;
    }

    public List<CastAndCrewResource> getEditors() {
        if (editors == null) {
            editors = new ArrayList<>();
        }
        return editors;
    }

    public MovieResource setEditors(List<CastAndCrewResource> editors) {
        this.editors = editors;
        return this;
    }

    public List<CastAndCrewResource> getCinematography() {
        if (cinematography == null) {
            cinematography = new ArrayList<>();
        }
        return cinematography;
    }

    public MovieResource setCinematography(List<CastAndCrewResource> cinematography) {
        this.cinematography = cinematography;
        return this;
    }

    public List<CastAndCrewResource> getSound() {
        if (sound == null) {
            sound = new ArrayList<>();
        }
        return sound;
    }

    public MovieResource setSound(List<CastAndCrewResource> sound) {
        this.sound = sound;
        return this;
    }

    public List<CastAndCrewResource> getArt() {
        if (art == null) {
            art = new ArrayList<>();
        }
        return art;
    }

    public MovieResource setArt(List<CastAndCrewResource> art) {
        this.art = art;
        return this;
    }

    public List<CastAndCrewResource> getOtherRoles() {
        if (otherRoles == null) {
            otherRoles = new ArrayList<>();
        }
        return otherRoles;
    }

    public MovieResource setOtherRoles(List<CastAndCrewResource> otherRoles) {
        this.otherRoles = otherRoles;
        return this;
    }

    public MovieFormatInfoResource getMovieFormatInfo() {
        return movieFormatInfo;
    }

    public MovieResource setMovieFormatInfo(MovieFormatInfoResource movieFormatInfo) {
        this.movieFormatInfo = movieFormatInfo;
        return this;
    }

    public MoviePersonalInfoResource getMoviePersonalInfo() {
        return moviePersonalInfo;
    }

    public MovieResource setMoviePersonalInfo(MoviePersonalInfoResource moviePersonalInfo) {
        this.moviePersonalInfo = moviePersonalInfo;
        return this;
    }
}
