package se.giron.moviecenter.adapter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.giron.moviecenter.model.resource.imports.AdapterResponse;
import se.giron.moviecenter.model.resource.imports.MovieTransferResource;

@Service
public class MovieWebServiceClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Value("${moviecenter.backend.url}")
	String URL_CREATE_MOVIE;

	public AdapterResponse createMovies(MovieTransferResource movieTransferResource) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return restTemplate.postForEntity(URL_CREATE_MOVIE, new HttpEntity<>(movieTransferResource, headers), AdapterResponse.class).getBody();
	}

	public void createMovieLog(MovieTransferResource movieTransferResource) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplate.postForEntity(URL_CREATE_MOVIE + "/import-log", new HttpEntity<>(movieTransferResource, headers), Object.class);
	}

	public void clearDatabase() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplate.delete(URL_CREATE_MOVIE, new HttpEntity<>(headers));
	}

	public String getURL() {
		return URL_CREATE_MOVIE;
	}

}
