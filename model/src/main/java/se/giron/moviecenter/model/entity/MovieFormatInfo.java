package se.giron.moviecenter.model.entity;

import se.giron.moviecenter.model.enums.SystemEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
public class MovieFormatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "movieFormatInfo")
    private Cover cover;

    @ManyToOne
    @JoinColumn(name="format_cd")
    private Format format;

    @Column(name="region_cd", columnDefinition = "TINYINT")
    private Integer region;

    private String upcId;

    @Column(name = "discs", columnDefinition = "TINYINT")
    private Integer discs;

    private String pictureFormat;

    @Enumerated(EnumType.STRING)
    private SystemEnum system;

    @OneToMany(mappedBy="movieFormatInfo", fetch = FetchType.LAZY)
    private Set<AudioLanguage> audioLanguages;

    @OneToMany(mappedBy="movieFormatInfo", fetch = FetchType.LAZY)
    private Set<Subtitle> subtitles;

    public Long getId() {
        return id;
    }

    public MovieFormatInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieFormatInfo setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public Cover getCover() {
        return cover;
    }

    public MovieFormatInfo setCover(Cover cover) {
        this.cover = cover;
        return this;
    }

    public Format getFormat() {
        return format;
    }

    public MovieFormatInfo setFormat(Format format) {
        this.format = format;
        return this;
    }

    public Integer getRegion() {
        return region;
    }

    public MovieFormatInfo setRegion(Integer region) {
        this.region = region;
        return this;
    }

    public String getUpcId() {
        return upcId;
    }

    public MovieFormatInfo setUpcId(String upcId) {
        this.upcId = upcId;
        return this;
    }

    public Integer getDiscs() {
        return discs;
    }

    public MovieFormatInfo setDiscs(Integer discs) {
        this.discs = discs;
        return this;
    }

    public String getPictureFormat() {
        return pictureFormat;
    }

    public MovieFormatInfo setPictureFormat(String pictureFormat) {
        this.pictureFormat = pictureFormat;
        return this;
    }

    public SystemEnum getSystem() {
        return system;
    }

    public MovieFormatInfo setSystem(SystemEnum system) {
        this.system = system;
        return this;
    }

    public Set<AudioLanguage> getAudioLanguages() {
        return audioLanguages;
    }

    public MovieFormatInfo setAudioLanguages(Set<AudioLanguage> audioLanguages) {
        this.audioLanguages = audioLanguages;
        return this;
    }

    public Set<Subtitle> getSubtitles() {
        return subtitles;
    }

    public MovieFormatInfo setSubtitles(Set<Subtitle> subtitles) {
        this.subtitles = subtitles;
        return this;
    }
}
