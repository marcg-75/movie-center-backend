package se.giron.moviecenter.model.resource.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MovieFilter")
public class MovieFilter {

    @ApiModelProperty(notes = "Movie title")
    private String title;

    @ApiModelProperty(notes = "Genre Code")
    private String genre;

    @ApiModelProperty(notes = "Format")
    private String format;

    @ApiModelProperty(notes = "My grade", dataType = "java.lang.Integer")
    private Integer grade;

    @ApiModelProperty(notes = "Free text filter query")
    private String q;


    public String getTitle() {
        return title;
    }

    public MovieFilter setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public MovieFilter setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public MovieFilter setFormat(String format) {
        this.format = format;
        return this;
    }

    public Integer getGrade() {
        return grade;
    }

    public MovieFilter setGrade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public String getQ() {
        return q;
    }

    public MovieFilter setQ(String q) {
        this.q = q;
        return this;
    }
}
