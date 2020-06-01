package se.giron.moviecenter.model.resource;

import java.util.Date;

/**
 * Created by marc on 2020-04-17.
 */
public class MoviePersonalInfoResource {

    private Integer archiveNumber;

    private Double grade;

    private Date obtainDate;

    private Double obtainPrice;

    private String currency;

    private String obtainPlace;

    private String notes;

    public Integer getArchiveNumber() {
        return archiveNumber;
    }

    public MoviePersonalInfoResource setArchiveNumber(Integer archiveNumber) {
        this.archiveNumber = archiveNumber;
        return this;
    }

    public Double getGrade() {
        return grade;
    }

    public MoviePersonalInfoResource setGrade(Double grade) {
        this.grade = grade;
        return this;
    }

    public Date getObtainDate() {
        return obtainDate;
    }

    public MoviePersonalInfoResource setObtainDate(Date obtainDate) {
        this.obtainDate = obtainDate;
        return this;
    }

    public Double getObtainPrice() {
        return obtainPrice;
    }

    public MoviePersonalInfoResource setObtainPrice(Double obtainPrice) {
        this.obtainPrice = obtainPrice;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public MoviePersonalInfoResource setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getObtainPlace() {
        return obtainPlace;
    }

    public MoviePersonalInfoResource setObtainPlace(String obtainPlace) {
        this.obtainPlace = obtainPlace;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public MoviePersonalInfoResource setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
