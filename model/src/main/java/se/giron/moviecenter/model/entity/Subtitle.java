package se.giron.moviecenter.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "movie_format_subtitle_languages")
public class Subtitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_format_id", nullable=false)
    private MovieFormatInfo movieFormatInfo;

    private String language;

    public Long getId() {
        return id;
    }

    public Subtitle setId(Long id) {
        this.id = id;
        return this;
    }

    public MovieFormatInfo getMovieFormatInfo() {
        return movieFormatInfo;
    }

    public Subtitle setMovieFormatInfo(MovieFormatInfo movieFormatInfo) {
        this.movieFormatInfo = movieFormatInfo;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Subtitle setLanguage(String language) {
        this.language = language;
        return this;
    }
}
