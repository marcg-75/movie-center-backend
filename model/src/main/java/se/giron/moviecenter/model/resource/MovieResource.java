package se.giron.moviecenter.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.entity.MoviePersonRole;
import se.giron.moviecenter.model.entity.Studio;

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

    @NotBlank(message = "{movie.description.notnull}")
    private String description;

    @NotNull
    private Genre mainGenre;

    private List<Genre> additionalGenres = new ArrayList<>();

    private Time runtime;

    private Date releaseDate;

    private String country;

    private String ageRestriction;

    private Set<Studio> studios;

    private Set<MoviePersonRole> castAndCrew;

    private List<PersonResource> actors;

    private List<PersonResource> directors;

    private List<PersonResource> producers;

    private List<PersonResource> music;

    private List<PersonResource> writers;

    private List<PersonResource> casters;

    private List<PersonResource> editors;

    private List<PersonResource> cinematography;

    private List<PersonResource> sound;

    private List<PersonResource> art;

    private List<PersonResource> otherRoles;

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

    public Set<MoviePersonRole> getCastAndCrew() {
        return castAndCrew;
    }

    public MovieResource setCastAndCrew(Set<MoviePersonRole> castAndCrew) {
        this.castAndCrew = castAndCrew;
        return this;
    }

    public List<PersonResource> getActors() {
        return actors;
    }

    public MovieResource setActors(List<PersonResource> actors) {
        this.actors = actors;
        return this;
    }

    public List<PersonResource> getDirectors() {
        return directors;
    }

    public MovieResource setDirectors(List<PersonResource> directors) {
        this.directors = directors;
        return this;
    }

    public List<PersonResource> getProducers() {
        return producers;
    }

    public MovieResource setProducers(List<PersonResource> producers) {
        this.producers = producers;
        return this;
    }

    public List<PersonResource> getMusic() {
        return music;
    }

    public MovieResource setMusic(List<PersonResource> music) {
        this.music = music;
        return this;
    }

    public List<PersonResource> getWriters() {
        return writers;
    }

    public MovieResource setWriters(List<PersonResource> writers) {
        this.writers = writers;
        return this;
    }

    public List<PersonResource> getCasters() {
        return casters;
    }

    public MovieResource setCasters(List<PersonResource> casters) {
        this.casters = casters;
        return this;
    }

    public List<PersonResource> getEditors() {
        return editors;
    }

    public MovieResource setEditors(List<PersonResource> editors) {
        this.editors = editors;
        return this;
    }

    public List<PersonResource> getCinematography() {
        return cinematography;
    }

    public MovieResource setCinematography(List<PersonResource> cinematography) {
        this.cinematography = cinematography;
        return this;
    }

    public List<PersonResource> getSound() {
        return sound;
    }

    public MovieResource setSound(List<PersonResource> sound) {
        this.sound = sound;
        return this;
    }

    public List<PersonResource> getArt() {
        return art;
    }

    public MovieResource setArt(List<PersonResource> art) {
        this.art = art;
        return this;
    }

    public List<PersonResource> getOtherRoles() {
        return otherRoles;
    }

    public MovieResource setOtherRoles(List<PersonResource> otherRoles) {
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
