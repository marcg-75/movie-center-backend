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

    @Lob
    @Column(name = "foreground", columnDefinition = "BLOB")
    @Deprecated
    private byte[] foreground;

    @Lob
    @Column(name = "background", columnDefinition = "BLOB")
    @Deprecated
    private byte[] background;

    private String foregroundUrl;  // TODO: Dessa ska även innehålla hela filen (se SBR2-backend)

    private String backgroundUrl;  // TODO: - '' -

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

    public byte[] getForeground() {
        return foreground;
    }

    public Cover setForeground(byte[] foreground) {
        this.foreground = foreground;
        return this;
    }

    public byte[] getBackground() {
        return background;
    }

    public Cover setBackground(byte[] background) {
        this.background = background;
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
