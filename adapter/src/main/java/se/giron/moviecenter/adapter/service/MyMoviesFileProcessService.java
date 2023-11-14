package se.giron.moviecenter.adapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.giron.moviecenter.adapter.transform.mymovies.MyMoviesXmlConverter;
import se.giron.moviecenter.adapter.web.MovieWebServiceClient;
import se.giron.moviecenter.model.resource.MovieResource;
import se.giron.moviecenter.model.resource.imports.AdapterResponse;
import se.giron.moviecenter.model.resource.imports.MovieImportStatus;
import se.giron.moviecenter.model.resource.imports.MovieTransferResource;
import se.giron.moviecenter.mymovies.TitleType;
import se.giron.moviecenter.mymovies.TitlesType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static se.giron.moviecenter.adapter.transform.mymovies.MovieMapperUtils.movie2resource;
import static se.giron.moviecenter.adapter.util.MovieCenterUtil.listFiles;
import static se.giron.moviecenter.adapter.util.MovieCenterUtil.moveFile;

@Service
public class MyMoviesFileProcessService {

	private final Logger LOG = LoggerFactory.getLogger(MyMoviesFileProcessService.class);

	@Autowired
	MyMoviesXmlConverter myMoviesXmlConverter;

	@Autowired
	private MovieWebServiceClient movieWebServiceClient;

	@Autowired
	private LogService logService;

	@Value("${adapter.directory.indata.mymovies:}")
	private String filePath;

	@Value("${adapter.directory.ok.mymovies:}")
	private String okPath;

	@Value("${adapter.directory.error.mymovies:}")
	private String errorPath;

	@Value("${adapter.directory.failed.mymovies:}")
	private String failedPath;

	@Value("${clearDbBeforeImport:}")
	private Boolean clearDbBeforeImport;

	@Async("threadPoolTaskExecutor")
	public void process( ) {
        //final String pattern = "[0-9]+[\\S\\s]*.xml";
        final String pattern = "[\\S\\s]*.xml";

		if (this.clearDbBeforeImport) {
			LOG.info("Clearing movie database ...");
			movieWebServiceClient.clearDatabase();
			LOG.info("Clearing movie database done");
		}

		try {
			LOG.info("Running processFiles ...");

            List<Path> filesList = listFiles(filePath, pattern);
			if (!filesList.isEmpty()) {
				filesList.forEach(processFile);
			} else {
				LOG.info("No files detected in " + filePath);
			}
		} catch(IOException e) {
			LOG.error("Failed to process files", e);
		}
	}

	private final Function<Path, TitlesType> xmlToObjects = xmlFile -> {
		if (!xmlFile.toFile().exists()) {
			LOG.error("Kunde inte hitta filen {}", xmlFile);
			throw new RuntimeException(new FileNotFoundException(xmlFile.toString()));
		}
		try {
			return myMoviesXmlConverter.convertFromXMLToObject(xmlFile);
		} catch (IOException e) {
			LOG.error("Något gick fel vid inläsning av fil", e);
			throw new RuntimeException(e);
		}
	};

