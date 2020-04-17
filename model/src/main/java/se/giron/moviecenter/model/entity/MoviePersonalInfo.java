package se.giron.moviecenter.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class MoviePersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "grade", columnDefinition = "TINYINT")
    private Integer grade;

    private Date obtainDate;

    private BigDecimal obtainPrice;

    private String currency;

    private String obtainPlace;

    private String notes;

    public Long getId() {
        return id;
    }

    public MoviePersonalInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public MoviePersonalInfo setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public Integer getGrade() {
        return grade;
    }

    public MoviePersonalInfo setGrade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public Date getObtainDate() {
        return obtainDate;
    }

    public MoviePersonalInfo setObtainDate(Date obtainDate) {
        this.obtainDate = obtainDate;
        return this;
    }

    public BigDecimal getObtainPrice() {
        return obtainPrice;
    }

    public MoviePersonalInfo setObtainPrice(BigDecimal obtainPrice) {
        this.obtainPrice = obtainPrice;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public MoviePersonalInfo setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getObtainPlace() {
        return obtainPlace;
    }

    public MoviePersonalInfo setObtainPlace(String obtainPlace) {
        this.obtainPlace = obtainPlace;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public MoviePersonalInfo setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
