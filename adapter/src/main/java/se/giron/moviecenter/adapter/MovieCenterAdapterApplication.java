package se.giron.moviecenter.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import se.giron.moviecenter.adapter.service.DvdProfilerFileProcessService;
import se.giron.moviecenter.adapter.service.MyMoviesFileProcessService;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "se.giron.moviecenter")
@EnableAsync
public class MovieCenterAdapterApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MovieCenterAdapterApplication.class, args);
	}

	@Value("${adapter.variant:}")
	private String adapterVariant;

	@Autowired
	MyMoviesFileProcessService myMoviesFileProcessService;

	@Autowired
	DvdProfilerFileProcessService dvdProfilerFileProcessService;

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Stockholm"));

		// If we have any files to import, and file import is enabled: run import job

		if (adapterVariant.equals("mymovies")) {
			myMoviesFileProcessService.process();
		} else {
			dvdProfilerFileProcessService.process();
		}
	}
}