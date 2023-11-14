package se.giron.moviecenter.core.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.giron.moviecenter.core.exception.ValidationException;
import se.giron.moviecenter.core.service.ImportLogService;
import se.giron.moviecenter.core.service.MovieService;
import se.giron.moviecenter.core.service.PersonService;
import se.giron.moviecenter.model.resource.MovieResource;
import se.giron.moviecenter.model.resource.error.ErrorResponse;
import se.giron.moviecenter.model.resource.imports.AdapterResponse;
import se.giron.moviecenter.model.resource.imports.MovieTransferResource;

import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping({"/adapter"})
public class AdapterController implements MessageSourceAware {

    @Autowired
    private MovieService movieService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ImportLogService importLogService;

    private MessageSourceAccessor messageSource;

    @PostMapping("/movie")
    public ResponseEntity<AdapterResponse> importMovie(@RequestBody @Valid MovieTransferResource movieTransferResource) {
        try {
            Optional<MovieResource> oMovieResource = movieService.addMovie(movieTransferResource.getMovie());

            if (oMovieResource.isPresent()) {
                return new ResponseEntity<>(new AdapterResponse("Success", CREATED, null), CREATED);
            }
            return new ResponseEntity<>(new AdapterResponse("Already added", ALREADY_REPORTED, null), ALREADY_REPORTED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new AdapterResponse(getMessageByKey(e.getMessageCode()), BAD_REQUEST, e.getMessage()), OK);
        }
    }

    @PostMapping("/movie/import-log")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLog(@RequestBody @Valid MovieTransferResource movieTransferResource) {
        importLogService.createLog(movieTransferResource);
    }

    @ApiOperation(value = "Delete all movies and persons in the database. Non-revokeable! Use only for reset purpose!")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Movies and persons deleted"),
            @ApiResponse(code = 403, message = "User is not allowed to delete movies and persons"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @DeleteMapping("/movie")
    public ResponseEntity<Void> clearDatabase() {
        personService.deleteAll();
        movieService.deleteAll();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private String getMessageByKey(String messageOrKey) {
        String message = messageOrKey;
        try {
            message = messageSource.getMessage(messageOrKey);
            if (StringUtils.isEmpty(message)) {
                message = messageOrKey;
            }
        } catch (NoSuchMessageException ignored) {
        }  // We will return message text (parameter value) in this case.

        return message;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }
}
