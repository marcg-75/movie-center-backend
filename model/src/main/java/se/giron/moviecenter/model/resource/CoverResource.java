package se.giron.moviecenter.model.resource;

/**
 * Created by marc on 2020-04-17.
 */
public class CoverResource {

    private byte[] foreground;

    private byte[] background;

    private String foregroundUrl;

    private String backgroundUrl;

    public byte[] getForeground() {
        return foreground;
    }

    public CoverResource setForeground(byte[] foreground) {
        this.foreground = foreground;
        return this;
    }

    public byte[] getBackground() {
        return background;
    }

    public CoverResource setBackground(byte[] background) {
        this.background = background;
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
