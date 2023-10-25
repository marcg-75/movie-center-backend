package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import se.giron.moviecenter.model.entity.MovieImportLog;

import java.util.List;
import java.util.Optional;

public interface ImportLogRepository extends JpaRepository<MovieImportLog, Long>, JpaSpecificationExecutor<MovieImportLog> {

    Optional<MovieImportLog> findByMovieTitle(String movieTitle);

    List<MovieImportLog> findByFilename(String filename);
}
