package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.giron.moviecenter.model.entity.Movie;

import java.util.List;
import java.util.Optional;

/**
 * Created by marc on 2020-04-17.
 */
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    @EntityGraph(value = "Movie", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Movie> findById(Long movieId);

    List<Movie> findAllByTitle(String title);

    @Query("SELECT m FROM Movie m WHERE m.moviePersonalInfo.archiveNumber = :archiveNumber")
    Movie findByArchiveNumber(@Param("archiveNumber") Integer archiveNumber);

    @Query("SELECT m FROM Movie m WHERE m.movieFormatInfo.upcId = :upcId")
    Movie findByUpcId(@Param("upcId") String upcId);

    @Query("SELECT m FROM Movie m WHERE m.movieFormatInfo.upcId = :upcId OR m.moviePersonalInfo.archiveNumber = :archiveNumber")
    List<Movie> findAllByUpcIdOrArchiveNumber(@Param("upcId") String upcId, @Param("archiveNumber") Integer archiveNumber);
//
//    @EntityGraph(value = "Movie", type = EntityGraph.EntityGraphType.LOAD)
//    Movie save(Movie movie);

    @Query("SELECT COUNT(m) FROM Movie m")
    Integer totalCount();
}
