package se.giron.moviecenter.adapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.giron.moviecenter.adapter.transform.XmlConverter;
import se.giron.moviecenter.adapter.web.MovieWebServiceClient;
import se.giron.moviecenter.dvdprofiler.DVD;
import se.giron.moviecenter.model.resource.imports.MovieImportStatus;
import se.giron.moviecenter.model.resource.imports.MovieTransferResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static se.giron.moviecenter.adapter.transform.MovieMapperUtils.movie2resource;
import static se.giron.moviecenter.adapter.util.MovieCenterUtil.listFiles;
import static se.giron.moviecenter.adapter.util.MovieCenterUtil.moveFile;

@Service
public class FileProcessService {

	private final Logger LOG = LoggerFactory.getLogger(FileProcessService.class);

	@Autowired
	XmlConverter xmlConverter;

	@Autowired
	private MovieWebServiceClient movieWebServiceClient;

    @Value("${adapter.directory.indata:}")
	private String filePath;

	@Value("${adapter.directory.ok:}")
	private String okPath;

	@Value("${adapter.directory.error:}")
	private String errorPath;

    @Value("${adapter.directory.failed:}")
    private String failedPath;

	@Async("threadPoolTaskExecutor")
	public void process( ) {
        final String pattern = "[0-9]*.xml";

		try {
			LOG.info("Running processFiles ...");

            List<Path> filesList = listFiles(filePath, pattern);
			if (filesList != null && !filesList.isEmpty()) {
				filesList.stream().forEach(processFile);
			}
		} catch(IOException e) {
			LOG.error("Failed to process files", e);
		}
	}

	private final Consumer<Path> processFile = xmlFile -> {
		MovieTransferResource movieTransferResource = null;
		boolean processingStatusOk = true;
		boolean statusImportOk = true;
		Exception exception = null;
		try {
			movieTransferResource = processFile(xmlFile);
		} catch(Exception e) {
			LOG.error("Failed to process file {}", xmlFile, e);
			exception = e;
			processingStatusOk = false;
		}
		try {
			if (exception != null) {
				movieTransferResource = processError(xmlFile, exception);
			}
			movieWebServiceClient.createMovies(movieTransferResource);
		} catch (Exception e) {
			LOG.error("Failed to create import log entry for file {}", xmlFile, e);
            statusImportOk = false;
		}

        String movieTitle = (movieTransferResource != null && movieTransferResource.getMovie() != null)
                ? movieTransferResource.getMovie().getTitle() : "";

		archiveFile(xmlFile, processingStatusOk, statusImportOk, movieTitle);
	};

	private void archiveFile(final Path xmlFile, final boolean statusOk, final boolean statusImportOk, String movieTitle) {
		try {
			// Move to archive
            if (!statusOk) {
                moveFile(xmlFile, FileSystems.getDefault().getPath(errorPath));
                LOG.warn("Processing of file {} failed due to errors in file. The file was archived to {}", xmlFile.getFileName(), errorPath);
            } else if (!statusImportOk) {
                moveFile(xmlFile, FileSystems.getDefault().getPath(failedPath));
                LOG.warn("Failed to import movie in file {} due to errors while processing the data. The file was archived to {}", xmlFile.getFileName(), failedPath);
            } else {
                moveFile(xmlFile, FileSystems.getDefault().getPath(okPath));
                LOG.info("File {} with movie {} has been processed and archived to {}", xmlFile.getFileName(), movieTitle, okPath);
            }
		} catch (IOException e) {
			LOG.error("An error occurred while attempting to archive {}", xmlFile, e);
		}
	}

	private MovieTransferResource processError(final Path xmlFile, final Exception error) {
        MovieTransferResource movieTransferResource = new MovieTransferResource();

		// Read the file
		DVD movieTransfer = xmlToObjects.apply(xmlFile);

        movieTransferResource
                .setImportDate(Date.from(Instant.now()))
                .setStatus(MovieImportStatus.FAILED)
                .setStatusDescription(error.getMessage())
                .setMovie(movie2resource(movieTransfer))
                .setFileName(xmlFile.getFileName().toString());

		return movieTransferResource;
	}

	private MovieTransferResource processFile(final Path xmlFile) {
        MovieTransferResource movieTransferResource = new MovieTransferResource();

		// Read the file
		final DVD movieTransfer = xmlToObjects.apply(xmlFile);

        movieTransferResource
                .setImportDate(Date.from(Instant.now()))
                .setStatus(MovieImportStatus.SUCCESS)
                .setMovie(movie2resource(movieTransfer))
                .setFileName(xmlFile.getFileName().toString());

		return movieTransferResource;
	}

	private Function<Path, DVD> xmlToObjects = xmlFile -> {
		if (!xmlFile.toFile().exists()) {
			LOG.error("Kunde inte hitta filen {}", xmlFile);
			throw new RuntimeException(new FileNotFoundException(xmlFile.toString()));
		}
		try {
			return xmlConverter.convertFromXMLToObject(xmlFile);
		} catch (IOException e) {
			LOG.error("Något gick fel vid inläsning av fil", e);
			throw new RuntimeException(e);
		}
	};
}