	private final Consumer<Path> processFile = xmlFile -> {
		MovieTransferResource movieTransferResource = null;
		boolean processingStatusOk = true;
		boolean statusImportOk = true;
		final TitlesType allMoviesTransfer;
		final String fileName = xmlFile.getFileName().toString();
		MovieTransferResource overallMovieTransferResource = initOverallImportLog(fileName);

		int nSuccessfulMovies = 0;
		int nIgnoredMovies = 0;
		int nFailedMovies = 0;

		LOG.info("Importing movies from file " + fileName);

		try {
			// Read the file
			allMoviesTransfer = xmlToObjects.apply(xmlFile);
			overallMovieTransferResource.setCountTotal(allMoviesTransfer.getTitle().size());

			for (TitleType title : allMoviesTransfer.getTitle()) {
				movieTransferResource = processTitle(title, fileName);

				if (MovieImportStatus.FAILED.equals(movieTransferResource.getStatus())) {
					LOG.error("Failed to process title", movieTransferResource.getFailure());
					movieTransferResource.setCountTotal(1);
					logService.logMovieMessage(movieTransferResource, movieTransferResource.getStatusDescription());
					nFailedMovies++;
					processingStatusOk = false;
				} else {
					try {
						AdapterResponse response = movieWebServiceClient.importMovie(movieTransferResource);

						if (response.getStatus() == HttpStatus.CREATED) {
							nSuccessfulMovies++;
						} else if (response.getStatus() == HttpStatus.ALREADY_REPORTED) {
							nIgnoredMovies++;
						} else {
							LOG.error(response.getMessage());
							movieTransferResource.setStatus(MovieImportStatus.FAILED);
							logService.logMovieMessage(movieTransferResource, response.getMessage());
							nFailedMovies++;
							processingStatusOk = false;
							statusImportOk = false;
						}
					} catch (Exception e) {
						LOG.error("Failed to create movie from parsed title", e);
						nFailedMovies++;
						statusImportOk = false;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Failed to process file {}", xmlFile, e);
			nFailedMovies = overallMovieTransferResource.getCountTotal() - nSuccessfulMovies;
			processingStatusOk = false;
		}

		// Overall import logging.
		overallMovieTransferResource.setCountSuccessful(nSuccessfulMovies);
		overallMovieTransferResource.setCountIgnored(nIgnoredMovies);
		overallMovieTransferResource.setCountFailed(nFailedMovies);
		overallMovieTransferResource.setStatus(processingStatusOk && statusImportOk ? MovieImportStatus.SUCCESS : MovieImportStatus.FAILED);
		logService.logMovieMessage(overallMovieTransferResource, "Movie import execution done");

		archiveFile(xmlFile, processingStatusOk, statusImportOk);
	};

	private void archiveFile(final Path xmlFile, final boolean statusOk, final boolean statusImportOk) {
		try {
			// Move to archive
            if (!statusOk) {
                moveFile(xmlFile, FileSystems.getDefault().getPath(errorPath));
                LOG.warn("Processing of file {} failed due to errors in file. The file was archived to {}", xmlFile.getFileName(), errorPath);
            } else if (!statusImportOk) {
                moveFile(xmlFile, FileSystems.getDefault().getPath(failedPath));
                LOG.warn("Failed to import movies in file {} due to errors while processing the data. The file was archived to {}", xmlFile.getFileName(), failedPath);
            } else {
                moveFile(xmlFile, FileSystems.getDefault().getPath(okPath));
                LOG.info("File {} has been processed and archived to {}", xmlFile.getFileName(), okPath);
            }
		} catch (IOException e) {
			LOG.error("An error occurred while attempting to archive {}", xmlFile, e);
		}
	}

	private MovieTransferResource processTitle(TitleType movieTransfer, String fileName) {
		MovieTransferResource movieTransferResource = new MovieTransferResource();

		try {
			MovieResource movieResource = movie2resource(movieTransfer);

			movieTransferResource
					.setStatus(MovieImportStatus.SUCCESS)
					.setMovie(movieResource);
		} catch (Exception e) {
			movieTransferResource
					.setStatus(MovieImportStatus.FAILED)
					.setStatusDescription(e.getMessage())
					.setCountFailed(1)
					.setFailure(e)
					.setMovie(new MovieResource().setTitle(movieTransfer.getTitle()));
		}

		movieTransferResource
				.setImportDate(Date.from(Instant.now()))
				.setFileName(fileName)
				.setCountTotal(1);

		return movieTransferResource;
	}

	@Deprecated
	private MovieTransferResource processTitleError(TitleType movieTransfer, String fileName, final Exception error) {
		MovieTransferResource movieTransferResource = new MovieTransferResource();

		movieTransferResource
				.setImportDate(Date.from(Instant.now()))
				.setStatus(MovieImportStatus.FAILED)
				.setStatusDescription(error.getMessage())
				.setMovie(movie2resource(movieTransfer))
				.setFileName(fileName);

		return movieTransferResource;
	}

	private MovieTransferResource initOverallImportLog(String fileName) {
		return new MovieTransferResource()
				.setImportDate(Date.from(Instant.now()))
				.setFileName(fileName);
	}
}
