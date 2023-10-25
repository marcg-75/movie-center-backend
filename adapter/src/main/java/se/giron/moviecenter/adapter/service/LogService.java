package se.giron.moviecenter.adapter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.giron.moviecenter.adapter.web.MovieWebServiceClient;
import se.giron.moviecenter.model.resource.imports.MovieTransferResource;

import java.util.Map;

@Service
public class LogService {

    @Autowired
    private MovieWebServiceClient movieWebServiceClient;

    public void logMovieMessage(MovieTransferResource movieTransferResource, String message) {
        movieTransferResource.setStatusDescription(message);
        movieWebServiceClient.createMovieLog(movieTransferResource);
    }
}
