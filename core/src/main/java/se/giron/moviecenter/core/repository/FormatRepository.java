package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import se.giron.moviecenter.model.entity.Format;

/**
 * Created by marc on 2020-04-17.
 */
public interface FormatRepository extends JpaRepository<Format, Long>, JpaSpecificationExecutor<Format> {
}
