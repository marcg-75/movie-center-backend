package se.giron.moviecenter.model.entity;

import javax.persistence.*;

@Entity
public class Cover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "movie_format_id")
    private MovieFormatInfo movieFormatInfo;

    private String fgFileName;

    private String bgFileName;

    private String foregroundUrl;

    private String backgroundUrl;

    public Long getId() {
        return id;
    }

    public Cover setId(Long id) {
        this.id = id;
        return this;
    }

    public MovieFormatInfo getMovieFormatInfo() {
        return movieFormatInfo;
    }

    public Cover setMovieFormatInfo(MovieFormatInfo movieFormatInfo) {
        this.movieFormatInfo = movieFormatInfo;
        return this;
    }

    public String getFgFileName() {
        return fgFileName;
    }

    public Cover setFgFileName(String fgFileName) {
        this.fgFileName = fgFileName;
        return this;
    }

    public String getBgFileName() {
        return bgFileName;
    }

    public Cover setBgFileName(String bgFileName) {
        this.bgFileName = bgFileName;
        return this;
    }

    public String getForegroundUrl() {
        return foregroundUrl;
    }

    public Cover setForegroundUrl(String foregroundUrl) {
        this.foregroundUrl = foregroundUrl;
        return this;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public Cover setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
        return this;
    }
}
