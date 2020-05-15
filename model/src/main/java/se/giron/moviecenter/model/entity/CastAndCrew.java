package se.giron.moviecenter.model.entity;

import javax.persistence.*;

@Entity
public class CastAndCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_role_id", nullable = false)
    private PersonRole personRole;

    private String characterName;

    @Transient
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public CastAndCrew setId(Long id) {
        this.id = id;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public CastAndCrew setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public PersonRole getPersonRole() {
        return personRole;
    }

    public CastAndCrew setPersonRole(PersonRole personRole) {
        this.personRole = personRole;
        return this;
    }

    public String getCharacterName() {
        return characterName;
    }

    public CastAndCrew setCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public CastAndCrew setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
