package se.giron.moviecenter.model.resource.imports;

import org.springframework.http.HttpStatus;

public class AdapterResponse {
    private String message;
    private HttpStatus status;
    private String exception;

    public AdapterResponse() {
    }

    public AdapterResponse(String message, HttpStatus status, String exception) {
        this.message = message;
        this.status = status;
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
