package se.giron.moviecenter.model.resource.error;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ErrorResponse {

    private String uuid;
    private Date timestamp;
    private Integer code;
    private String error;
    private List<String> messages = new ArrayList<>();

    public ErrorResponse(HttpStatus httpStatus) {
        timestamp = Date.from(Instant.now());
        uuid = UUID.randomUUID().toString();
        code = httpStatus.value();
        error = httpStatus.getReasonPhrase();
    }

    public ErrorResponse message(String message) {
        messages.add(message);
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Integer getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public List<String> getMessages() {
        return messages;
    }
}