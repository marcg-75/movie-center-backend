package se.giron.moviecenter.model.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraph(name = "Movie", attributeNodes = @NamedAttributeNode("studios"))
//@DiscriminatorValue("MOVIE")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String originalTitle;

    private String description;

    @ManyToOne
    @JoinColumn(name="main_genre_cd")
    private Genre mainGenre;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_subgenre",
            joinColumns = { @JoinColumn(name = "movie_id")},
            inverseJoinColumns = { @JoinColumn(name = "genre_cd")}
    )
    private Set<Genre> additionalGenres = new HashSet<>();

    private Time runtime;

    private Date releaseDate;

    private String country;

    private String ageRestriction;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "movie_studio",
            joinColumns = { @JoinColumn(name = "movie_id")},
            inverseJoinColumns = { @JoinColumn(name = "studio_id")}
    )
    private Set<Studio> studios = new HashSet<>();

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "movie_id")
    private Set<CastAndCrew> castAndCrew = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "movie")
    private MovieFormatInfo movieFormatInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "movie")
    private MoviePersonalInfo moviePersonalInfo;

    public Long getId() {
        return id;
    }

    public Movie setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Movie setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Movie setDescription(String description) {
        this.description = description;
        return this;
    }

    public Genre getMainGenre() {
        return mainGenre;
    }

    public Movie setMainGenre(Genre mainGenre) {
        this.mainGenre = mainGenre;
        return this;
    }

    public Set<Genre> getAdditionalGenres() {
        return additionalGenres;
    }

    public Movie setAdditionalGenres(Set<Genre> additionalGenres) {
        this.additionalGenres = additionalGenres;
        return this;
    }

    public Time getRuntime() {
        return runtime;
    }

    public Movie setRuntime(Time runtime) {
        this.runtime = runtime;
        return this;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Movie setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Movie setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public Movie setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
        return this;
    }

    public MovieFormatInfo getMovieFormatInfo() {
        return movieFormatInfo;
    }

    public Movie setMovieFormatInfo(MovieFormatInfo movieFormatInfo) {
        this.movieFormatInfo = movieFormatInfo;
        return this;
    }

    public MoviePersonalInfo getMoviePersonalInfo() {
        return moviePersonalInfo;
    }

    public Movie setMoviePersonalInfo(MoviePersonalInfo moviePersonalInfo) {
        this.moviePersonalInfo = moviePersonalInfo;
        return this;
    }

    public Set<Studio> getStudios() {
        return studios;
    }

    public Movie setStudios(Set<Studio> studios) {
        this.studios = studios;
        return this;
    }

    public Set<CastAndCrew> getCastAndCrew() {
        return castAndCrew;
    }

    public Movie setCastAndCrew(Set<CastAndCrew> castAndCrew) {
        this.castAndCrew = castAndCrew;
        return this;
    }
}
