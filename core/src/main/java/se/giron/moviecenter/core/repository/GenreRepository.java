package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.giron.moviecenter.model.entity.Genre;

import java.util.List;

/**
 * Created by marc on 2020-04-17.
 */
public interface GenreRepository extends JpaRepository<Genre, String> {

    List<Genre> findAllByOrderByNameAsc();
}
