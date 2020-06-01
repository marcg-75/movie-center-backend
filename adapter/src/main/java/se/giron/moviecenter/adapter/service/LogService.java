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

//    public void logMovieMessage(Map<String, MovieTransferResource> sampleTransferResources, String message) {
//        sampleTransferResources.forEach((s, sampleTransferResource) -> sampleTransferResource.setStatusDescription(message));
//        movieWebServiceClient.createMovieLog(new MovieTransferWrapperResource(sampleTransferResources));
//    }

//    public void logExternalMessage(ExternalLogTransferResource externalLogTransferResource, String message) {
//        externalLogTransferResource.setStatusDescription(message);
//        movieWebServiceClient.createExternalLog(externalLogTransferResource);
//    }
}
