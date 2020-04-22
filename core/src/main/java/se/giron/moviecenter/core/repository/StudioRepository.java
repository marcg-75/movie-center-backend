package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.giron.moviecenter.model.entity.Studio;

import java.util.List;

/**
 * Created by marc on 2020-04-22.
 */
public interface StudioRepository extends JpaRepository<Studio, Long> {

    List<Studio> findAllByOrderByNameAsc();
}
