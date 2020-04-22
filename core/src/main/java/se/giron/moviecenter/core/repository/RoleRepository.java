package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import se.giron.moviecenter.model.entity.Role;

import java.util.List;

/**
 * Created by marc on 2020-04-17.
 */
public interface RoleRepository extends JpaRepository<Role, String> {

    List<Role> findAllByOrderByNameAsc();
}
