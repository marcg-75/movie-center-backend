package se.giron.moviecenter.model.resource;

/**
 * Created by marc on 2020-04-17.
 */
public class CoverResource {

    private String fgFileName;

    private String bgFileName;

    private String foregroundUrl;

    private String backgroundUrl;

    public String getFgFileName() {
        return fgFileName;
    }

    public CoverResource setFgFileName(String fgFileName) {
        this.fgFileName = fgFileName;
        return this;
    }

    public String getBgFileName() {
        return bgFileName;
    }

    public CoverResource setBgFileName(String bgFileName) {
        this.bgFileName = bgFileName;
        return this;
    }

    public String getForegroundUrl() {
        return foregroundUrl;
    }

    public CoverResource setForegroundUrl(String foregroundUrl) {
        this.foregroundUrl = foregroundUrl;
        return this;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public CoverResource setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
        return this;
    }
}
