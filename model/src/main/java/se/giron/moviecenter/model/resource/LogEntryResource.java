package se.giron.moviecenter.model.resource;

import se.giron.moviecenter.model.enums.LogLevelEnum;

public class LogEntryResource {

    private LogLevelEnum level;
    private String message;
    private String userAgent;
    private String stackTrace;

    public LogLevelEnum getLevel() {
        return level;
    }

    public LogEntryResource setLevel(LogLevelEnum level) {
        this.level = level;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogEntryResource setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LogEntryResource setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public LogEntryResource setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }
}
