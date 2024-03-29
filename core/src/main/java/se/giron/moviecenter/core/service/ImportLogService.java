package se.giron.moviecenter.core.service;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.giron.moviecenter.core.repository.ImportLogRepository;
import se.giron.moviecenter.model.entity.MovieImportLog;
import se.giron.moviecenter.model.resource.imports.MovieTransferResource;

import java.time.Instant;
import java.util.Date;

@Service
public class ImportLogService {

    public static final String DURATION_FORMAT = "HH:mm:ss";
    private static final Logger LOG = LoggerFactory.getLogger(ImportLogService.class);

    @Autowired
    private ImportLogRepository importLogRepository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void createLog(MovieTransferResource movieTransferResource) {
        importLogRepository.save(movieTransferResource2log(movieTransferResource));
    }

    private MovieImportLog movieTransferResource2log(MovieTransferResource movieTransferResource) {
        final Date importEndTimestamp = Date.from(Instant.now());

        String importDuration = null;

        if (movieTransferResource.getImportDate() != null) {
            importDuration = DurationFormatUtils.formatPeriod(movieTransferResource.getImportDate().getTime(), importEndTimestamp.getTime(), DURATION_FORMAT);
        }

        return new MovieImportLog()
                .setMovieTitle(movieTransferResource.getMovie() != null ? movieTransferResource.getMovie().getTitle() : null)
                .setImportStartTimestamp(movieTransferResource.getImportDate())
                .setImportEndTimestamp(importEndTimestamp)
                .setImportDuration(importDuration)
                .setStatus(movieTransferResource.getStatus())
                .setStatusDescription(movieTransferResource.getStatusDescription())
                .setFilename(movieTransferResource.getFileName())
                .setCountTotal(movieTransferResource.getCountTotal())
                .setCountSuccessful(movieTransferResource.getCountSuccessful())
                .setCountIgnored(movieTransferResource.getCountIgnored())
                .setCountFailed(movieTransferResource.getCountFailed());
    }
}
