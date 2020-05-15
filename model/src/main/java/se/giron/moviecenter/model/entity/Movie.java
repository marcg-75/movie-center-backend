package se.giron.moviecenter.model.entity;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_studio",
            joinColumns = { @JoinColumn(name = "movie_id")},
            inverseJoinColumns = { @JoinColumn(name = "studio_id")}
    )
    private Set<Studio> studios;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "movie_id")
    private Set<CastAndCrew> castAndCrew = new HashSet<>();

//    @OneToMany
//    @JoinTable(
//            name="cast_and_crew",
//            joinColumns = @JoinColumn( name="movie_id"),
//            inverseJoinColumns = @JoinColumn( name="person_role_id")
//    )
//
//    @OneToMany(mappedBy="movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @Where(clause = "role_cd = 'ACTOR'")
//    private Set<CastAndCrew> actors = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_role_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'DIRECTOR'")
//    private Set<Person> directors = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'PRODUCER'")
//    private Set<Person> producers = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'MUSIC'")
//    private Set<Person> music = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'WRITER'")
//    private Set<Person> writers = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'CASTING'")
//    private Set<Person> casters = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'EDITOR'")
//    private Set<Person> editors = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'CINEMATOGRAPHY'")
//    private Set<Person> cinematography = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'SOUND'")
//    private Set<Person> sound = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'ART'")
//    private Set<Person> art = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cast_and_crew",
//            joinColumns = { @JoinColumn(name = "movie_id")},
//            inverseJoinColumns = { @JoinColumn(name = "person_id")}
//    )
//    @WhereJoinTable(clause = "role_cd = 'MISC'")
//    private Set<Person> otherRoles = new HashSet<>();

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

//    public Set<CastAndCrew> getActors() {
//        return actors;
//    }
//
//    public Movie setActors(Set<CastAndCrew> actors) {
//        this.actors = actors;
//        return this;
//    }
//
//    public Set<Person> getDirectors() {
//        return directors;
//    }
//
//    public Movie setDirectors(Set<Person> directors) {
//        this.directors = directors;
//        return this;
//    }
//
//    public Set<Person> getProducers() {
//        return producers;
//    }
//
//    public Movie setProducers(Set<Person> producers) {
//        this.producers = producers;
//        return this;
//    }
//
//    public Set<Person> getMusic() {
//        return music;
//    }
//
//    public Movie setMusic(Set<Person> music) {
//        this.music = music;
//        return this;
//    }
//
//    public Set<Person> getWriters() {
//        return writers;
//    }
//
//    public Movie setWriters(Set<Person> writers) {
//        this.writers = writers;
//        return this;
//    }
//
//    public Set<Person> getCasters() {
//        return casters;
//    }
//
//    public Movie setCasters(Set<Person> casters) {
//        this.casters = casters;
//        return this;
//    }
//
//    public Set<Person> getEditors() {
//        return editors;
//    }
//
//    public Movie setEditors(Set<Person> editors) {
//        this.editors = editors;
//        return this;
//    }
//
//    public Set<Person> getCinematography() {
//        return cinematography;
//    }
//
//    public Movie setCinematography(Set<Person> cinematography) {
//        this.cinematography = cinematography;
//        return this;
//    }
//
//    public Set<Person> getSound() {
//        return sound;
//    }
//
//    public Movie setSound(Set<Person> sound) {
//        this.sound = sound;
//        return this;
//    }
//
//    public Set<Person> getArt() {
//        return art;
//    }
//
//    public Movie setArt(Set<Person> art) {
//        this.art = art;
//        return this;
//    }
//
//    public Set<Person> getOtherRoles() {
//        return otherRoles;
//    }
//
//    public Movie setOtherRoles(Set<Person> otherRoles) {
//        this.otherRoles = otherRoles;
//        return this;
//    }
}
