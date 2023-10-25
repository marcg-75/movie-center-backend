package se.giron.moviecenter.model.entity;

import se.giron.moviecenter.model.resource.imports.MovieImportStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MovieImportLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieTitle;

    private Date importStartTimestamp;

    private Date importEndTimestamp;

    private String importDuration;

    @Enumerated(EnumType.STRING)
    private MovieImportStatus status;

    private String statusDescription;

    private String filename;

    private Integer countMovies;

    private Integer countNewMovies;

    private Integer countUpdatedMovies;

    private Integer countFailedMovies;

    public Long getId() {
        return id;
    }

    public MovieImportLog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public MovieImportLog setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
        return this;
    }

    public Date getImportStartTimestamp() {
        return importStartTimestamp;
    }

    public MovieImportLog setImportStartTimestamp(Date importStartTimestamp) {
        this.importStartTimestamp = importStartTimestamp;
        return this;
    }

    public Date getImportEndTimestamp() {
        return importEndTimestamp;
    }

    public MovieImportLog setImportEndTimestamp(Date importEndTimestamp) {
        this.importEndTimestamp = importEndTimestamp;
        return this;
    }

    public String getImportDuration() {
        return importDuration;
    }

    public MovieImportLog setImportDuration(String importDuration) {
        this.importDuration = importDuration;
        return this;
    }

    public MovieImportStatus getStatus() {
        return status;
    }

    public MovieImportLog setStatus(MovieImportStatus status) {
        this.status = status;
        return this;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public MovieImportLog setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public MovieImportLog setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Integer getCountMovies() {
        return countMovies;
    }

    public MovieImportLog setCountMovies(Integer countMovies) {
        this.countMovies = countMovies;
        return this;
    }

    public Integer getCountNewMovies() {
        return countNewMovies;
    }

    public MovieImportLog setCountNewMovies(Integer countNewMovies) {
        this.countNewMovies = countNewMovies;
        return this;
    }

    public Integer getCountUpdatedMovies() {
        return countUpdatedMovies;
    }

    public MovieImportLog setCountUpdatedMovies(Integer countUpdatedMovies) {
        this.countUpdatedMovies = countUpdatedMovies;
        return this;
    }

    public Integer getCountFailedMovies() {
        return countFailedMovies;
    }

    public MovieImportLog setCountFailedMovies(Integer countFailedMovies) {
        this.countFailedMovies = countFailedMovies;
        return this;
    }
}
