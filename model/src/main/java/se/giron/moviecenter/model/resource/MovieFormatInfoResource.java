package se.giron.moviecenter.model.resource;

import se.giron.moviecenter.model.entity.AudioLanguage;
import se.giron.moviecenter.model.entity.Format;
import se.giron.moviecenter.model.entity.Subtitle;

import java.util.List;
import java.util.Set;

/**
 * Created by marc on 2020-04-17.
 */
public class MovieFormatInfoResource {

    private CoverResource cover;

    private Format format;

    private Integer region;

    private String upcId;

    private Integer discs;

    private String pictureFormat;

    private String system;

    private List<AudioLanguage> audioLanguages;

    private List<Subtitle> subtitles;

    public CoverResource getCover() {
        return cover;
    }

    public MovieFormatInfoResource setCover(CoverResource cover) {
        this.cover = cover;
        return this;
    }

    public Format getFormat() {
        return format;
    }

    public MovieFormatInfoResource setFormat(Format format) {
        this.format = format;
        return this;
    }

    public Integer getRegion() {
        return region;
    }

    public MovieFormatInfoResource setRegion(Integer region) {
        this.region = region;
        return this;
    }

    public String getUpcId() {
        return upcId;
    }

    public MovieFormatInfoResource setUpcId(String upcId) {
        this.upcId = upcId;
        return this;
    }

    public Integer getDiscs() {
        return discs;
    }

    public MovieFormatInfoResource setDiscs(Integer discs) {
        this.discs = discs;
        return this;
    }

    public String getPictureFormat() {
        return pictureFormat;
    }

    public MovieFormatInfoResource setPictureFormat(String pictureFormat) {
        this.pictureFormat = pictureFormat;
        return this;
    }

    public String getSystem() {
        return system;
    }

    public MovieFormatInfoResource setSystem(String system) {
        this.system = system;
        return this;
    }

    public List<AudioLanguage> getAudioLanguages() {
        return audioLanguages;
    }

    public MovieFormatInfoResource setAudioLanguages(List<AudioLanguage> audioLanguages) {
        this.audioLanguages = audioLanguages;
        return this;
    }

    public List<Subtitle> getSubtitles() {
        return subtitles;
    }

    public MovieFormatInfoResource setSubtitles(List<Subtitle> subtitles) {
        this.subtitles = subtitles;
        return this;
    }
}
