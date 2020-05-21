package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.giron.moviecenter.model.entity.Language;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findAllByOrderByIdAsc();

    List<Language> findAllByOrderByNameAsc();

    //List<Language> findAllByOrderByViewOrderAsc();

    @Query("SELECT l FROM Language l WHERE l.name = :lName")
    List<Language> findByName(@Param("lName") String name);
}
