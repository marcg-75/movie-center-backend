package se.giron.moviecenter.core.controller;

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
import se.giron.moviecenter.core.service.MovieService;
import se.giron.moviecenter.model.resource.imports.AdapterResponse;
import se.giron.moviecenter.model.resource.imports.MovieTransferResource;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping({"/adapter"})
public class AdapterController implements MessageSourceAware {

    @Autowired
    private MovieService movieService;

//    @Autowired
//    private MovieImportLogService movieImportLogService;

    private MessageSourceAccessor messageSource;

    @PostMapping("/movies")
    public ResponseEntity<AdapterResponse> createMovie(@RequestBody @Valid MovieTransferResource movieTransferResource) {
        try {
            movieService.createMovie(movieTransferResource.getMovie());

            return new ResponseEntity<>(new AdapterResponse("Success", CREATED, null), CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new AdapterResponse(getMessageByKey(e.getMessageCode()), BAD_REQUEST, e.getMessage()), OK);
        }
    }

//    @PostMapping("/movie/import-logs")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createLog(@RequestBody @Valid SampleTransferWrapperResource sampleTransferResources) {
//        movieImportLogService.createLog(sampleTransferResources.getTransferResources());
//    }

    private String getMessageByKey(String messageOrKey) {
        String message = messageOrKey;
        try {
            message = messageSource.getMessage(messageOrKey);
            if (StringUtils.isEmpty(message)) {
                message = messageOrKey;
            }
        } catch (NoSuchMessageException ignored) {}  // We will return message text (parameter value) in this case.

        return message;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }
}
