package se.giron.moviecenter.model.entity;

import javax.persistence.*;

@Entity
//@Table(name = "movie_person_role")
//@IdClass(MoviePersonRoleId.class)
public class MoviePersonRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Id
    //@Column(name = "movie_id")
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    //@Id
    //@Column(name = "person_id")
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    //@Id
    //@Column(name = "role_cd")
    @ManyToOne
    @JoinColumn(name = "role_cd", nullable = false)
    private Role role;

    public Movie getMovie() {
        return movie;
    }

    public MoviePersonRole setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public MoviePersonRole setPerson(Person person) {
        this.person = person;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public MoviePersonRole setRole(Role role) {
        this.role = role;
        return this;
    }
}
