package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.giron.moviecenter.model.entity.Person;

import java.util.List;

/**
 * Created by marc on 2020-04-17.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByName(String name);

    @Query("SELECT COUNT(p) FROM Person p")
    Integer totalCount();
}
