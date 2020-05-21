package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.giron.moviecenter.model.entity.Language;
import se.giron.moviecenter.model.entity.Studio;

import java.util.List;

/**
 * Created by marc on 2020-04-22.
 */
public interface StudioRepository extends JpaRepository<Studio, Long> {

    List<Studio> findAllByOrderByNameAsc();

    @Query("SELECT s FROM Studio s WHERE s.name = :sName")
    List<Studio> findByName(@Param("sName") String name);
}
