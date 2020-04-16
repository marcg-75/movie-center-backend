package se.giron.moviecenter.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class MoviePersonRoleId implements Serializable {

    private Long movieId;
    private Long personId;
    private String roleCode;

    public MoviePersonRoleId() {
        this.movieId = -1L;
        this.personId = -1L;
        this.roleCode = "MISC";
    }

    public MoviePersonRoleId(Long userId, Long personId, String roleCode) {
        this.movieId = userId;
        this.personId = personId;
        this.roleCode = roleCode;
    }

    public Long getMovieId() {
        return movieId;
    }

    public MoviePersonRoleId setMovieId(Long movieId) {
        this.movieId = movieId;
        return this;
    }

    public Long getPersonId() {
        return personId;
    }

    public MoviePersonRoleId setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public MoviePersonRoleId setRoleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviePersonRoleId that = (MoviePersonRoleId) o;
        return Objects.equals(getMovieId(), that.getMovieId()) &&
                Objects.equals(getPersonId(), that.getPersonId()) &&
                Objects.equals(getRoleCode(), that.getRoleCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieId(), getPersonId(), getRoleCode());
    }
}
