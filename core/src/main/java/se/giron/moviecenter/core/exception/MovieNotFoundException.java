package se.giron.moviecenter.core.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 9029937920762731509L;

    public MovieNotFoundException(Long id) {
        super("could not find movie '" + id + "'.");
    }

    public MovieNotFoundException(String id) {
        super("could not find movie '" + id + "'.");
    }
}
