package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.giron.moviecenter.model.entity.MovieGenre;

import java.util.List;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {
    @Query("SELECT mg FROM MovieGenre mg WHERE mg.movie.id = :movieId AND mg.genre.code = :genreCd")
    List<MovieGenre> findAllByMovieAndGenre(@Param("movieId") Long movieId, @Param("genreCd") String genreCd);
}
