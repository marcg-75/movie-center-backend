package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import se.giron.moviecenter.model.entity.MoviePersonRole;

import java.util.List;

/**
 * Created by marc on 2020-04-17.
 */
public interface MoviePersonRoleRepository extends JpaRepository<MoviePersonRole, Long>, JpaSpecificationExecutor<MoviePersonRole> {

    List<MoviePersonRole> findByMovieId(Long movieId);  // TODO: Onödig, när kopplingen redan finns i Movie-entiteten?

    Long countByMovieId(Long movieId);

    @Query("SELECT COUNT(mpr) FROM MoviePersonRole mpr WHERE mpr.role.code = ?1")
    Long countByRole(String roleCd);
}
