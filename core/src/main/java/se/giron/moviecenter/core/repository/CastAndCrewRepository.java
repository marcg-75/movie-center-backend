package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.giron.moviecenter.model.entity.CastAndCrew;

import java.util.List;

/**
 * Created by marc on 2020-04-17.
 */
public interface CastAndCrewRepository extends JpaRepository<CastAndCrew, Long>, JpaSpecificationExecutor<CastAndCrew> {

    List<CastAndCrew> findByMovieId(Long movieId);  // TODO: Onödig, när kopplingen redan finns i Movie-entiteten?

    Long countByMovieId(Long movieId);
}
