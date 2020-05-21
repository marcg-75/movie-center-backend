package se.giron.moviecenter.model.resource;

import se.giron.moviecenter.model.entity.Format;
import se.giron.moviecenter.model.entity.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marc on 2020-04-17.
 */
public class MovieFormatInfoResource {

    private CoverResource cover = new CoverResource();

    private Format format;

    private Integer region;

    private String upcId;

    private Integer discs;

    private String pictureFormat;

    private String system;

    private List<Language> audioLanguages = new ArrayList<>();

    private List<Language> subtitles = new ArrayList<>();

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

    public List<Language> getAudioLanguages() {
        return audioLanguages;
    }

    public MovieFormatInfoResource setAudioLanguages(List<Language> audioLanguages) {
        this.audioLanguages = audioLanguages;
        return this;
    }

    public List<Language> getSubtitles() {
        return subtitles;
    }

    public MovieFormatInfoResource setSubtitles(List<Language> subtitles) {
        this.subtitles = subtitles;
        return this;
    }
}
