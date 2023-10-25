package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.giron.moviecenter.model.entity.CastAndCrew;

public interface CastAndCrewRepository extends JpaRepository<CastAndCrew, Long> {
}
